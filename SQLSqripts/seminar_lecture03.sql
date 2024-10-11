set schema 'javarush_seminars';

create table car (
id serial primary key not null,
name varchar(255),
year int,
human_id int 
)


delete from humans
where id in (5,6,7) 

select * from humans

--ALTER TABLE javarush_seminars.car RENAME TO cars;

insert into cars (name, year, human_id)
values ('Tesla', 2021, 2),
		('Mercedes', 2020, 2),
		('Volkswagen', 2023, 1);
	-- выбираем все значения из таблицы humans и cars (получаем декартовое произведение 4 * 3 = 12 полей)	
select * from humans, cars;

update javarush_seminars.cars set 
year = 2003
where id =3;

select * from cars 

-- выбираем только те строки, где id совпадают с human_id
select * from humans, cars
where humans.id = cars.human_id;

-- найти имена людей у которых машины с годом выпуска больше 2010
select distinct human_id from cars
where year > 2010;

-- лучше использовать оператор in чем =
select humans."name" from humans 
where  id in  (
select distinct human_id from cars
where year > 2010
)

insert into cars(name, year, human_id)
values ('Toyota', 2012, 3);

select * from cars 

select name from humans 
where id in (
	select distinct human_id 
	from cars
	where year > 2010
	)
	
	
	
insert into cars(name, year, human_id)
values ('lamborgini', 2024, null);

select * from cars

-- найдите имя человека с наибольшим количеством машин

select humans.name from humans 
where id = (
select human_id from cars 
group by human_id 
order by count(human_id) desc 
limit 1
) 

-- найти людей у которых нет машин
select name from humans 
where humans.id not in (
select distinct human_id from cars 
where human_id  notnull
) 

select * from cars

select * from humans



-- найдите обладателя теслы
select name from humans 
where id in 
(select human_id from cars 
where name = 'Tesla' )

select * from humans inner join cars on humans.id = cars.human_id 

select h.*, c.* from humans h
	left join cars c on h.id = c.human_id 
	where c.human_id isnull 

create table pets(
id serial, 
name varchar(100),
human_id int );

insert into pets(name, human_id)
values ('byblik', 1),
		('kisa', 3);

insert into pets(name, human_id)
values ('Marsik', 2)

select h.name, c.name, p.name from humans as h
 join cars as c on h.id = c.human_id
 join pets as p on h.id = p.human_id

alert table cars 
add foreign key(human_id)
references humans(id);