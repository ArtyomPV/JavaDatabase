# Работа с SQL-сервером
* операторы определения данных (Data Definition Language, DDL):

    CREATE создаёт объект базы данных (саму базу, таблицу, представление, пользователя и так далее)
    ALTER изменяет объект
    DROP удаляет объект

* операторы манипуляции данными (Data Manipulation Language, DML):

    SELECT выбирает данные, удовлетворяющие заданным условиям
    INSERT добавляет новые данные
    UPDATE изменяет существующие данные
    DELETE удаляет данные

* операторы определения доступа к данным (Data Control Language, DCL):

    GRANT предоставляет пользователю (группе) разрешения на определённые операции с объектом
    REVOKE отзывает ранее выданные разрешения
    DENY задаёт запрет, имеющий приоритет над разрешением

* операторы управления транзакциями (Transaction Control Language, TCL):

    COMMIT применяет транзакцию
    ROLLBACK откатывает все изменения, сделанные в контексте текущей транзакции
    SAVEPOINT делит транзакцию на более мелкие участки

# Создание таблиц
```html
CREATE TABLE table_name (
	column1 datatype,
	column2 datatype,
	column3 datatype,
   ....
);
```
### Ключи в таблице
|                 |                                                                                           |
|-----------------|-------------------------------------------------------------------------------------------|
| PRIMARY KEY	    | Колонка является ключом таблицы                                                           |
| UNIQUE KEY	     | Все значения колонки должны быть уникальны                                                |
| DEFAULT value   | 	Значение по умолчанию                                                                    |
| NOT NULL	       | Запрет принимать значение NULL                                                            |
| AUTO_INCREMENT	 | SQL-сервер будет автоматически увеличивать значение при добавлении новой записи в таблицу |
| GENERATED	      | Вычисляемое поле                                                                          |
| STORAGE	        | Где хранить данные: на диске или в памяти                                                 |
| COMMENT         | 	Комментарий к колонке, например, название на локальном языке                             |


# Вставка данных в таблицу
```roomsql

INSERT INTO таблица (колонка1, колонка2, колонка3) VALUES
(значение1, значение2, значение3),
(значение1, значение2, значение3),
(значение1, значение2, значение3);
```
### Оператор INSERT INTO SELECT
```roomsql
INSERT INTO user (name, created_time)
SELECT employee.name, employee.join_date
FROM employee;
```

## Изменение данных в таблице

```
UPDATE таблица SET
  	колонка1 = выражение1,
  	колонка2 = выражение2,
  	колонка3 = выражение3
WHERE условие;
```

### Сложные сценарии изменение данных
```sql
UPDATE user SET
  	level = 80,
WHERE user.name IN (select name from employee);
```


<style>
span.orange{
padding: 5px;
border:1px solid orange;
color: orange
}
span.red{
color:red;
}
span.green{
color:green;
font-weight: bold;
}
</style>