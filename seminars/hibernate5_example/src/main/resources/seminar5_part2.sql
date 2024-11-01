drop table if exists humans;

create table humans(
id serial primary key,
name varchar(100)
);

drop table if exists cars;

create table cars (
id serial primary key,
model varchar(100),
year int
);

select * from humans;
select * from cars;