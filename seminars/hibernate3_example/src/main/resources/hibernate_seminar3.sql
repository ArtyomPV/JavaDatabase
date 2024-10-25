DROP SCHEMA hibernate_seminar3 CASCADE;

create schema hibernate_seminar3;


drop table if exists humans;

drop table if exists cars;


create table humans(
id serial primary key,
name varchar(255),
age int
);

create table cars(
id serial primary key,
name varchar(255)
);


select * from cars;

delete from cars where id in (3,4);


create table workers(
id serial primary key,
name varchar(255),
age int
);

create table boats(
id serial primary key,
name varchar(255),
worker_id int,
foreign key(worker_id) references workers(id)
);

alter table boats
add column version int;

alter table boats 
add column update_date date;

select * from boats;


drop table boats;