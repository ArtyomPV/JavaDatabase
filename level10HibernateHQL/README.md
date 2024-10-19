[1. Учимся писать запросы на HQL](#heading-write-hql)

[2. Изучаем класс Query](#heading_query)

[3. Join в HQL](#heading_join_hql)

[4. Запросы с параметрами](#heading-parametersQuery)

[5. Ограничение длины запроса](#heading-limit-query)

[6. Другие типы запросов](#heading-other-request)

<h2 class="green" id="heading-write-hql">
Учимся писать запросы на HQL</h2>

### 1.1 Знакомство с HQL

HQL, он же Hibernate Query Language. Фактически, – это SQL переделанный под написание запросов в Hibernate. У него есть несколько ключевых отличий.

* Использование имени класса вместо имени таблицы.
* Использование имени поля класса вместо имени колонки таблицы.
* Необязательное использование select.

Давай попросим Hibernate вернуть нам всех пользователей, которые есть у него в базе. Вот как будет выглядеть этот запрос:

<span class="frame">
from User
</span>

Это все, для сравнения приведем аналогичный запрос на SQL:

<span class="frame">
select * from user
</span>

Тут User – это имя класса, а user – это имя таблицы.

Полностью Java код будет выглядеть вот так:

```
public List<User> getAllUsers() {
    try (Session session = sessionFactory.openSession()) {
    return session.createQuery("from User", User.class).list();
    }
}
```

### 1.2 Пример работы с HQL

Пусть у нас есть таблица user_data, которая содержит такие поля:

    id INT
    user_name VARCHAR(100)
    user_level INT
    user_created DATE
Мы создадим Java-класс, который замапим на эту таблицу:
```java
@Entity
@Table(name="user_data")
class User {
@Id
@GeneratedValue
public Integer id;

@Column(name="user_name")
public String name;

@Column(name="user_level")
public Integer level;

@Column(name="user_created")
public Date created;
}
```
| HQL                                  | 	SQL                                                     |
|--------------------------------------|----------------------------------------------------------|
| from User	                           | select * from user_data                                  |
 | from User where id=3	                | select * from user_data where id=3                       |
 | from User where level in (10,20,30)	 | select * from user_data where user_level IN (10, 20, 30) |
 | from User order by created asc	      | select * from user_data order by user_created asc        |
 | from User where name like 'тест'	    | select * from user_data where user_name like 'тест'      |

### 1.3 Использование select

Получить имена всех пользователей, которые есть в нашей таблице user_data, тогда нужно написать такой запрос:
<p class="frame">
select name from User
</p>

Также если среди имен есть дубликаты, то можно воспользоваться оператором DISTINCT:
<p class="frame">
select distinct name from User
</p>
Алиасы работают так же, как и в SQL:
<p class="frame">
select distinct u.name from User u where u.created > '2020-01-01'
</p>
Ну и полностью в виде Java-кода этот запрос будет выглядеть вот так:

```
public List<String> getUserNames() {
    try (Session session = sessionFactory.openSession()) {
        String hql = "select distinct u.name from User u where u.created > '2020-01-01'";
        Query<String> query = session.createQuery(hql , String.class);
        return query.list();
    }
}
```
<h2 class="green" id="heading_query">
Изучаем класс Query
</h2>

### 2.1 Знакомство с классами Query
````java
public List<Employee> getAllEmployes() {
    try (Session session = sessionFactory.openSession()) {
            Query<Employee> query = session.createQuery("from Employee", Employee.class);
            return query.list();
    }
}
````

Query – это интерфейс и у него есть несколько реализаций на разные случаи.

Стандартный способ создания Query:

<p class="frame">
 Query<Employee> query = session.createQuery("from Employee", Employee.class);
</p>

вызываем метод list() у объекта Query:
<p class="frame">
 Query<Employee> query = session.createQuery("from Employee", Employee.class);
 List<Employee> resultLіst = query.list();
</p>

У метода list() есть JPA-синоним, метод который делает тоже самое, но называется getResultList().
Ты можешь иногда встретить его в коде, написанном другими программистами.

Кстати, если запрос подразумевает, что результат будет в единственном результате, то для вызова запроса проще использовать метод uniqueResult().

<p class="frame">
Query<Employee> query = session.createQuery("from Employee where id = 1", Employee.class);
Employee result = query.uniqueResult();
</p>

У метода uniqueResult() есть JPA-синоним – метод singleResult().
Он появился для совместимости Hibernate со стандартом JPA. Делает он абсолютно то же самое.

### 2.2 Методы класса Query

Mетод <span class="orange">stream()</span> И его JPA-синоним <span class="orange">getResultStream()</span>.

Оба этих метода возвращают поток данных вместо списка. Такой подход может быть очень эффективным, когда тебе не нужны сразу все объекты, полученные в результате выполнения запроса. Или есть вероятность, что будут использованы только первые из них.

Пример:
```java
Query<Employee> query = session.createQuery("from Employee where id > 100", Employee.class);
Stream<Employee> stream = query.stream();
```

Второй метод – это метод executeUpdate(). Ты можешь написать запрос, который что-то изменит в базе данных. На этот случай нужно, чтобы Hibernate не использовал read-only транзакцию при обращении к базе данных.

Пример запроса: мы решили поднять уровень всех пользователей на 1.
```java
Query<User> query = session.createQuery("update User set level=level+1", User.class);
int count = query.executeUpdate();
```

Метод executeUpdate() вернет количество строк, которые реально были изменены.

### 2.3 Методы класса Scroll

Этот метод чем-то похож на метод stream(). Только он позволяет перемещаться по списку результатов, не вытаскивая результаты вообще. То есть ты можешь выполнить запрос, потом проскролить его на миллионную строку результата и начать читать оттуда данные.

Такой продвинутый итератор.
```java
Query<Employee> query = session.createQuery("from Employee where id > 100", Employee.class);
ScrollableResults<Employee> scroll = query.scroll();
```

У объекта ScrollableResults есть такие методы:

| Метод	             | Описание                                   |
|--------------------|--------------------------------------------|
| R get()	           | Возвращает текущий элемент                 |
| next()	            | Перемещает указатель на следующий элемент  |
| previous()	        | Перемещает указатель на предыдущий элемент |
| scroll(int size)	  | Скролит на size строк вперед               |
| position(int pos)	 | Делает текущим элементом элемент номер pos |
| last()	            | Текущий элемент теперь последний           |
| first()	           | Текущий элемент теперь первый              |
| getRowNumber()	    | Возвращает номер текущей строки            |
| setRowNumber()	    | Устанавливает номер текущей строки         |

Допустим, ты выполнил запрос и хочешь получить последний элемент. Вот как это можно сделать:
```java
Query<Employee> query = session.createQuery("from Employee where id > 100", Employee.class);
ScrollableResults<Employee> scroll = query.scroll();
scroll.last();
Employee lastEmployee = scroll.get();
```

<h2 class="green" id="#heading_join_hql">Join в HQL</h2>

### 3.1 Маппинг зависимых сущностей

В SQL можно писать запросы с использованием JOIN. А можно ли так же делать в HQL? Краткий ответ – да. А вот полный ответ будет более интересным.

Во-первых, когда мы пишем JOIN в SQL, то чаще всего это значит, что одна таблица у нас ссылается на другую таблицу. Например, таблица task содержит колонку employee_id, которая ссылается на колонку id таблицы employee.

Эту зависимость можно описать с помощью аннотаций в Hibernate. Для начала давай просто создадим Entities для наших таблиц. Сначала опишем таблицу employee:
```java
@Entity
@Table(name="employee")
class Employee {
@Column(name="id")
public Integer id;

@Column(name="name")
public String name;

@Column(name="salary")
public Integer salary;

@Column(name="join_date")
public Date joinDate;
}
```
И класс EmployeeTask для таблицы task:
```java
@Entity
@Table(name="task")
class EmployeeTask {
@Column(name="id")
public Integer id;

@Column(name="name")
public String name;

 @ManyToOne
 @JoinColumn(name="employee_id", nullable=true)
 public Employee employee;

@Column(name="deadline")
public Date deadline;
}
```
Аннотация <span class="orange">@ManyToOne</span> говорит Hibernate, что много сущностей EmployeeTask могут 
сылаться на одну сущность Employee.

А аннотация <span class="orange">@JoinColumn</span> указывает имя колонки, из которой будет браться id.
Вся остальная необходимая информация будет взята из аннотаций класса Employee.

### 3.2 Использование join в HQL

#### Первая ситуация.
У нас есть сотрудник (Employee) и мы хотим получить список его задач. Вот как будет выглядеть этот запрос на SQL:
```
SELECT task.* FROM task JOIN employee ON task.employee_id = employee.id
WHERE employee.name = "Иван Иванович";
```

А теперь запишем этот же запрос на HQL:
```
from EmployeeTask where employee.name = "Иван Иванович"
```
У класса EmployeeTask есть поле employee, а у него есть поле name, 
так что такой запрос будет работать.

#### Ситуация вторая.
Вернуть список сотрудников, которые имеют просроченные задачи. 
Вот как будет выглядеть этот запрос на SQL:
```
SELECT DISTINCT employee.*
FROM task JOIN employee ON task.employee_id = employee.id
WHERE task.deadline < CURDATE();
```
DISTINCT используется, потому что может быть много задач назначенных на одного пользователя.

А теперь запишем этот же запрос на HQL:
```
select distinct employee from EmployeeTask where deadline < current_date();
```
employee в этом запросе – это поле класса EmployeeTask

#### Ситуация третья.
Назначим все не назначенные задачи на директора. Запрос на SQL будет выглядеть так:
```
UPDATE task SET employee_id = 4 WHERE employee_id IS NULL
```
А теперь запишем этот же запрос на HQL:
```
update EmployeeTask set employee = :user where employee is null
```
Последний запрос самый сложный. Нам нужно передать ID, директора,
но класс EmployeeTask не содержит поля, куда можно записать id, 
вместо этого он содержит поле Employee, 
куда нужно присвоить ссылку на объект типа Employee.

<h2 class="green" id="heading-parametersQuery">Запросы с параметрами</h2>

### 4.1 Параметры к запросам
Hibernate позволяет передавать параметры запросам. Таким образом сильно упрощается вся работа с запросами и базой данных.

<div class="frame">
from EmployeeTask where employee.name = :username
</div>

И вот как будет выглядеть наш Java-код для поиска задач:
```
String hql = "from EmployeeTask where employee.name = :username";
Query<EmployeeTask> query = session.createQuery( hql, EmployeeTask.class);
query.setParameter("username", "Иван Иванович");
List<EmployeeTask> resultLIst = query.list();
```
Также вместо имени параметр, можно использовать просто номер:
```
String hql = "from EmployeeTask where employee.name = :1";
Query<EmployeeTask> query = session.createQuery( hql, EmployeeTask.class);
query.setParameter(1, "Иван Иванович");
List<EmployeeTask> resultLIst = query.list();
```
Хотя лучше, конечно, использовать имя – читать и поддерживать такой код гораздо легче.

### 4.2 Метод setParameterList().

```
String hql = "from EmployeeTask where occupation IN (:occupation_list)";
Query<EmployeeTask> query = session.createQuery( hql, EmployeeTask.class);
query.setParameterList("occupation_list", new String[] {"Программист", "Тестировщик"});
List<EmployeeTask> resultLIst = query.list();
```
В качестве значения параметра можно передать 4 вида списка:

* массив объектов: Object[]
* коллекция: Collection
* типизированный массив: T[]
* типизированная коллекция: Collection<T>

Если ты решил передавать типизированную коллекцию или массив, то тебе нужно передать тип данных третьим параметром. Пример:

```
String hql = "from EmployeeTask where occupation IN (:occupation_list)";
Query<EmployeeTask> query = session.createQuery( hql, EmployeeTask.class);
query.setParameterList("occupation_list", new String[] {"Программист", "Тестировщик"}, String.class);
List<EmployeeTask> resultLIst = query.list();
```
При работе с параметрами-списками также можно использовать номер вместо имени параметра. Но опять-таки с именем удобнее.

### 4.3 Защита от SQL Injection

Одно из важнейший назначений параметров – это защита базы от SQL-инъекций. 
Многие программисты-новички вместо использования параметров просто бы склеили строку из нескольких частей.

Вместо того, чтобы написать так:
```
String hql = "from EmployeeTask where employee.name = :username";
Query<EmployeeTask> query = session.createQuery( hql, EmployeeTask.class);
query.setParameter("username", "Иван Иванович");
List<EmployeeTask> resultLIst = query.list();
```
Написали бы так:
```
String hql = "from EmployeeTask where employee.name = " + "Иван Иванович";
Query<EmployeeTask> query = session.createQuery( hql, EmployeeTask.class);
List<EmployeeTask> resultLIst = query.list();
```
Никогда так не делайте! Никогда не склеивай SQL/HQL-запрос из нескольких частей. 
Потому что рано или поздно имя пользователя придет тебе с клиента. 
И злобный хакер в качестве имени клиента передаст вам строку типа ""Иван"; DROP TABLE user;"

<h2 class = "green" id="heading-limit-query">Ограничение длины запроса</h2>
### Работа со страницами: LIMIT и OFFSET
Аналоги этих операторов присутствуют и в Hibernate. 

* setFirstResult() – аналог OFFSET.
* setMaxResults() – аналог LIMIT.

Показать 20 задач начиная с 41:
```java
Query<EmployeeTask> query = session.createQuery( “from EmployeeTask”, EmployeeTask.class);
query.setFirstResult(41);
query.setMaxResults(20);
List<EmployeeTask> resultLIst = query.list();
```

### Сортировка результатов
<p class="frame orange">
from Employee order by joinDate desc
</p>

Можно сортировать по нескольким полям:

<p class="frame orange">
from Employee order by joinDate desc, name asc
</p>

Кроме того, поле для сортировки можно передать в качестве параметра:
```java
String hql = "from EmployeeTask where employee.name = :username order by :ord";
Query<EmployeeTask> query = session.createQuery( hql, EmployeeTask.class);
query.setParameter(“username”, “Иван Иванович”);
query.setParameter(“ord”, “name”);

List<EmployeeTask> resultLIst = query.list();
```

### Функции в HQL
Вот список агрегатных функций, которые поддерживает HQL:

|Агрегатные функции	|Описание|
|----|-----|
|count()	|Возвращает количество строк|
|sum()	|Считает сумму значений|
|min()	|Возвращает минимальное значение|
|max()	|Возвращает максимальное значение|
|avg()	|Возвращает среднее значение|

Давай найдем количество задач, назначенных на пользователя:

```java
String hql = "select count(*) from EmployeeTask where employee.name = :username ";
Query<Integer> query = session.createQuery( hql, Integer.class);
query.setParameter(“username”, “Иван Иванович”);
Integer count = query.uniqueResult();
```
<h2 class="green" id="heading-other-request">Другие типы запросов</h2>
### 6.1 NamedQueries
     Hibernate позволяет хранить их отдельно в виде аннотаций
     @org.hibernate.annotations.NamedQueries({
     @org.hibernate.annotations.NamedQuery(name = "Employee_FindById",
     query = "from Employee where id = :id"),
     @org.hibernate.annotations.NamedQuery(name = "Employee_FindAllEmployes",
     query = "from Employee"),
     @org.hibernate.annotations.NamedQuery(name = "Employee_UpdateEmployeeName",
     query = "Update Employee set name = :newName where id = :id"),
     ...
     })

Аннотации можно добавить перед любым Entity-классом: имена запросов не привязаны 
ни к какому Entity.

Также к запросу (query) можно добавить различные параметры:

```
@org.hibernate.annotations.NamedQuery(
name = "Employee_ FindAllEmployes",
query = "from Employee",
timeout = 1,
fetchSize = 10,
cacheable = true,
cacheMode = "GET"
)
```
Использовать такие запросы очень просто – для этого нужно использовать метод createNamedQuery вместо метода createQuery:
```
Query<Employee> query = session.createNamedQuery("Employee_FindAllEmployes", Employee.class);
List<Employee> resultLIst = query.list();

```

### 6.2 NativeQuery
И еще одна простая, но очень полезная вещь – NativeQuery.

Если ты не хочешь использовать HQL, но хочешь использовать Hibernate для маппинга сущностей,
то ты можешь писать запросы на старом добром SQL. Никто тебя не ограничивает.

Для этого всего лишь нужно вызвать метод createNativeQuery(), вместо createQuery().
```
NativeQuery<Employee> query = session.createNativeQuery("select * from employee", Employee.class);
List<Employee> resultLIst = query.list();
```
Ты просто пишешь createNativeQuery и все будет работать, как и раньше. 
Этот метод возвращает объект типа NativeQuery, который поддерживает все методы, что и класс Query. Для тебя ничего не поменяется.

Кроме того, Native SQL Query также можно хранить в виде Named Queries.
```
@org.hibernate.annotations.NamedNativeQueries(
@org.hibernate.annotations.NamedNativeQuery(name = "Employee_GetAll",
query = "select * from employee",
resultClass = Employee.class)
)
```
И конечно же приведем код по работе с ними:
```
NativeQuery<Employee> query = session.createNamedQuery("Employee_GetAll", Employee.class);
List<Employee> resultLIst = query.list();
```

<style>
.green{
color: green;
}
.orange{
color: orange;
}
.frame {

margin: 0 auto;
padding: 5px;
border:1px solid orange;
text-align: center;
}
</style>