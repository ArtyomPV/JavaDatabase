<h4 id="heading_start"></h4>
[Начало](#heading_start)

[1. Маппинг коллекций](#heading_mapping_collection)

[2. @ManyToOne](#heading_many_to_one)

[3. @OneToMany](#heading_one_to_many)

[4. @ManyToMany](#heading_many_to_many)


<h2 class="green" id="heading_mapping_collection">Маппинг коллекций</h2>

### Список коллекций
Что ж, ты познакомился с тем, как мапить простые типы. Теперь пора перейти к вопросам поинтереснее – как мапить коллекции объектов.

А объекты у нас могут быть в 5 группах:

* Array – массив объектов
* List – список объектов
* Set – множество объектов
* Map – словарь объектов
* Collection – коллекция объектов

И пример класса с полем-коллекцией:
```java
@Entity
@Table(name="user")
class User {
@Id
@Column(name="id")
public Integer id;

@Волшебная-аннотация
public List messages;
}
```
Так что же это за волшебная аннотация такая, которая позволит нам хранить 
не одно поле, а много значений?

Эта аннотация называется @ElementCollection. Пример:
```
@Entity
@Table(name="user")
class User  {
@Id
@Column(name="id")
public Integer id;

@ElementCollection
public List<String> messages;
}
```
Пишется она очень просто, а вот работает нетривиально.

Вспомогательная таблица
Все поля Entity-класса, которые содержат много элементов и помечаются с помощью аннотации @ElementCollection содержатся в базе данных в специальной вспомогательной таблице. Что, собственно говоря, логично.

Эта таблица может содержать данные в двух видах:

* Упорядоченные (List, Map) содержат три колонки:

    * Key Column (Foreign Key) – ссылка на ID объекта-родителя.
    * Index Column – позиция/индекс в коллекции.
    * Element Column – значение.


* Неупорядоченные (Set) содержат две колонки:

    * Key Column (Foreign Key) – ссылка на ID объекта-родителя.
    * Element Column – значение.

Также ты можешь задать имя этой таблицы явно с помощью аннотации:

@CollectionTable(name="имя_таблицы")
Пример:
```java
@Entity
@Table(name="user")
class User {
@Id
@Column(name="id")
public Integer id;

@ElementCollection
@CollectionTable(name="user_message")
public List<String> messages;
}
```
Важно! Если аннотация @CollectionTable не указана, то Hibernate сам построит имя таблицы на основе имени класса и имени поля: класс User и поле messages дадут имя таблице "User_messages".

### Коллекция Set
Но давай не будем отдавать на откуп Hibernate создание вспомогательной таблицы и создадим ее сами. Сначала нам нужно создать таблицу с двумя колонками:
```
CREATE TABLE user_message {
user_id INT,
message VARCHAR(255)
};
```
Обрати внимание, что у этой таблицы нет своей id-колонки. Это и есть основной признак вспомогательных таблиц. С другими видами вспомогательных таблиц ты познакомишься немного позже.

Теперь нужно замапить эту таблицу на наше поле messages в классе User. Выглядеть это дело будет так:
```
@Entity
@Table(name="user")
class User {
@Id
@Column(name="id")
public Integer id;

@ElementCollection
@CollectionTable(name="user_message", joinColumns = @JoinColumn(name = "user_id"))
@Column(name = "message")
public Set<String> messages;
}
```
Тут стоит обратить внимание на две вещи.

Во-первых, колонка message, указанная с помощью аннотации @Column(name = "message"), находится во вспомогательной таблице user_message, а не в таблице user.

Во-вторых, в аннотации @JoinColumn(name = "user_id") мы указали имя колонки user_id, которая ссылается на id таблицы user. Это чтобы Hibernate знал, как их правильно объединять.

### Коллекция List
Если ты хочешь хранить во вспомогательной таблице упорядоченные элементы списка или массива, то тебе понадобится таблица с тремя колонками:
```
CREATE TABLE user_message {
user_id INT,
index INT,
message VARCHAR(255)
};
```
Если не нравится имя колонки "index", или ты не можешь его поменять,
то можно указать другое имя во время маппинга. 
Для этого нужно использовать аннотацию @Index.

Пример:

```java
import jakarta.persistence.OrderColumn;

@Entity
@Table(name = "user")
class User {
    @Id
    @Column(name = "id")
    public Integer id;

    @ElementCollection
    @CollectionTable(name = "user_message",
            indexes = {@Index(columnList = "list_index")},
            joinColumns = @JoinColumn(name = "user_id"))
    @OrderColumn(name = "list_index")
    @Column(name = "message")
    public List<String> messages;
}
```
### Коллекция Map
И наконец, ты хочешь сохранять не просто коллекцию, а HashMap, и тебе для него нужны две колонки во вспомогательной таблице:
```
CREATE TABLE user_message {
user_id INT,
key VARCHAR(255),
message VARCHAR(255)
};
```
Для того, чтобы указать ключ для Map тебе понадобится аннотация @MapKeyColumn.

Пример:
```java
@Entity
@Table(name="user")
class User {
@Id
@Column(name="id")
public Integer id;

@ElementCollection
@CollectionTable(name="user_message", joinColumns = @JoinColumn(name = "user_id"))
@MapKeyColumn(name = "key")
@Column(name = "message")
public Map<String, String> messages;
}
```
Более подробную информацию ты сможешь найти в [официальной документации](https://docs.jboss.org/hibernate/orm/6.0/userguide/html_single/Hibernate_User_Guide.html#collections-array).

[Начало](#heading_start)

<h2 class="green" id="heading_many_to_one">@ManyToOne</h2>

### 2.1 Связь на уровне таблиц
Мы познакомились с тем, как Hibernate хранит коллекции во вспомогательных таблицах. 
Теперь же разберемся, как организовать связи между полноценными таблицами, которые хранят настоящие Entity-классы.

Всего между Entity-классами в Hibernate есть четыре вида отношений:

* one-to-one
* one-to-many
* many-to-one
* many-to-many

И начнем мы разбор с самого простого варианта – many-to-one.

С таким отношением между таблицами в SQL ты уже сталкивался. Вот как оно обычно выглядит:

|id	|name	|occupation	|salary	|age	|join_date|
|-----|-----|-----|-----|-----|-----|
|1	|Иванов Иван	|Программист|	100000	|25	|2012-06-30|
|2	|Петров Петр	|Программист	|80000	|23	|2013-08-12|
|3	|Иванов Сергей	|Тестировщик	|40000	|30	|2014-01-01|
|4	|Рабинович Мойша	|Директор	|200000	|35	|2015-05-12|
|5	|Кириенко Анастасия	|Офис-менеджер	40000	|25	|2015-10-10|
|6	|Васька	|Кот	|1000	|3	|2018-11-11|

Таблица employee:

В этой таблице есть такие колонки:
```
id INT
name VARCHAR
occupation VARCHAR
salary INT
age INT
join_date DATE
```
А так выглядит таблица task, которая содержит задачи для сотрудников:

| id	 | employee_id	name | 	deadline                    |
|-----|------------------|------------------------------|
| 1	  | 1	               | Исправить багу на фронтенде	 |2022-06-01|
| 2   | 	2               | 	Исправить багу на бэкенде	  | 2022-06-15       |
 | 3   | 	5               | 	Купить кофе	                | 2022-07-01                   |
 | 4   | 	5               | 	Купить кофе	                | 2022-08-01                   |
 | 5   | 	5               | 	Купить кофе	                | 2022-09-01                   |
| 6   | 	(NULL)          | 	Убрать офис	(NULL)          |
| 7   | 	4               | 	Наслаждаться жизнью	(NULL)  |
| 8   | 	6               | 	Наслаждаться жизнью	(NULL)  |

В этой таблице есть всего 4 колонки:

* id – уникальный номер задания (и строки в таблице);
* employee_id – ID сотрудника из таблицы employee, на которого назначена задача;
* name – название и описание задачи;
* deadline – время до которого нужно выполнить задачу.

Мы видим, что много строк таблицы task могут ссылаться на одну запись таблицы employee. 
Такая связь на уровне таблиц называется многие-к-одному (many-to-one).

### 2.2 Связь на уровень Java-классов
Кроме связи на уровне таблиц, можно еще организовать связь на уровне 
Entity-классов в Hibernate. Делается это с помощью аннотации @ManyToOne.

Но для начала просто создадим два класса: Employee и EmployeeTask:
```java
@Entity
@Table(name="employee")
class Employee {
@Column(name="id")
public Integer id;

@Column(name="name")
public String name;

@Column(name="occupation")
public String occupation;

@Column(name="salary")
public Integer salary;

@Column(name="join_date")
public Date join;
}
```
И второй класс для хранения заданий сотрудников:
```java
@Entity
@Table(name="task")
class EmployeeTask {
@Column(name="id")
public Integer id;

@Column(name="name")
public String description;

@Column(name="employee_id")
public Integer employeeId;

@Column(name="deadline")
public Date deadline;
}
```
С этими классами все хорошо, но между ними нет никакой связи, которая бы отражала
тот факт, что поле employeeId класса EmployeeTask ссылается на поле id класса Employee.
Пришло время это исправить

### 2.3 Аннотация @ManyToOne.
Во-первых, в Java мы привыкли оперировать объектами (и ссылками на объекты),
а не их id. Так что первым делом давайте вместо поля employeeId в классе EmployeeTask
просто укажем ссылку на объект типа Employee. Вот как будет выглядеть наш новый класс:
```java
@Entity
@Table(name="task")
class EmployeeTask {
@Column(name="id")
public Integer id;

@Column(name="name")
public String description;

@ManyToOne
@JoinColumn(name = "employee_id")
public Employee employee;

@Column(name="deadline")
public Date deadline;
}
```
С помощью аннотации @ManyToOne мы указали, что много объектов EmployeeTask 
могут ссылаться на один объект типа Employee. Также с помощью аннотации @JoinColumn 
мы указали, в какой колонке нашей таблицы хранится id объекта Employee.

### 2.4 Примеры запросов
А теперь покажем несколько примеров, как Hibernate умеет работать с такими связанными
классами.

Сценарий первый
    Давай напишем запрос, чтобы узнать все задачи, которые были назначены 
на определенного пользователя. Вот как этот запрос будет выглядеть на HQL:

    from EmployeeTask where employee.name = "Иван Иванович"

Ты можешь просто обращаться к полям зависимых классов через точку. 
Это очень удобно. Но давай все же запишем этот запрос в виде Java-кода:
```
String hql = "from EmployeeTask where employee.name = :username";
Query<EmployeeTask> query = session.createQuery( hql, EmployeeTask.class);
query.setParameter("username", "Иван Иванович");
List<EmployeeTask> resultLIst = query.list();
```

Сценарий второй

Давай напишем запрос, который возвращает список сотрудников, 
у которых есть просроченные задачи. Задача просрочена, если ее deadline уже в прошлом.
Вот как будет выглядеть этот запрос на SQL:
```
SELECT DISTINCT employee.*
FROM task JOIN employee ON task.employee_id = employee.id
WHERE task.deadline < CURDATE();
```
DISTINCT используется, потому что может быть много задач назначенных на 
одного пользователя.

А теперь запишем этот же запрос на HQL:

    select distinct employee from EmployeeTask where deadline < CURDATE();

Employee в этом запросе – это поле класса EmployeeTask

***Ситуация третья***

Назначим все неназначенные задачи на директора. Запрос на SQL будет выглядеть так:

    UPDATE task SET employee_id = 4 WHERE employee_id IS NULL

А теперь запишем этот же запрос на HQL:

    update EmployeeTask set employee = :user where employee is null

Последний запрос самый сложный. Нам нужно передать ID директора, 
но класс EmployeeTask не содержит поля, куда можно записать id, 
вместо этого он содержит поле Employee, куда нужно присвоить ссылку на объект типа Employee.
```
Employee director = session.get(Employee.class, 4);

String hql = "update EmployeeTask set employee = :user where employee is null";
Query<EmployeeTask> query = session.createQuery(hql, EmployeeTask.class);
query.setParameter("user", director);
query.executeUpdate();
```

[Начало](#heading_start)

<h2 class="green" id="heading_one_to_many">@OneToMany</h2>

### Связь на уровне таблиц
Давай опять рассмотрим две наши таблицы:

|id	|name	|occupation	|salary	|age	|join_date|
|----|----|----|----|----|----|
|1	|Иванов Иван	|Программист|	100000	|25|	2012-06-30|
|2	|Петров Петр	|Программист|	80000	|23|	2013-08-12|
|3	|Иванов Сергей	|Тестировщик|	40000	|30	|2014-01-01|
|4	|Рабинович Мойша	|Директор	|200000|	35	|2015-05-12|
|5	|Кириенко Анастасия	|Офис-менеджер	|40000|	25|	2015-10-10|
|6	|Васька|	Кот	|1000	|3	|2018-11-11|

Таблица employee:

В этой таблице есть такие колонки:
```
id INT
name VARCHAR
occupation VARCHA
salary INT
age INT
join_date DATE
```
А так выглядит таблица task, которая содержит задачи для сотрудников:

|id	|emploee_id	|name	|deadline|
|----|----|----|----|
|1	|1	|Исправить багу на фронтенде	|2022-06-01|
|2	|2	|Исправить багу на бэкенде	|2022-06-15|
|3	|5	|Купить кофе	|2022-07-01|
|4	|5	|Купить кофе|	2022-08-01|
|5	|5	|Купить кофе	|2022-09-01|
|6	|(NULL)	|Убрать офис|	(NULL)|
|7	|4	|Наслаждаться жизнью	|(NULL)|
|8	|6	|Наслаждаться жизнью|	(NULL)|

В этой таблице есть всего 4 колонки:

    id – уникальный номер задания (и строки в таблице).
    employee_id – ID сотрудника из таблицы employee, на которого назначена задача.
    name – название и описание задачи.
    deadline – время до которого нужно выполнить задачу.
На одну запись таблицы employee могут ссылаться много строк таблицы task. Такая связь на уровне таблиц называется один-ко-многим (one-to-many).

Связь на уровень Java-классов
И наши классы, класс Employee:
```java
@Entity
@Table(name="user")
class Employee {
@Column(name="id")
public Integer id;

@Column(name="name")
public String name;

@Column(name="occupation")
public String occupation;

@Column(name="salary")
public Integer salary;

@Column(name="join_date")
public Date join;
}
```
И класс EmployeeTask в его изначальном виде:
```java
@Entity
@Table(name="task")
class EmployeeTask {
@Column(name="id")
public Integer id;

@Column(name="name")
public String description;

@Column(name="employee_id")
public Integer employeeId;

@Column(name="deadline")
public Date deadline;
}
```
Аннотация @OneToMany
Мы можем организовать связь Entity-классов по-другому.

Помнишь аннотацию @ElementCollection, с помощью которой мы в родительском классе создавали коллекцию дочерних объектов? Что-то похожее можно сделать с помощью аннотации @OneToMany. Только в этот раз изменениям подвергнется класс Employee:
```java
@Entity
@Table(name="user")
class Employee {
@Column(name="id")
public Integer id;

@OneToMany(cascade = CascadeType.ALL)
@JoinColumn(name = "employee_id")
private Set<EmployeeTask> tasks = new HashSet<EmployeeTask>();
}
```
С помощью аннотации @OneToMany мы указали, что объект Employee может хранить у себя много объектов EmployeeTask. Также с помощью аннотации @JoinColumn мы указали, в какой колонке таблицы task хранится id объекта Employee.

При этом класс EmployeeTask обычно не содержит поля, которое ссылается на колонку employee_id. Пример:
```java
@Entity
@Table(name="task")
class EmployeeTask {
@Column(name="id")
public Integer id;

@Column(name="name")
public String description;

@Column(name="deadline")
public Date deadline;
}
```
Поле employee_id считается служебным и его значением управляет Hibernate.

Примеры запросов
Если ты хочешь добавить какому-то работнику некоторый task, то тебе нужно написать код типа такого:
```
EmployeeTask task1 = new EmployeeTask();
task1.description = "Сделать что-то важное";
session.persist(task1);

EmployeeTask task2 = new EmployeeTask();
task2.description = "Ничего не делать";
session.persist(task2);
session.flush();

Employee director = session.find(Employee.class, 4);
director.tasks.add(task1);
director.tasks.add(task2);

session.update(director);
session.flush();
```
Сначала мы создаем два объекта EmployeeTask, сохраняем их в базу и вызываем метод flush(), чтобы выполнилась операция INSERT и у объектов появились ID.

Затем находим в базе директора, берем у него поле tasks и добавляем ему две задачи. Затем сохраняем директора в базу. После этого в базе у новых task в колонке employee_id появится значение 4 – id директора в таблице employee.

Важно! Таблицы в базе одни и те же для аннотаций @ManyToOne и @OneToMany. А вот Java-классы для этих таблиц – разные.

[Начало](#heading_start)
<h2 class="green" id="heading_many_to_many">@ManyToMany</h2>

### Служебная таблица
Теперь разберем еще один часто встречающийся случай – many-to-many. Давай представим, что у нас отношение между задачами и сотрудниками многие-ко-многим:

Один сотрудник в таблице employee может делать много задач из таблицы task.
Одна задача в таблице task может быть назначена на несколько сотрудников.
Такая связь между сущностями называется многие-ко-многим. И чтобы ее реализовать на уровне SQL, нам понадобится дополнительная служебная таблица. Назовем ее, например, employee_task.

Таблица employee_task будет содержать всего две колонки:

* employee_id
* task_id
Каждый раз, когда мы будем назначать определенную задачу определенному пользователю, в эту таблицу будет добавляться новая строка. Пример:

|employee_id	|task_id|
|---|---|---|
|1	|1|
|1	|2|
|2	|3|
Ну, а таблица task должна лишиться колонки employee_id. В ней есть смысл, только если задача может быть назначена только на одного сотрудника. Если же задача может быть назначена на нескольких сотрудников, то эту информацию нужно хранить в служебной таблице employee_task.

Связь на уровне таблиц
Вот как будут выглядеть наши новые таблицы:

|id	|name	|occupation	|salary|	age	|join_date|
|---|----|---|---|----|-----|
|1	|Иванов Иван|	Программист|	100000	|25	|2012-06-30|
|2	|Петров Петр|	Программист	|80000	|23	|2013-08-12|
|3	|Иванов Сергей	|Тестировщик	|40000	|30	|2014-01-01|
|4	|Рабинович Мойша|Директор|	200000	|35	|2015-05-12|
|5	|Кириенко Анастасия	|Офис-менеджер	|40000	|25	|2015-10-10|
|6	|Васька	|Кот	|1000	|3	|2018-11-11|

Таблица employee (не изменилась):

В этой таблице есть такие колонки:

* id INT
* name VARCHAR
* occupation VARCHAR
* salary INT
* age INT
* join_date DATE
А вот так выглядит таблица task, потеряла колонку employee_id (отмечена красным):

| id	 | emploee_id	 |name	|deadline|
|-----|-------------|---|----|
| 1	  | 1	          | Исправить багу на фронтенде |	2022-06-01|
| 2   | 	2          | 	Исправить багу на бэкенде |	2022-06-15|
| 3	  | 5	          | Купить кофе	 |2022-07-01|
| 4	  | 5	          |Купить кофе	|2022-08-01|
| 5	  | 5	          | Купить кофе	                |2022-09-01|
| 6	  | (NULL)	     |Убрать офис|	(NULL)|
|  7	 | 4	          | Наслаждаться                |жизнью	(NULL)|
| 8	  | 6	          | Наслаждаться                | жизнью	(NULL)|

В этой таблице теперь есть всего 3 колонки:

* id – уникальный номер задания (и строки в таблице)
* employee_id – (удалена)
* name – название и описание задачи
* deadline – время, до которого нужно выполнить задачу
* 
Также у нас есть служебная таблица employee_task, куда перекочевали данные об employee_id из таблицы task:

|employee_id|	task_id|
|----|-----|
|1	|1|
|2	|2|
|5|	3|
|5|	4|
|5	|5|
|(NULL)|	6|
|4|	7|
|6|	8|
Я специально временно сохранил удаленную колонку в таблице task, чтобы ты мог увидеть, что данные из нее переехали в таблицу employee_task.

Еще один важный момент – красная строка "(NULL) 6" в таблице employee_task. Я отметил ее красным, так как ее не будет в таблице employee_task.

Если таск 7 назначен на пользователя 4, то в таблице employee_task должна быть строка (4, 7).

Если таск 6 ни на кого не назначен, то просто в таблице employee_task для него не будет никакой записи. Вот как будут выглядеть финальные версии этих таблиц:

Таблица task:

| id	                           | name	                        | deadline  |
|-------------------------------|------------------------------|-----------|
 | 1| 	Исправить багу на фронтенде | 	2022-06-01 |
 | 2| 	Исправить багу на бэкенде   | 	2022-06-15                  |
 | 3| 	Купить кофе	                | 2022-07-01                   |
 | 4| 	Купить кофе	                | 2022-08-01                   |
 | 5| 	Купить кофе	                | 2022-09-01                   |
 | 6| 	Убрать офис                 | 	(NULL)                      |
 | 7| 	Наслаждаться жизнью	        | (NULL)                       |
 | 8| 	Наслаждаться жизнью         | 	(NULL)                      |
Tаблица employee_task:

|employee_id|	task_id|
|-----|	-----|
|1|	1|
|2|	2|
|5|	3|
|5|	4|
|5|	5|
|4|	7|
|6|	8|
Связь на уровне Java-классов
Зато со связью на уровне Entity-классов у нас полный порядок. Начнем с хороших новостей.

Во-первых, у Hibernate есть специальная аннотация @ManyToMany, которая позволяет хорошо описать случай отношения таблиц many-to-many.

Во-вторых, нам по-прежнему достаточно двух Entity-классов. Класс для служебной таблицы нам не нужен.

Вот как будут выглядеть наши классы. Класс Employee в изначальном виде:
```
@Entity
@Table(name="user")
class Employee {
@Column(name="id")
public Integer id;

@Column(name="name")
public String name;

@Column(name="occupation")
public String occupation;

@Column(name="salary")
public Integer salary;

@Column(name="join_date")
public Date join;
}
```
И класс EmployeeTask в его изначальном виде:
```
@Entity
@Table(name="task")
class EmployeeTask {
@Column(name="id")
public Integer id;

@Column(name="name")
public String description;

@Column(name="deadline")
public Date deadline;
}
```
### Аннотация @ManyToMany
Я опущу в примерах существующие поля, зато добавлю новые. Вот как они будут выглядеть. Класс Employee:
```
@Entity
@Table(name="employee")
class Employee {
@Column(name="id")
public Integer id;

@ManyToMany(cascade = CascadeType.ALL)
@JoinTable(name="employee_task",
joinColumns=  @JoinColumn(name="employee_id", referencedColumnName="id"),
inverseJoinColumns= @JoinColumn(name="task_id", referencedColumnName="id") )
private Set<EmployeeTask> tasks = new HashSet<EmployeeTask>();

}
```
И класс EmployeeTask:
```
@Entity
@Table(name="task")
class EmployeeTask {
@Column(name="id")
public Integer id;

@ManyToMany(cascade = CascadeType.ALL)
@JoinTable(name="employee_task",
joinColumns=  @JoinColumn(name="task_id", referencedColumnName="id"),
inverseJoinColumns= @JoinColumn(name=" employee_id", referencedColumnName="id") )
private Set<Employee> employees = new HashSet<Employee>();

}
```
Кажется, что все сложно, но на самом деле там все просто.

Во-первых, там используется аннотация @JoinTable (не путать с @JoinColumn), которая описывает служебную таблицу employee_task.

Во-вторых, там описывается, что колонка task_id таблицы employee_task ссылается на колонку id таблицы task.

В-третьих, там говориться, что колонка employee_id таблицы employee_task ссылается на колонку id таблицы employee.

Мы фактически с помощью аннотаций описали какие данные содержатся в таблице employee_task и как Hibernate должен их интерпретировать.

Зато мы теперь очень просто можем добавить (и удалить) задание любому сотруднику. А также добавить любого исполнителя любому заданию.

Примеры запросов
Давай напишем пару интересных запросов, чтобы лучше понять, как работают эти ManyToMany поля. А работают они абсолютно так, как и ожидается.

Во-первых, наш старый код будет работать без изменений, так как у директора и раньше было поле tasks:
```
EmployeeTask task1 = new EmployeeTask();
task1.description = "Сделать что-то важное";
session.persist(task1);

EmployeeTask task2 = new EmployeeTask();
task2.description = "Ничего не делать";
session.persist(task2);
session.flush();

Employee director = session.find(Employee.class, 4);
director.tasks.add(task1);
director.tasks.add(task2);

session.update(director);
session.flush();
```
Во-вторых, если мы захотим назначить какому-то заданию еще одного исполнителя, то сделать это еще проще:
```
Employee director = session.find(Employee.class, 4);
EmployeeTask task = session.find(EmployeeTask.class, 101);
task.employees.add(director);

session.update(task);
session.flush();
```
**Важно!** В результате выполнения этого запроса не только у задачи появится исполнитель-директор, но еще и у директора появится задача № 101.

Во-первых, факт о связи директора и задачи в таблице employee_task будет сохранен в виде строки: (4,101).

Во-вторых, поля, помеченные аннотациями @ManyToMany, являются proxy-объектами и при обращении к ним всегда выполняется запрос к базе данных.

Так что если добавить задачу к сотруднику и сохранить информацию о сотруднике в базу, то после этого у задачи в списке исполнителей появится новый исполнитель.

[Начало](#heading_start)












<style>
.green {
  color: green;
}

</style>