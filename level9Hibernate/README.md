[1. Object Relational Mapping](#heading_orm)

[2. Маппинг классов в Hibernate](#heading_mapping)

[3. Подключаемся к базе данных](#header_connect_to_db)

<h2 class='heading' id='heading_orm'>Object Relational Mapping</h2>

ORM расшифровывается как Object-Relational Mapping и по сути является маппингом Java-объектов на SQL-запросы.

Ребята придумали очень простую вещь — каждой таблице в базе данных должен соответствовать какой-то класс в Java-приложении. В Java приложении мы оперируем объектами, а эти объекты уже умеют сами сохранять себя в базу данных.

Было три подхода к решению этой задачи, и выглядели они примерно так:

* Объект сам себя сохраняет в базу данных и обновляет свои поля на основе информации из БД.
* Объект умеет сохранять себя в базу данных, но никогда не выступает инициатором этого дела.
* Объект содержит только данные, и кто-то его сохраняет в базу данных и загружает из БД.

JPA
За де-факто пришло и признание де-юре. Разработчики JDK решили создать спецификацию по тому, как правильно мапить объекты на таблицы в базе данных. Эта спецификация называется <span class = 'orange'>JPA — Java Persistence API</span>.

Это именно спецификация. Она описывает, как все должно работать и какими аннотациями нужно отмечать различные части класса, если мы хотим, чтобы его объекты сохранялись в базу данных.

Hibernate есть два набора функций:

* JPA-стандарт
* Hibernate Native API (дополнительная функциональность)

![picture](images/img.png)

Установка библиотеки 
```
<!-- https://mvnrepository.com/artifact/org.hibernate/hibernate-core -->
<dependency>
    <groupId>org.hibernate</groupId>
    <artifactId>hibernate-core</artifactId>
    <version>6.6.1.Final</version>
    <type>pom</type>
</dependency>

```

если есть ошибки добавь
```
<dependency>
        <groupId>jakarta.xml.bind</groupId>
        <artifactId>jakarta.xml.bind-api</artifactId>
      	<version>4.0.0</version>
</dependency>

<dependency>
        <groupId>org.glassfish.jaxb</groupId>
        <artifactId>jaxb-runtime</artifactId>
      	<version>4.0.0</version>
</dependency>

<dependency>
        <groupId>org.javassist</groupId>
        <artifactId>javassist</artifactId>
        <version>3.29.0-GA</version>
</dependency>
```
<h2 class='heading' id='heading_mapping'>Маппинг классов в Hibernate</h2>

#### Аннотация @Entity, @Table

нужно добавить к классу аннотацию <span class = 'orange'>@Entity</span>

```java
@Entity
class User {
    public int id;
    public String name;
    public int level;
    public Date createdDate;
}
```

Вторая аннотация — <span class = 'orange'>@Table</span>. 
С ее помощью можно задать имя таблицы в базе, с которой будет связан данный класс.
```java
@Entity
@Table(name="user")
class User {
    public int id;
    public String name;
    public int level;
    public Date createdDate;
}
```
Если имя класса и имя таблицы совпадают, то аннотацию @Table можно не указывать.

Также если твое приложение работает с таблицами из нескольких схем одновременно, 
то нужно указать, в какой конкретно схеме находится таблица:
```java
@Entity
@Table(name="user", schema="test")
class User {
    public int id;
    public String name;
    public int level;
    public Date createdDate;
}
```
#### Аннотация @Column
```java
@Entity
@Table(name="user")
class User {
    @Column(name = "id")
    public Integer id;

    @Column(name = "name")
    public String name;

    @Column(name = "level")
    public Integer level;

    @Column(name = "created_date")
    public Date createdDate;
}
```
У аннотации @Column есть различные параметры, ниже мы рассмотрим самые популярные из них:

| # | 	Имя атрибута	 | Тип атрибута	 | Описание                                   |
|---|----------------|---------------|--------------------------------------------|
| 1 | 	name	         | String	       | Задает имя колонки таблицы для поля класса |
| 2 | 	unique	       | boolean	      | Все значения поля должны быть уникальны    |
| 3 | 	nullable      | 	boolean      | 	Поле может принимать значение null        |
| 4 | 	length	       | int	          | Максимальная длина (для строк)             |


Давай добавим несколько ограничений на поля нашего Entity-класса User:

* имя пользователя должно быть уникально и не длиннее 100 символов
* level может быть null
* createdDate не может быть null
* 
Тогда наши аннотации станут такими:

```java
@Entity
@Table(name="user")
class User {
   @Column(name="id")
    public Integer id;

   @Column(name="name", unique=true, length=100)
   public String name;

   @Column(name="level", nullable=true)
   public Integer level;

   @Column(name="created_date", nullable=false)
   public Date createdDate;
}
```
#### Аннотация @Id
С ее помощью можно задать первичный ключ для таблицы.
```java
@Entity
@Table(name="user")
class User {
   @Id
   @Column(name="id")
   public Integer id;

   @Column(name="name")
   public String name;

   @Column(name="level")
   public Integer level;

   @Column(name="created_date")
   public Date createdDate;
}
```
Если ты хочешь, чтобы Hibernate самостоятельно генерировал ID твоих объектов при добавлении их в базу, 
то нужно добавить еще одну аннотацию — <span class='orange'>@GeneratedValue</span>.
```java
@Entity
@Table(name="user")
class User {
   @Id
   @GeneratedValue
   public Integer id;

   @Column(name="name")
   public String name;

   @Column(name="level")
   public Integer level;

   @Column(name="created_date")
   public Date createdDate;
}
```

<h2 class = "header" id ="header_connect_to_db">Подключаемся к базе данных</h2>

* Конфигурирование подключения к базе данных
* Настройка Hibernate
* Работа с EntityManager

![picture](images/img_1.png)

Сконфигурировать Hibernate и задать ему информацию о базе данных можно тремя способами

* Использовать Properties-файл
* Использовать файл hibernate.cfg.xml
* Использовать методы бина Configuration

### Авторизация в базе данных
Начнем с самого важного – подключения базы данных. Для этого тебе нужно будет указать данные, чтобы Hibernate мог авторизоваться в нужной базе данных.

    Properties File Configuration
        hibernate.properties
            hibernate.dialect= org.hibernate.dialect.Oracle9Dialect
            hibernate.connection.driver_class= oracle.jdbc.driver.OracleDriver
            hibernate.connection.url= jdbc:oracle:thin:@localhost:1521:supershop
            hibernate.connection.username=root
            hibernate.connection.password=secret
            hibernate.show_sql=true
            hibernate.hbm2ddl=update

Эти же настройки можно задать в виде xml-файла:

    XML Based Configuration
        hibernate.cfg.xml
            <hibernate-configuration>
                <session-factory>
            
                    <property name="dialect">org.hibernate.dialect.Oracle9Dialect</property>
                    <property name="connection.driver_class">oracle.jdbc.driver.OracleDriver</property>
                    <property name="connection.url">jdbc:oracle:thin:@localhost:1521:supershop</property>
                    <property name="connection.username">root</property>
                    <property name="connection.password">secret</property>
                    <property name="hibernate.show_sql ">true</property>
                    <property name="hbm2ddl.auto">update</property> 
            
                </session-factory>
            </hibernate-configuration>

В обоих примерах мы видим одни и те же настройки с одними и теми же значениями. Просто первый пример представляет их в виде properties-файла, а второй — в виде xml-файла.

Эти настройки делятся на три группы:

1. Указываем тип СУБД
   * Указываем диалект (тип СУБД), например, Oracle 9.0
   * Указываем имя JDBC-драйвера для работы с данной СУБД
2. Указываем данные для авторизации в базе
   * url базы
   * username
   * password
3. Конфигурируем движок Hibernate
   * hibernate.show_sql — Hibernate будет дублировать в консоль все запросы, которые выполняет
   * hbm2ddl.auto — Hibernate поменяет структуру базы данных, если надо

 
Есть еще третий способ заданий конфигурации — через бины. Обычно он используется в паре со Spring, так что мы рассмотрим его, когда будем изучать SpringFramework.

### Получаем SessionFactory

Следующий этап — это получить объект SessionFactory. Есть несколько способов это сделать:

Способ первый — используем файл hibernate.properties.

```
SessionFactory sessionFactory = new Configuration().buildSessionFactory();
```

Если файл hibernate.properties не будет найден в текущей директории проекта, то кинется исключение.

Способ второй — конфигурация с помощью hibernate.cfg.xml.
```
SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
```
Если ты напишешь такой код, то в качестве источника настроек будет использован файл hibernate.cfg.xml. Если такой файл не будет найден, то метод buildSessionFactory() кинет исключение.

Способ третий — задаем файл с конфигурацией вручную.

Иногда в процессе разработки возникает необходимость переключиться на тестовую базу или поменять какие-то другие настройки для работы с базой, для этого можно задать файл конфигурации вручную:
```
SessionFactory sessionFactory = new Configuration().configure("hibernate-dev.cfg.xml").buildSessionFactory();
```
Способ четвертый — используем кастомный файл hibernate.properties:
```
ClassLoader classLoader = Thread.currentThread().getClassLoader();

Properties properties = new Properties();
properties.load(classLoader.getResourceAsStream("hibernate-dev.properties"));

SessionFactory sessionFactory = new Configuration()
.addProperties(properties)
.buildSessionFactory();
```

И наконец можно просто зашить все нужные параметры прямо в код:
```
Properties properties = new Properties();
properties.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
properties.put(Environment.URL, "jdbc:mysql://localhost:3306/supershop");
properties.put(Environment.USER, "root");
properties.put(Environment.PASS, "password");

SessionFactory sessionFactory = new Configuration()
.setProperties(properties)
.buildSessionFactory();
```

### Указываем, где искать Entity-классы
Но это еще не все. Когда мы конфигурируем объект SessionFactory в Hibernate, то этот SessionFactory проверяет, что в базе данных существуют все необходимые таблицы с колонками требуемых типов.

И чтобы SessionFactory мог это сделать, ему нужно передать список entity-классов, которые ему нужно сопоставить с базой данных.

Передать список entity-классов можно тремя способами:

Способ первый. В hibernate.cfg.xml добавить строку вида:

```xml
<mapping class="полное-имя класса" />
```
Пример:
```xml
<mapping class="com.javarush.data.User" />
<mapping class="com.javarush.data.Employee" />
<mapping class="com.javarush.data.Task" />
```
Способ второй. Вызвать у объекта Configuration метод addAnnotatedClass(). Пример:
```

SessionFactory sessionFactory = new Configuration()
.configure()
.addAnnotatedClass(com.javarush.data.User.class)
.buildSessionFactory();
```
























<style>
.heading {
color: darkgreen;
}

span.orange {
color: orange;
}
</style> 