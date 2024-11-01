create schema hibernate13_ManyToMany;

drop table if exists employee;

create table employee(
id serial primary key,
name varchar(100),
occupation varchar(255),
salary int, 
age int, 
join_date date
);


insert into employee (name, occupation, salary, age, join_date) values 
('Иванов Иван',	'Программист',	100000,	25,	'2012-06-30'),
('Петров Петр',	'Программист',	80000,	23,	'2013-08-12'),
('Иванов Сергей',	'Тестировщик',	40000,	30,	'2014-01-01'),
('Рабинович Мойша',	'Директор',	200000,	35,	'2015-05-12'),
('Кириенко Анастасия',	'Офис-менеджер',	40000,	25,	'2015-10-10'),
('Васька',	'Кот',	1000,	3,	'2018-11-11');

select * from employee;

drop table if exists task;

create table task(
id serial primary key,
employee_id int, 
name varchar(100),
deadline date
);

insert into task(employee_id, name, deadline) values
(1,	'Исправить багу на фронтенде', '2022-06-01'),
(2,	'Исправить багу на бэкенде', '2022-06-15'),
(5,	'Купить кофе', '2022-07-01'),
(5,	'Купить кофе', '2022-08-01'),
(5,	'Купить кофе', '2022-09-01'),
(null,	'Убрать офис', NULL),
(4,	'Наслаждаться жизнью', NULL),
(6,	'Наслаждаться жизнью', NULL);

select * from task;

select * from employee;


alter table task
	add foreign key (employee_id) references employee(id);

	
SELECT  employee.*
FROM task JOIN employee ON task.employee_id = employee.id
WHERE task.deadline < current_date;

SELECT CURRENT_DATE;


update task set employee_id = 5 where employee_id is null;



create table author (
id serial primary key,
first_name varchar(100),
last_name varchar(100),
full_name varchar(255)

);

create table publisher(
id serial primary key,
name varchar(255)
);

create table book(
id serial primary key,
title varchar(255),
author_id int,
publisher_id int,
publication_year int,
isbn bigint,
foreign key(author_id) references author(id),
foreign key(publisher_id) references publisher(id)
);

insert into author(first_name, last_name, full_name) values
	('Petr', 'Dubin', 'Petr V. Dubin');
	
insert into publisher(name) values
	('Piter');
	
insert into book (title, publication_year, isbn) values
	('wild animal', 2007, 1754);
	
select * from book;
select * from author;
select * from publisher;
select * from employee_task;

select * from author_book;