drop table if exists users;

create table users(
id serial primary key,
first_name varchar(100),
middle_name varchar(100),
last_name varchar(100)
);

create table lectures(
id serial primary key,
name varchar(255)
);

create table students(
id serial primary key,
first_name varchar(100),
middle_name varchar(100),
last_name varchar(100)
);

create table books(
id serial primary key,
name varchar(255)
);

alter table books add student_id int;

alter table books add
foreign key (student_id) references students(id);

select * from users;

select * from books;

select * from students;

insert into students(first_name, middle_name, last_name) values
	('Petr', 'V', 'Gvozdik');

insert into books(name, student_id) values
	('Astronomy', 3),
	('Algebra', 4);
