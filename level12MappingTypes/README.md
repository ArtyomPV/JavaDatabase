
<h2>Маппинг примитивных типов данных</h2>
### Поддерживаемые типы данных
Все типы данных в Java можно разбить на три группы:

* Тип достаточно простой и его легко сохранить в базу данных.
* Тип сложный и для него нужно писать специальный конвертор.
* Тип очень сложный и для хранения его значений нужна отдельная таблица.

Для всех этих типов есть их аналоги в языке SQL, поэтому Hibernate хорошо знает, 
как их сохранять и загружать из базы.

Пример:
```
@Entity
@Table(name="user")
class User
{
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
Задания типа вручную – аннотация @Type
Иногда ты можешь захотеть вмешаться в политику Hibernate и явно указать ему в каком типе нужно хранить данные в базе. Например, у тебя в Entity-классе поле имеет тип Integer, но в базе для него есть колонка с типом VARCHAR.

Для этого есть специальная аннотация – @Type. Выглядит она очень просто:

<div class="frame orange">@Type(type="имя-типа")</div>
Давай, например, попросим Hibernate, чтобы поле createdDate нашего класса User хранилось в базе 
в виде строки:

```
@Entity
@Table(name="user")
class User
{
@Column(name="id")
public Integer id;

@Column(name="created_date")
@Type(type="org.hibernate.type.StringType")
public Date createdDate;
}
```
Если Hibernate поймет, как конвертировать тип Date в ваш новый тип, 
то просто сделает это. Если не поймет, тогда тебе нужно будет указать специальной конвертор типов. 
Но об этом немного позднее.

### Список Hibernate-типов для баз данных
Кстати, ты обратил внимание, что мы указали тип **org.hibernate.type.StringType**, 
а не **String**. Это потому, что мы выбрали один из типов, который поддерживает СУБД, а не язык Java. У них у каждого своя система типов. Просто разработчики Hibernate придумали удобные названия в стиле Java, вместо этих VARCHAR’ов.

Кстати, этот список не такой уж и маленький. Приведу тут его часть:

|Hibernate type (org.hibernate.type package)	|JDBC type	|Java type	|BasicTypeRegistry key(s)|
|-----|-----|-------|-------|
|StringType	|VARCHAR	|java.lang.String	|string, java.lang.String|
|MaterializedClob	|CLOB	|java.lang.String	|materialized_clob|
|TextType	|LONGVARCHAR	|java.lang.String	|text|
|CharacterType	|CHAR	|char, java.lang.Character	|char, java.lang.Character|
|BooleanType	|BIT	|boolean, java.lang.Boolean	|boolean, java.lang.Boolean|
|NumericBooleanType	|INTEGER, 0 is false, 1 is true|	boolean, java.lang.Boolean	|numeric_boolean|
|YesNoType	|CHAR, 'N'/'n' is false, 'Y'/'y' is true. The uppercase value is written to the database.|	boolean, java.lang.Boolean	|yes_no|
|TrueFalseType	|CHAR, 'F'/'f' is false, 'T'/'t' is true. The uppercase value is written to the database.	|boolean, java.lang.Boolean	|true_false|
|ByteType	|TINYINT	|byte, java.lang.Byte	|byte, java.lang.Byte|
|ShortType	|SMALLINT	|short, java.lang.Short	|short, java.lang.Short|
|IntegerTypes	|INTEGER	|int, java.lang.Integer	|int, java.lang.Integer|
|LongType	|BIGINT	|long, java.lang.Long	|long, java.lang.Long|
|FloatType	|FLOAT	|float, java.lang.Float	|float, java.lang.Float|
|DoubleType	|DOUBLE	|double, java.lang.Double	|double, java.lang.Double|
|BigIntegerType	|NUMERIC	|java.math.BigInteger	|big_integer, java.math.BigInteger|
|BigDecimalType	|NUMERIC	|java.math.BigDecimal	|big_decimal, java.math.bigDecimal|
|TimestampType	|TIMESTAMP	|java.sql.Timestamp	|timestamp, java.sql.Timestamp|
|TimeType	|TIME	|java.sql.Time	|time, java.sql.Time|
|DateType	|DATE	|java.sql.Date	|date, java.sql.Date|
|CalendarType	|TIMESTAMP	|java.util.Calendar	|calendar, java.util.Calendar|
|CalendarDateType	|DATE	|java.util.Calendar	|calendar_date|
|CurrencyType	|java.util.Currency	|VARCHAR	|currency, java.util.Currency|
|LocaleType	|VARCHAR|	java.util.Locale	|locale, java.utility.locale|
|TimeZoneType	|VARCHAR, using the TimeZone ID	|java.util.TimeZone	|timezone, java.util.TimeZone|
|UrlType	|VARCHAR	|java.net.URL	|url, java.net.URL|
|ClassType	|VARCHAR (class FQN)	|java.lang.Class	|class, java.lang.Class|


Таблица, конечно, большая, но очень полезная. Например, из нее понятно, 
что тип Boolean можно сохранить в базу как минимум шестью разными способами. 
Тебе столько не нужно? А кто сказал, что способ сохранения выбираешь ты?

В SQL нет типа Boolean и его часто хранят так:

* 1 или 0
* ‘F’ или ‘T’
* ‘Y’ или ‘N’

Поэтому очень хорошо, когда Hibernate понимает все эти заморочки.
Или, например, давай возьмем хранение массивов данных в базе. 
Есть куча разных вариантов и Hibernate умеет работать с ними со всеми:

| Hibernate type (org.hibernate.type package) | 	JDBC type	                                                                                     | Java type	                                                                   | BasicTypeRegistr                                        |
|---------------------------------------------|-------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------|---------------------------------------------------------|
| BlobType	                                   | BLOB	                                                                                           | java.sql.Blob	                                                               | blog, java.sql.Blob                                     |
| ClobType                                    | 	CLOB	                                                                                          | java.sql.Clob	                                                               | clob, java.sql.Clob                                     |
| BinaryType	                                 | VARBINARY	                                                                                      | byte[]                                                                       | 	                                        binary, byte[] |
| MaterializedBlobType	                       | BLOB	                                                                                           | byte[]	                                                                      | materized_blob                                          |
| ImageType	                                  | LONGVARBINARY	                                                                                  | byte[]                                                                       | 	image                                                  |
| WrapperBinaryType	                          | VARBINARY	                                                                                      | java.lang.Byte[]	                                                            | wrapper-binary, Byte[], java.lang.Byte[]                |
| CharArrayType	                              | VARCHAR	                                                                                        | char[]                                                                       | 	characters, char[]                                     |
| CharacterArrayType	                         | VARCHAR	                                                                                        | java.lang.Character[]	wrapper-characters, Character[], java.lang.Character[] |                                                         |
| UUIDBinaryType	                             | BINARY	                                                                                         | java.util.UUID	                                                              | uuid-binary, java.util.UUID                             |
| UUIDCharType	                               | CHAR, can also read VARCHAR	                                                                    | java.util.UUID	                                                              | uuid-char                                               |
| PostgresUUIDType                            | 	PostgreSQL UUID, through Types#OTHER, which complies to the PostgreSQL JDBC driver definition	 | java.util.UUID	                                                              | pg-uuid                                                 |

А после выпуска JDK 8 в Hibernate добавилось еще несколько типов, связанных со временем. 
И все с целью упростить тебе жизнь. Тебе больше не нужно думать,
поддерживаются ли все эти новомодные типы. Создатели Hibernate уже добавили их поддержку для тебя:

| Hibernate type (org.hibernate.type package)	 | JDBC type	 | Java type                  | 	BasicTypeRegistr                        |
|----------------------------------------------|------------|----------------------------|------------------------------------------|
| DurationType	                                | BIGINT	    | java.time.Duration	        | Duration, java.time.Duration             |
| InstantType                                  | 	TIMESTAMP | 	java.time.Instant	        | Instant, java.time.Instant               |
| LocalDateTimeType	                           | TIMESTAMP	 | java.time.LocalDateTime    | 	LocalDateTime, java.time.LocalDateTime  |
| LocalDateType	                               | DATE	      | java.time.LocalDate	       | LocalDate, java.time.LocalDate           |
| LocalTimeType	                               | TIME       | 	java.time.LocalTime	      | LocalTime, java.time.LocalTime           |
| OffsetDateTimeType	                          | TIMESTAMP  | 	java.time.OffsetDateTime	 | OffsetDateTime, java.time.OffsetDateTime |
| OffsetTimeType                               | 	TIME	     | java.time.OffsetTime	      | OffsetTime, java.time.OffsetTime         |
| OffsetTimeType	                              | TIMESTAMP	 | java.time.ZonedDateTime	   | ZonedDateTime, java.time.ZonedDateTime   |

<h2>Полезные сценарии маппинга данных</h2>

### Мапим enum

Как мапить примитивные типы данных мы уже разобрались: 
используем аннотации @Column и аннотацию @Type.
Но не все случаи можно покрыть этими аннотациями. И самый частый случай – это enum.

Java-объекты типа enum могут храниться в базе в двух вариантах:

* в виде числа
* в виде строки

Давай напишем небольшой пример, где у пользователя будет любимый цвет, 
который задается с помощью enum.

Давай напишем небольшой пример, где у пользователя будет любимый цвет, который задается с помощью enum.
```
enum Color {
RED,
ORANGE,
YELLOW,
GREEN,
BLUE,
VIOLET
}
```
И добавим поле с цветом в класс User:
```
@Entity
@Table(name="user")
class User
{
@Column(name="id")
public Integer id;

@Column(name="favorite_color")
public Color favoriteColor;

@Column(name="created_date")
public Date createdDate;
}
```
Если мы хотим, чтобы Hibernate сохранял тип Color в базу как числа, 
то нужно полю favoriteColor добавить аннотацию:

<span class = "frame orange">@Enumerated(EnumType.ORDINAL)</span>
Если мы хотим, чтобы значения хранились как строки, то нужно добавить аннотацию:

<span class = "frame orange">@Enumerated(EnumType.STRING)</span>

Пример:
```
@Entity
@Table(name="user")
class User
{
@Column(name="id")
public Integer id;

@Enumerated(EnumType.ORDINAL) //значение будет сохранено в базу как число
@Column(name="favorite_color")
public Color favoriteColor;

@Column(name="created_date")
public Date createdDate;
}
```

### Мапим Boolean
Второй полезный сценарий – это маппинг типа Boolean. Так уж исторически сложилось, 
что в SQL нет своего типа данных для Boolean и вместо него там используют все что угодно.

Три самых распространенных варианта:

* 1 или 0
* ‘F’ или ‘T’
* ‘Y’ или ‘N’

В общем, если ты будешь проектировать свою базу, то лучше сразу пиши тип BIT. 
Ну а полный маппинг для него будет выглядеть так:
```
@Column(name = "is_correct", columnDefinition = "BIT")
@Type(type = "org.hibernate.type.NumericBooleanType")
private Boolean isCorrect;
```
Ну, а если базу данных проектируешь не ты, то смотри таблицу выше и думай, 
как правильно замапить нужные тебе типы.

### Вычисляемые поля
Иногда количество полей в Entity-классе и количество колонок в таблице не совпадают.
У этого может быть несколько причин.

Самая распространенная – это когда в нашем Entity-классе есть какое-то поле, 
которое мы не хотим сохранять в базу данных. С этим все понятно – просто добавь 
такому полю аннотацию @Transient и Hibernate проигнорирует его при работе с базой данных.

Пример:
```
@Entity(name = "Square")
public class Square {
@Id
public Long id;

           	public Integer width;

           	public Integer height;

           	@Transient
           	public Integer total;
}
```
Это хороший вариант, но при чтении объекта из базы поле total будет null. А нам бы хотелось, чтобы оно содержало произведение width*height. Такое тоже можно сделать в Hibernate. Для этого есть специальная аннотация @Formula.
```
@Entity(name = "Square")
public class Square {
@Id
public Long id;

           	public Integer width;

           	public Integer height;

           	@Formula(value = " width * height ")
          	public Integer total;
}
```
Такое поле не будет сохраняться в базу данных, 
а при чтении объекта из базы в него будет записываться значение вычисленное по формуле.

SQL-запрос будет выглядеть примерно так:
<div class="frame orange">
SELECT id, width, height, (width* height) AS total FROM Square;
</div>

### @Embedded
Еще одна полезная аннотация – это @Embedded. Она позволяет рассматривать поля дочернего объекта как поля самого Entity-класса.

Допустим, у тебя есть класс User и ты решил добавить в него адрес:
```
@Entity
@Table(name="user")
class User
{
@Column(name="id")
public Integer id;

@Column(name="user_address_country")
public String country;
@Column(name="user_address_city")
public String city;
@Column(name="user_address_street")
public String street;
@Column(name="user_address_home")
public String home;

@Column(name="created_date")
public Date createdDate;
}
```
Все вроде бы хорошо, но с точки зрения Java было бы логично вынести адрес в отдельный класс. 
Адрес все-таки отдельная сущность. Но как это сделать, если в базе вся эта 
информация хранится именно в таблице user?

Нам поможет аннотация @Embedded. Сначала мы создадим класс UserAddress и 
вынесем в него всю информацию по адресу пользователя:
```
@Embeddable
class UserAddress
{
@Column(name="user_address_country")
public String country;
@Column(name="user_address_city")
public String city;
@Column(name="user_address_street")
public String street;
@Column(name="user_address_home")
public String home;
}
```
А затем используем поле этого класса в нашем классе User:
```
@Entity
@Table(name="user")
class User
{
@Column(name="id")
public Integer id;

@Embedded
public UserAddress address;

@Column(name="created_date")
public Date createdDate;
}
```
Благодаря аннотации @Embedded во время сохранения объекта Hibernate поймет, 
что поля класса UserAddress нужно обрабатывать как поля самого класса User.

Важно! Если ты решишь добавить в свой класс User два поля UserAddress, 
то использовать @Embedded уже не получится: у тебя будет дубликат полей 
и тебе нужно будет как-то их разделить. Делается это с помощью переопределения аннотаций:
с помощью аннотации @AttributeOverrides.

Я хочу, чтоб ты это знал, но детально тут мы это все разбирать не будем. 
Думаю, тебе и этого хватит, чтобы голову поломать. 
Для любопытных могу оставить ссылку на официальную документацию.


<h2>Первичный ключ</h2>

### Аннотация @Id
Каждая Entity-сущность в Hibernate должна иметь поле, которое будет первичным ключом: 
содержать уникальное значение для всех объектов данного класса. Обычно это поле помечается аннотацией @Id.

Общий вид:
<div class="frame orange">
@Id
Класс имя;
</div>

Пример:
```
@Entity
@Table(name="user")
class User
{
@Id
@Column(name="id")
public Integer id;

@Embedded
public UserAddress address;

@Column(name="created_date")
public Date createdDate;
}
```
### Размещение аннотаций
Кстати, ты можешь писать аннотации @Column не только у полей, но и методов: у геттеров или сеттеров. Пример:

```
@Entity
@Table(name="user")
class User
{
public Integer id;
public Date createdDate;

@Id
@Column(name="id")
public Integer getId() {
return this.id;
}
public void setId(Integer id)    {
this.id = id;
}

@Column(name="created_date")
public Date getCreatedDate() {
return this.createdDate;
}
public void setCreatedDate(Date date) {
this.createdDate = date;
}
}
```
Такой подход появился, когда аннотации начали добавлять не только у классов, но и у интерфейсов. 
Полей класса у интерфейса нет, зато есть методы: геттеры и сеттеры. Hibernate поддерживает оба этих стандарта.

Важно! Если у класса есть аннотация @Entity, то все его поля рассматриваются Hibernate как сохраняемые поля 
(если у них не указана аннотация @Transient). Даже если у полей нет вообще никаких аннотаций: 
при этом имя колонки считается равным имени поля класса.

И тут аннотация @Id играет важную роль. Если аннотация размещена у поля класса, 
то Hibernate будет смотреть на имена и типы полей. Если аннотация @Id размещена у метода, 
тогда Hibernate будет смотреть на имена и типы методов.

Пример 1:
```
@Entity
@Table(name="user")
class User
{
@Id
public Integer id;
public Date createdDate;  //это поле будет обработано как будто тут есть @Column(name="createdDate")

}
```
Пример 2:
```
@Entity
@Table(name="user")
class User
{
public Integer id;
public Date createdDate;

@Id
public Integer getId() {
return this.id;
}
public void setId(Integer id)    {
this.id = id;
}

public Date getCreatedDate() { //это поле будет обработано как будто тут есть @Column(name="createdDate")
return this.createdDate;
}
public void setCreatedDate(Date date) {
this.createdDate = date;
}

}
```
### Аннотация @GeneratedValue
Ты можешь присваивать id твоим новым объектам самостоятельно, или отдать это на откуп Hibernate.
Для того чтобы Hibernate лучше понимал, как присваивать ID твоим объектам, у него есть специальная аннотация:

**@GeneratedValue**
Этой аннотацией обычно помечается то же поле, что и аннотацией @Id. У нее есть 4 возможных стратегии присвоения ID:

* AUTO
* IDENTITY
* SEQUENCE
* TABLE

Пример аннотации с указанной стратегией:
```
@Entity
@Table(name="user")
class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    public Date createdDate;
}
```
Если значение стратегии не указано, то выберется стратегия AUTO. Стратегия AUTO означает, 
что Hibernate будет сам назначать ID, основываясь в первую очередь на типе данных у поля ID.

Почему именно на типе? Да потому, что типы у ID могут быть очень разные, например, String или GUID. Пример:
```
@Entity
@Table(name="user")
class User
{
@Id
@GeneratedValue
public UUID id;

    public Date createdDate;
}
```
Примечание: GUID в Java называется UUID, так исторически пошло. И Hibernate умеет генерировать уникальные UUID для твоих объектов, если ты его об этом попросишь.

Различные стратегии @GeneratedValue
Если ты укажешь тип GeneratedValue(strategy = GenerationType.IDENTITY), то Hibernate делегирует установку 
ID на уровень базы данных. Обычно при этом используется колонка, помеченная как PRIMARY KEY, AUTOINCREMENT.

А вот если ты хочешь, чтобы твои ID были уникальными и генерировались по специально заданному алгоритму,
то можешь воспользоваться аннотацией GeneratedValue(strategy = GenerationType.SEQUENCE), пример:

```
@Entity
@Table(name="user")
public class User {
@Id
@GeneratedValue(generator = "sequence-generator")
@GenericGenerator(
name = "sequence-generator",
strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
parameters = {
@Parameter(name = "sequence_name", value = "user_sequence"),
@Parameter(name = "initial_value", value = "4"),
@Parameter(name = "increment_size", value = "1")
}
)
private long userId;

	// ...
}
```
Есть еще много разных способов генерации ID. Например, у тебя может быть составной уникальный ключ,
состоящий из нескольких колонок. И при записи объекта в базу, нужно все эти колонки заполнить.

Я не буду их подробно приводить. Все-таки задача наших лекций – это знакомство с Hibernate, 
а не пересказ официальной документации.


<h2>Маппинг дат</h2>

### 4.1 Экскурс в историю
Задача по сохранению Java-объектов в базу данных была актуальна чуть ли не сразу же после создания языка Java.
В тот момент в языке Java был только один тип данных – Date, который хранил время по стандарту UNIX-time: 
как количество миллисекунд, прошедших с 1970 года.

Ну а в базах данных в то время были уже различные типы данных для дат, как минимум были отдельные типы для даты, 
времени и дата+время:

* DATE
* TIME
* TIMESTAMP

Поэтому создатели языка Java добавили в него специальный пакет – java.sql, который содержал классы:

* java.sql.Date
* java.sql.Time
* java.sql.Timestamp

Мапить такие классы сплошное удовольствие:
```
@Entity
public class TemporalValues {

	@Basic
    private java.sql.Date sqlDate;

	@Basic
    private java.sql.Time sqlTime;

    @Basic
    private java.sql.Timestamp sqlTimestamp;
}
```
Но так как программистам раньше приходилось работать с классом java.util.Date, 
то в Hibernate добавили специальную аннотацию @Temporal для того, чтобы управлять маппингом типа Date.

Пример:
```
// Если аннотация отсутствует, то в базе будет тип TIMESTAMP
Date dateAsTimestamp;

@Temporal(TemporalType.DATE) // будет замаплен на тип DATE
Date dateAsDate;

@Temporal(TemporalType.TIME) // будет замаплен на тип TIME
Date dateAsTime;
```
Для типа java.util.Calendar и типа java.util.Date по умолчанию используется тип TIMESTAMP для их представления в базе данных.

### 4.2 Новое время
В нынешнее время с маппингом все обстоит намного проще и лучше. Все базы данных поддерживают 4 типа данных для работы со временем:

    DATE – дата: год, месяц и день.
    TIME – время: часы, минуты, секунды.
    TIMESTAMP – дата, время и наносекунды.
    TIMESTAMP WITH TIME ZONE – TIMESTAMP и временная зона (имя зоны или смещение).
Для того, чтобы представить тип DATE в Java, нужно использовать класс java.time.LocalDate из JDK 8 DateTime API.

Тип TIME из базы данных можно представить двумя типами из Java: java.time.LocalTime и java.time.OffsetTime. Ничего сложного.

А точную дату и время, представленную типом TIMESTAMP в базе, в Java можно представить 4 типами:

* java.time.Instant
* java.time.LocalDateTime
* java.time.OffsetDateTime
* java.time.ZonedDateTime

Ну и наконец TIMESTAMP WITH TIME ZONE можно представить двумя типами:

* java.time.OffsetDateTime
* java.time.ZonedDateTime
* 
Так как ты уже знаком с DateTime API, то запомнить это дело тебе труда не составит :)

Мапить их сплошное удовольствие:
```
@Basic
private java.time.LocalDate localDate;

@Basic
private java.time.LocalTime localTime;

@Basic
private java.time.OffsetTime offsetTime;

@Basic
private java.time.Instant instant;

@Basic
private java.time.LocalDateTime localDateTime;

@Basic
private java.time.OffsetDateTime offsetDateTime;

@Basic
private java.time.ZonedDateTime zonedDateTime;
```
Аннотация @Basic означает, что поле должно быть обработано автоматически: Hibernate сам решит на какую колонку и тип должно быть замаплено данное поле.

### 4.3 Работа с временными зонами
Если временная зона является частью даты, то хранить их в базе просто – просто как обычную дату:

@Basic
private java.time.OffsetDateTime offsetDateTime;
```
@Basic
private java.time.ZonedDateTime zonedDateTime;
```
Однако, если ты хочешь хранить временные зоны отдельно от даты:
```
@Basic
private java.time.TimeZone timeZone;

@Basic
private java.time.ZoneOffset zonedOffset;
```
То Hibernate по умолчанию будет хранить их в типе VARCHAR. Что, собственно, логично, так как TimeZone обычно имеет строковое имя типа "UTC+3" или "Cairo".

### 4.4 Установка своей временной зоны
Когда ты будешь работать с сохранением дат в базу данных, то столкнешься с тем, что есть аж 4 места, где можно задать текущую временную зону:

* Операционная система сервера;
* СУБД;
* Java-приложение;
* Hibernate.

Если в СУБД не указана временная зона (TimeZone), то она возьмет ее из настроек операционной системы. 
Это может быть неудобно, так как резервные СУБД часто размещают в других датацентрах, у которых своя временная зона.

Поэтому почти у всех СУБД админы задают единую зону, чтобы данные можно было легко переносить с одного сервера на другой.

Похожая ситуация и с Java-приложением. Оно тоже может запускаться на различных серверах в разных датацентрах,
поэтому обычно ему задают временную зону явно.

<div class="frame orange">
java -Duser.timezone=UTC ...
</div>
или во время работы программы:
<div class="frame orange">
TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
</div>
, конечно, Hibernate позволяет задать свою временную зону явно.

Во-первых, ее можно указать при конфигурировании SessionFactory:
```
settings.put(
AvailableSettings.JDBC_TIME_ZONE,
TimeZone.getTimeZone("UTC")
);
```
Во-вторых, временную зону можно указать для конкретной сессии:
```
Session session = sessionFactory()
.withOptions()
.jdbcTimeZone(TimeZone.getTimeZone("UTC"))
.openSession();
```
### 4.5 Аннотация @TimeZoneStorage
Часто бывает ситуация, что программисты начали проектировать базу из расчета работы в одной стране 
(и одной временной зоне), а затем спустя пару лет им понадобилось добавить поддержку работы в разных временных зонах.

Поэтому они просто добавили в базу отдельную колонку для хранения временной зоны.
Это настолько частая ситуация, что Hibernate добавил специальную аннотацию, которая позволяет хранить 
TimeZone конкретной даты в отдельной колонке.

Пример:
```
@TimeZoneStorage(TimeZoneStorageType.COLUMN)
@TimeZoneColumn(name = "birthday_offset_offset")
@Column(name = "birthday_offset")
private OffsetDateTime offsetDateTimeColumn;

@TimeZoneStorage(TimeZoneStorageType.COLUMN)
@TimeZoneColumn(name = "birthday_zoned_offset")
@Column(name = "birthday_zoned")
private ZonedDateTime zonedDateTimeColumn;
```
Это костыль. Но есть ему и оправдание: он появился еще во времена, когда DateTime API еще не было. 
А в классе java.util.Date нельзя было хранить TimeZone.

Очень надеюсь, что ты не часто будешь встречать такое в своем коде.



<h2 class="green">Маппинг объектов</h2>

### 6.1 Храним файлы на сервере
Иногда в базу данных нужно сохранить бинарные объекты. Например, файлы. 
Если файл большой, то разумнее всего хранить его в отдельной папке на диске,
а в базе данных хранить его пути. Пример:
<div class="frame orange">
c:\db-files\users\12355\avatar.jpg
</div>
И в базе храним просто относительный путь к файлу:
<div class="frame orange">
\12355\avatar.jpg
</div>
В базе удобно хранить относительный путь, так как из него потом легко получить URL:
<div class="frame orange">
https://storage.javarush.ru/users/12355/avatar.jpg
</div>
Мы просто приклеиваем относительный путь к имени сервера и готово.

### 6.2 Храним картинки прямо в базе
Однако, если картинки маленькие, их можно хранить прямо в базе и 
отдавать клиенту как набор байт. Для таких случаем в SQL есть специальный тип данных 
BLOB – Binary Large Object. Вернее, их даже два:

* CLOB – Character Large Object,
* BLOB – Binary Large Object.

CLOB используется для хранения очень больших текстов. А BLOB для хранения бинарных данных, таких как небольшие картинки, видео и тому подобное.

Пример:
```
@Entity
@Table(name="user")
public class User {

    @Id
    private String id;

	@Column(name = "name", columnDefinition="VARCHAR(128)")
    private String name;

	@Lob
	@Column(name = "photo", columnDefinition="BLOB")
    private byte[] photo;

	// ...
}
```
Аннотация @Lob подсказывает Hibernate, что в поле хранится Large Object. 
А columnDefinition="BLOB" уже говорит о том, как это все сохранить в базе.

Давай напишем пример кода, который сохраняет в базу нового пользователя и его фото:
```
byte[] imageBuffer = Files.readAllBytes(Paths.get("C:/temp/avatar.png"))

User user = new User();
user.setId("1");
user.setName("Zapp");
user.setPhoto(imageBuffer);

session.persist(user);
```
### 6.3 XML и JSON
Hibernate имеет интересную поддержку JSON в качестве типа данных. 
Он позволяет сохранить HashMap строк в виде единого JSON-объекта. 
Если СУБД умеет работать с JSON, то выглядит это так:
```
@JdbcTypeCode(SqlTypes.JSON)
private Map<String, String> properties;
```
Hibernate берет на себя заботу, чтобы объект типа Map<String, String> был сериализован 
в единый JSON-объект. Также при чтении объекта из базы превращает JSON-объект в набор Map<String, String>.

Нечто похожее Hibernate может проделать и с XML:
```
@JdbcTypeCode(SqlTypes.SQLXML)
private Map<String, String> properties;

```

<h2 class="green">Использование различных конвертеров типов данных</h2>

### 7.1 Создание собственного конвертера типов
Иногда возникают ситуации, когда ты хочешь сохранить в одну колонку таблицы достаточно 
сложный тип данных. Если Hibernate знает, как преобразовать его в строку (и обратно), 
то все отлично. Если же нет, то тебе придется написать собственный конвертер данных.

Допустим, кто-то решил хранить год рождения пользователя в базе в виде ГГ.ММ.ДД, 
например: 98.12.15. Тебе же нужно преобразовать его к обычной дате: 15/12/1998.
Тогда придется написать собственный конвертер.

Для этого нужно реализовать интерфейс AttributeConverter<EntityType, DbType>.
```
@Converter(autoApply = true)
public class DateConverter implements AttributeConverter<java.time.LocalDate, String> {

    public String convertToDatabaseColumn(java.time.LocalDate date) {
    	return date.format("YY.MM.DD");
    }

    public java.time.LocalDate convertToEntityAttribute(String dbData) {
    	String[] data = dbData.split(".");
    	return LocalDate.of(data[2], data[1], "19"+data[0]);
    }
}
```
И, конечно, этот конвертер можно добавить к любому полю (при условии совпадения типов):
```
@Entity
@Table(name="user")
class User {
@Id
@Column(name="id")
public Integer id;

@Column(name="join_date")
@Convert(converter = DateConverter.class)
public java.time.LocalDate date;
}
```
Конвертеры очень часто приходится использовать, если не ты проектировал базу данных. 
Данные там могут находиться в "странных форматах". Даты могут храниться как строки, 
Boolean как CHAR со значениями Y и N и тому подобное.

### 7.2 Создаем собственный тип данных
Помнишь таблицу со списком типов, которые известны Hibernate? Я про типы, 
которые указываются вместе с аннотацией @Type. 
Ты можешь написать свой собственный тип данных, который можно так же использовать, 
как и остальные встроенные типы в Hibernate.

Например, мы хотим иметь тип LocalTime, который будет сохраняться в базу не как TIME, 
а как VARCHAR. И, например, у нас есть доступ к такой базе, и нам не разрешают 
поменять типы данных в ее колонках. Тогда мы можем написать свой Hibernate-тип. 
Назовем его LocalTimeString.

Для начала нам понадобится небольшой класс, который будет описывать наш новый тип:
```
public class LocalTimeStringType extends AbstractSingleColumnStandardBasicType<<LocalTime> {

    public static final LocalTimeStringType  INSTANCE = new LocalTimeStringType ();

    public LocalTimeStringType () {
    	super(VarcharTypeDescriptor.INSTANCE, LocalTimeStringJavaDescriptor.INSTANCE);
    }

    @Override
    public String getName() {
    	return "LocalTimeString";
    }
}
```
Это что-то типа Enum, который состоит из одного значения. Набор таких одиноких энамов 
и есть все типы известные Hibernate.

Также нам понадобится класс – аналог конвертера, который будет содержать 
два метода – wrap() и unwrap() для преобразования значений типа LocalTime в String.

Вот так он будет выглядеть без реализации методов:
```
public class LocalTimeStringJavaDescriptor extends AbstractTypeDescriptor<LocalTime> {

    public static final LocalTimeStringJavaDescriptor INSTANCE =  new  LocalTimeStringJavaDescriptor();

    public LocalTimeStringJavaDescriptor() {
    	super(LocalTime.class, ImmutableMutabilityPlan.INSTANCE);
    }

    public <X> X unwrap(LocalTime value, Class<X> type, WrapperOptions options) {

    }

    public <X> LocalTime wrap(X value, WrapperOptions options) {

    }

}
```
А теперь напишем реализацию методов:
```
public <X> X unwrap(LocalTime value, Class<X> type, WrapperOptions options) {

    if (value == null)
    	return null;

    if (String.class.isAssignableFrom(type))
    	return (X) LocalTimeType.FORMATTER.format(value);

    throw unknownUnwrap(type);
}
```
И второй метод:
```
@Override
public <X> LocalTime wrap(X value, WrapperOptions options) {
if (value == null)
return null;

    if(String.class.isInstance(value))
    	return LocalTime.from(LocalTimeType.FORMATTER.parse((CharSequence) value));

    throw unknownWrap(value.getClass());
}
```
Готово. Можно использовать этот класс для хранения времени в виде строки:
```
@Entity
@Table(name="user")
class User
{
@Id
@Column(name="id")
public Integer id;

@Column(name="join_time")
@Type(type = "com.javarush.hibernate.customtypes.LocalTimeStringType")
public java.time.LocalTime time;
}
```
### 7.3 Регистрация своего типа
Также ты можешь зарегистрировать свой тип данных во время конфигурирования Hibernate. 
Это немного нетривиально.
```
ServiceRegistry serviceRegistry = StandardServiceRegistryBuilder()
.applySettings(getProperties()).build();

    MetadataSources metadataSources = new MetadataSources(serviceRegistry);
    Metadata metadata = metadataSources
  	.addAnnotatedClass(User.class)
  	.getMetadataBuilder()
  	.applyBasicType(LocalTimeStringType.INSTANCE)
  	.build();

    SessionFactory factory =  metadata.buildSessionFactory();
```
Тебе сначала нужно будет получить MetadataSources, из нее получить MetadataBuilder, 
а уже с помощью него добавлять свой класс. Можно и через hibernate.cfg.xml, 
но тоже немного громоздко.

Зато после регистрации ты сможешь писать так:
```
@Entity
@Table(name="user")
class User
{
@Id
@Column(name="id")
public Integer id;

@Column(name="join_time")
@Type(type = "LocalTimeString")
public java.time.LocalTime time;
}
```






<style>
.bold{
text-weight:800;
}
.orange { color:orange;}
.frame {

margin: 0 auto;
padding: 5px;
border:1px solid orange;
text-align: center;
}

</style>