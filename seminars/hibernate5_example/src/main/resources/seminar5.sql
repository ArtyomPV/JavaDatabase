drop table if exists cars;

create table cars (
id serial primary key,
name varchar(255),
date int,
human_id int,
FOREIGN  key(human_id) references human(id)
);

drop table if exists human;

create table human(
id serial primary key,
balance numeric,
name varchar(100)
);

select * from human h ;
select * from cars;

alter table human add gender varchar(255);

alter table human add gender1 varchar(255);

alter table human add is_active boolean;

alter table human add is_married char;
alter table human add birthday date;
alter table human add string_map jsonb;
alter table human add created_date date;

update human set birthday = '2002-7-12' where id = 3;

