# Условные функции
## 1.1 Список условных функций

|   	 |   Функции |                               	Описание |
|----:|----------:|----------------------------------------:|
|  1	 |     CASE	 |                           Аналог switch |
|  2	 |     IF()	 | Аналог тернарного оператора или if-else |
|  3	 | IFNULL()	 |                          Аналог if-else |
|  4	 | NULLIF()	 |                          Аналог if-else |

### 1.2 Функция IF()

IF (условие, истинное, ложное)

В функцию IF нужно передать три выражения:
* условие, которое проверяется на истинность;
* выражение, которое возвращается, когда условие истинно;
* выражение, которое возвращается, когда условие ложно.

Example:

|     | Запрос                                         | Результат |
|:----|:-----------------------------------------------|:----------|
| 1   | SELECT IF(1>2, 2, 3)                           | 3         |
| 2   | SELECT IF(1<2, 'yes', 'no')                    | 'yes'     |
| 3   | SELECT IF(STRCMP('test','test1'), 'no', 'yes') | 'no'      |

### 1.3 Функция IFNULL() и NULLIF()

<span class="orange">IFNULL (выражение1, выражение2)</span>

Если выражение1 не равно NULL, тогда функция вернет выражение1. Если же выражение1 IS NULL, то функция вернет выражение2. Суть – подстановка значения по умолчанию, когда поле равно NULL.

Вторая модификация функции IF – это функция NULLIF(), она тоже принимает два значения:

<span class="orange">NULLIF (выражение1, выражение2)</span>

Она работает в противоположном направлении:

если выражение1 равно выражение2, то функция возвращает NULL;
если выражения не равны, то возвращает выражение1.

## Оператор CASE

## 2.1 Оператор CASE
```sql
CASE case_value
	WHEN value1 THEN result1
	[WHEN value2 THEN result2] ...
	[ELSE resultN]
END
```

| SQL	        | Java                     |
|:------------|:-------------------------|
| case x      | 	switch (x) {            |
| when value  | 	case value:             |
| then result | 	return result;          |
| else result | 	default: return result; |
| end         | 	}                       |

Example:

Java	
```java
switch (a) {
case 1: return "one";
case 2: return "two";
case 3: return "three";
default: return "unknown";
}
```
SQL
```sql
CASE a
WHEN 1 THEN 'one'
WHEN 2 THEN 'two'
WHEN 3 THEN 'three'
ELSE 'unknown'
END
```
## 2.2 Второй вариант оператора CASE
```html
CASE
	WHEN условие1 THEN result1
	[WHEN условие2 THEN result2] ...
	[ELSE resultN]
END
```
Пример запроса:
```html
SELECT
    id,
    emploee_id,
    CASE WHEN deadline < CURDATE() THEN CONCAT('EXPIRED!', name) ELSE name END AS name,
    deadline         	
FROM task
```

# Группировка данных
## Оператор GROUP BY
он позволяет выполнять группировку данных во время запроса.

<span class="orange">GROUP BY колонка</span>

Запрос: 
```
SELECT brand, COUNT(*)
FROM product
GROUP BY brand
```
запрос выводит два столбца наименование бренда и количество продуктов каждого бренда

<span class="green">COUNT(*)</span> – это так называемая агрегатная функция, которая выполняется над группой строк. В нашем случае она просто считает их количество. Таких функций очень много, и мы рассмотрим их немного позднее.

## 3.2 Сложные запросы с GROUP BY
```html
SELECT brand, COUNT(*)
FROM product
WHERE price > 10
GROUP BY brand
```

сначала применяется WHERE для фильтрации нужных строк, а уже потом к полученному результату применяете группировку.

## 3.3 Имена колонок у результата
имя count(*)? Давай заменим его на total. Выглядеть такой запрос будет примерно так:

```html
SELECT brand, COUNT(*) AS total
FROM product
WHERE price > 10
GROUP BY brand
```

Также ты можешь переименовать и существующие колонки. Давай возьмем один из уже использованных запросов и добавим колонкам новые имена.

```html
SELECT
id AS  product_id,
name AS product_name,
price
FROM product
WHERE price < 20 AND brand IS NOT NULL
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