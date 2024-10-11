create table publisher(
id int, 
name varchar(100)
);


create table book(
id int, 	
title varchar(100),
author_id int,
publisher_id int,
publication_year int,
isbn bigint
);
drop table library.author 

create table library.author (
id int,
first_name varchar(50),
last_name varchar(50),
full_name varchar(100)
);


create table part(
id int,
number varchar(50),
name varchar(100),
weight float
);

drop table lego.lego_set

create table lego.lego_set(
id serial primary key,
number int,
name varchar(50),
released int,
inventory int
);

insert into library.author (id,	first_name,	last_name,	full_name)
values
(1,	'Robert',	'Stevenson',	'Robert Louis Stevenson'),
(2,	'Aldous',	'Huxley',	'Aldous Huxley'),
(3,	'Gabriel',	'Marquez',	'Gabriel Garcia Marquez'),
(4,	'Ernest',	'Hemingway',	'Ernest Hemingway'),
(5,	'William',	'Shakespeare',	'William Shakespeare'),
(6,	'Homer',	null,	'Homer'),
(7,	'Mark',	'Twain',	'Mark Twain'),
(8,	'George',	'Orwell',	'George Orwell'),
(9,	'Plato', null,		'Plato'),
(10,	'Edgar',	'Poe',	'Edgar Allan Poe'),
(11,	'John',	'Tolkien',	'J. R. R. Tolkien'),
(12,	'Franz',	'Kafka',	'Franz Kafka'),
(13,	'Arthur',	'Doyle',	'Arthur Conan Doyle'),
(14,	'Charles',	'Dickens',	'Charles Dickens'),
(15,	'James',	'Joyce',	'James Joyce'),
(16,	'Jules',	'Verne',	'Jules Verne'),
(17,	'Johann',	'Goethe',	'Johann Wolfgang von Goethe'),
(18,	'Marcel',	'Proust',	'Marcel Proust'),
(19,	'Friedrich',	'Nietzsche',	'Friedrich Nietzsche')


insert into library.book (title,	author_id,	publisher_id,	publication_year,	isbn)
values
('Treasure Island',	1,	1,	2017,	9785389052857),
('Strange Case of Dr Jekyll and Mr Hyde',	1,	1,	2019,	9785389024007),
('Treasure Island',	1,	1,	2017,	9785389092983),
('Treasure Island',	1,	1,	2007,	9785911815196),
('Treasure Island',	1,	1,	2011,	9785389031173),
('Treasure Island',	1,	1,	2015,	9785389097186),
('Hamlet',	5,	1,	2018,	9785389064751),
('The Taming of the Shrew',	5,	1,	2016,	9785389033900),
('Romeo and Juliet',	5,	1,	2018,	9785389027039),
('The Taming of the Shrew',	5,	1,	2009,	9785998503160),
('Odyssey',	6,	1,	2018,	9785389057319),
('Odyssey',	6,	1,	2018,	9785389092983),
('Iliad',	6,	1,	2018,	9785389058705),
('Iliad',	6,	1,	2018,	9785389030800),
('Odyssey',	6,	1,	2014,	9785389081192),
('Odyssey',	6,	1,	2010,	9785998509018),
('Iliad',	6,	1,	2014,	9785389078390),
('Iliad',	6,	1,	2010,	9785389078390),
('A Connecticut Yankee in King Arthur’s Court',	7,	2,	2018,	9789660375383),
('The Adventures of Tom Sawyer',	7,	1,	2018,	9785389030978)