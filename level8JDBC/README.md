## Транзакции при работе с базой данных

У транзакции обычно есть три состояния:

* initial state — состояние системы перед выполнением группы действий
* success state — состояние после выполнения группы действий
* failed state — что-то пошло не так

При этом обычно есть три команды:

* begin/start — выполняется перед началом логической группы действий
* commit — выполняется после группы действий транзакции
* rollback — запускает процесс возврата системы из failed state в initial state

Есть 2 способа завершить транзакцию:

* COMMIT — подтверждаем все внесенные изменения
* ROLLBACK — откатываем все внесенные изменения

Во-первых, каждый вызов метода execute() объекта Statement выполняется в отдельной транзакции. Для этого у Connection есть параметр AutoCommit. Если он выставлен в true, то commit() будет вызываться после каждого вызова метода execute().

Во-вторых, если ты хочешь выполнить несколько команд в одной транзакции, то сделать это можно так:

- отключаем AutoCommit
- вызываем наши команды
- вызываем метод commit() явно
- 
Выглядит это очень просто:
```
connection.setAutoCommit(false);

Statement statement = connection.createStatement();
int rowsCount1 = statement.executeUpdate("UPDATE  employee SET salary = salary+1000");
int rowsCount2 = statement.executeUpdate("UPDATE  employee SET salary = salary+1000");
int rowsCount3 = statement.executeUpdate("UPDATE  employee SET salary = salary+1000");

connection.commit();
```
Если во время работы метод commit() на сервере произойдет ошибка, то SQL-сервер отменит все три действия.

Но бывают ситуации, когда ошибка возникает еще на стороне клиента, и мы так и не дошли до вызова метода commit():
```
connection.setAutoCommit(false);

Statement statement = connection.createStatement();
int rowsCount1 = statement.executeUpdate("UPDATE  employee SET salary = salary+1000");
int rowsCount2 = statement.executeUpdate("UPDATE  employee SET salary = salary+1000");
int rowsCount3 = statement.executeUpdate("UPDATE  несколько опечаток приведут к исключению");

connection.commit();
```
## Работа с PreparedStatement
Виды statements
* PreparedStatement
* CallableStatement

PreparedStatement отлично помогает от популярного подхода к взлому базы данных, который называется SQL Injection.

Пример использования PreparedStatement:

```
String firstName = "Harry";
String lastName = "Potter";
String phone = "+7123456789";
String email = "test@test.com";


        try {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javarush", "root", "root");

        //Запрос с указанием мест для параметров в виде символа "?"
        String sql = "INSERT INTO contact(first_name, last_name, phone, email) VALUES (?, ?, ?, ?);";

        // Создание запроса. Переменная con — это объект типа Connection
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        // Установка параметров
        preparedStatement.setString(1, firstName);
        preparedStatement.setString(2, lastName);
        preparedStatement.setString(3, phone);
        preparedStatement.setString(4, email);

        // Выполнение запроса
        preparedStatement.executeUpdate();
        connection.close();

        } catch (SQLException e) {
        throw new RuntimeException();
        }


```


## Вызов функций SQL-сервера
### CallableStatement

У JDBC есть еще один интерфейс для еще более сложных сценариев. Он унаследован от PreparedStatement и называется CallableStatement.

Он используется для вызова (Call) хранимых процедур в базе данных. Особенность такого вызова в том, что кроме результата ResultSet такой хранимой процедуре можно еще и передать параметры.

### Batching запросов

В реальных проектах часто возникает ситуация, когда необходимо сделать очень много однотипных запросов (наиболее часто в этом случае встречается PreparedStatement), например, надо вставить несколько десятков или сотен записей.

Если выполнять каждый запрос отдельно, то это займет кучу времени и снизит производительность приложения. Чтобы не допустить этого, можно использовать batch-режим вставки. Он заключается в том, что ты накапливаешь некоторый буфер своими запросами, а потом выполняешь их сразу.

В качестве примера приведу кусочек кода:
```
PreparedStatement stmt = con.prepareStatement(
        	"INSERT INTO jc_contact (first_name, last_name, phone, email) VALUES (?, ?, ?, ?)");

for (int i = 0; i < 10; i++) {
	// Заполняем параметры запроса
	stmt.setString(1, "FirstName_" + i);
    stmt.setString(2, "LastNAme_" + i);
    stmt.setString(3, "phone_" + i);
    stmt.setString(4, "email_" + i);
	// Запрос не выполняется, а укладывается в буфер,
	//  который потом выполняется сразу для всех команд
	stmt.addBatch();
}
// Выполняем все запросы разом
int[] results = stmt.executeBatch();
```
Вместо того, чтобы выполнять запрос методом execute(), мы складываем его в пакет с помощью метода addBatch().

А затем, когда набралось несколько сотен запросов, можно их разом отправить на сервер, вызвав команду executeBatch().

Полезно. Метод executeBatch() возвращает массив целых чисел — int[]. Каждая ячейка этого массива содержит число, которое означает количество строк, измененных соответствующим запросом. Если запрос номер 3 в batch’е изменил 5 строк, то 3-я ячейка массива будет содержать число 5.


### Сохранение объектов в базу

Полный список типов данных JDBC
Кроме известных тебе типов данных JDBC позволяет работать со многими родными типами данных для СУБД. Ниже я приведу список типов и функции для получения их:

| Тип	              | Метод                 |
|-------------------|-----------------------|
| Array	            | getArray()            |
| AsciiStream	      | getAsciiStream()      |
| BigDecimal	       | getBigDecimal()       |
| BinaryStream	     | getBinaryStream()     |
| Blob	             | getBlob()             |
| Boolean	          | getBoolean()          |
| Blob	             | getBlob()             |
| Boolean	          | getBoolean()          |
| Byte	             | getByte()             |
| Bytes	            | getBytes()            |
| CharacterStream	  | getCharacterStream()  |
| Clob	             | getClob()             |
| Date	             | getDate()             |
| Double	           | getDouble()           |
| Float	            | getFloat()            |
| Int	              | getInt()              |
| Long	             | getLong()             |
| NCharacterStream	 | getNCharacterStream() |
| Object	           | getObject()           |
| Ref	              | getRef()              |
| RowId	            | getRowId()            |
| Short	            | getShort()            |
| SQLXML	           | getSQLXML()           |
| String	           | getString()           |
| Time	             | getTime()             |
| Timestamp	        | getTimestamp()        |
| UnicodeStream	    | getUnicodeStream()    |
| URL	              | getURL()              |

#### Тип данных BLOB

BLOB расшифровывается как Binary Large Object. Он используется для хранения массива байт. Тип Blob в JDBC является интерфейсом и в него можно класть (и получать) данные двумя способами:

С помощью InputStream

С помощью массива байт

Пример: колонка номер 3 содержит тип BLOB:
```
Statement statement = connection.createStatement();
ResultSet results = statement.executeQuery("SELECT * FROM user");
results.first();

    Blob blob = results.getBlob(3);
    InputStream is = blob.getBinaryStream();
```
Чтобы создать свой объект Blob, нужно воспользоваться функцией createBlob(). Пример:
```
String insertQuery = “INSERT INTO images(name, image) VALUES (?, ?)”;
PreparedStatement statement = connection.prepareStatement(insertQuery);

// Создаем объект Blob и получаем у него OtputStream для записи в него данных
Blob blob = connection.createBlob();

// Заполняем Blob данными  …
OutputStream os = blob.setBinaryStream(1);

// Передаем Вlob как параметр запроса
statement.setBlob(2, blob);
statement.execute();
```
Заполнить Blob данными можно двумя способами. Первый — через OutputSteam:

    Path avatar = Paths.get("E:\\images\\cat.jpg");
    OutputStream os = blob.setBinaryStream(1);
    Files.copy(avatar, os);

И второй — через заполнение байтами:

    Path avatar = Paths.get("E:\\images\\cat.jpg");
    byte[] content = Files.readAllBytes(avatar);
    blob.setBytes(1, content);

## Продвинутая работа с ResultSet
с помощью ResultSet можно менять строки в базе данных.

При создании объекта statement в него можно передать кучу наших пожеланий. Эти пожелания можно разделить на три группы:

* Тип связи с базой
* Управление одновременным доступом
* Сохраняемость и транзакции

Эти параметры можно передавать при создании объекта Statement или PreparedStatement. Пример:
```
Statement statement = connection.createStatement(
ResultSet.TYPE_FORWARD_ONLY,
ResultSet.CONCUR_READ_ONLY,
ResultSet.CLOSE_CURSORS_OVER_COMMIT );

PreparedStatement statement = connection.prepareStatement(sql,
ResultSet.TYPE_FORWARD_ONLY,
ResultSet.CONCUR_READ_ONLY,
```

#### Типы ResultSet

* ResultSet.TYPE_FORWARD_ONLY
* ResultSet.TYPE_SCROLL_INSENSITIVE
* ResultSet.TYPE_SCROLL_SENSITIVE

Тип по умолчанию — TYPE_FORWARD_ONLY.

__*TYPE_FORWARD_ONLY*__ означает, что ResultSet можно перемещать только вперед. То есть ты можешь перемещаться только из строки 1, строки 2, строки 3 и т. д. В ResultSet ты не можешь двигаться назад: нельзя считать данные из 9-й строки после чтения десятой.

_**TYPE_SCROLL_INSENSITIVE**_ означает, что ResultSet можно перемещать (прокручивать) как вперед, так и назад. Ты также можешь перейти к позиции относительно текущей позиции или перейти к абсолютной позиции.

ResultSet этого типа нечувствителен к изменениям в базовом источнике данных, пока ResultSet открыт. То есть если запись в ResultSet изменяется в базе данных другим потоком или процессом, она не будет отражена в уже открытых ResultSet этого типа.

_**TYPE_SCROLL_SENSITIVE**_ означает, что ResultSet можно перемещать (прокручивать) как вперед, так и назад. Ты также можешь перейти к позиции относительно текущей позиции или перейти к абсолютной позиции.

ResultSet этого типа чувствителен к изменениям в базовом источнике данных, пока ResultSet открыт. То есть если запись в ResultSet изменяется в базе данных другим потоком или процессом, она будет отражена в уже открытых ResultSet этого типа.

#### Concurrency
Параллельность ResultSet определяет, может ли ResultSet обновляться, или только считываться.

ResultSet может иметь один из двух уровней параллелизма:

* ResultSet.CONCUR_READ_ONLY
* ResultSet.CONCUR_UPDATABLE

CONCUR_READ_ONLY означает, что ResultSet может быть только прочитан.

CONCUR_UPDATABLE означает, что ResultSet может быть прочитан и изменен.


## Работа с RowSet

 Знакомство с RowSet
Как ты уже знаешь, стандарту JDBC уже почти 20 лет, и он немного устарел. В него потихоньку добавляют новые типы и новые классы, но не везде это можно сделать красиво. И одно из таких мест — это ResultSet.

Работу с базой данных можно сделать более эффективной, но интерфейс ResultSet слабо для этого подходит. Кроме того, мы нигде явно не создаем его объекты, нам их возвращает метод executeQuery().

Создатели JDBC не стали долго думать и сделали механизм, полностью параллельный всему, что было до этого. И называться он стал RowSet.

Вот его основные преимущества:

RowSet расширяет интерфейс ResultSet, поэтому его функции более мощные, чем у ResultSet.
RowSet более гибко перемещается по данным таблицы и может прокручивать назад и вперед.
RowSet поддерживает кэшированные данные, которые можно использовать даже после закрытия соединения.
RowSet поддерживает новый метод подключения, ты можешь подключиться к базе данных без подключения. Также он поддерживает чтение источника данных XML.
RowSet поддерживает фильтр данных.
RowSet также поддерживает операции соединения таблиц.
Типы RowSet:

* CachedRowSet
* FilteredRowSet
* JdbcRowSet
* JoinRowSet
* WebRowSet