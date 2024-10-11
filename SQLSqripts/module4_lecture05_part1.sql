drop table if exists object

create table object(
id serial primary key,
name varchar(100),
type varchar(50),
discoverer varchar(150),
discover_date date
);


select * from space.object

insert into space.object( name, type, discoverer, discover_date)
values ('Uranus',	'PLANET',	'William Herschel',	'13-March-1781')

insert into space.object( name, type, discoverer, discover_date)
values ('Phobos',	'SATELLITE',	'Asaph Hall',	'18-August-1877'),
('Deimos',	'SATELLITE',	'Asaph Hall',	'12-August-1877'),
('4446 Carolyn',	'ASTEROID','E. Bowell',	'15-October-1985'),
('Titan',	'SATELLITE',	'Christiaan Huygens',	'25-March-1655'),
('Pluto',	'DWARF_PLANET',	'Clyde W. Tombaugh',	'18-February-1930'),
('P/2010 B2',	'COMET',	'WISE',	'22-January-2010'),
('C/1823 Y1',	'COMET',	'Nell de Breaute',	'29-December-1823'),
('Sun STAR',	null, null, null),	
('Vesta',	'ASTEROID',	'Heinrich Wilhelm Olbers',	'29-March-1807'),
('Io',	'SATELLITE',	'Galileo Galilei',	'8-January-1610'),
('Callisto',	'SATELLITE',	'Galileo Galilei',	'7-January-1610'),
('1143 Odysseus',	'ASTEROID',	'K. Reinmuth',	'28-January-1930'),
('624 Hektor',	'ASTEROID',	'A. Kopff',	'10-February-1907'),
('588 Achilles',	'ASTEROID',	'M. F. Wolf',	'22-February-1906'),
('Venus',	'PLANET',	null, null),
('Mars',	'PLANET',	null, null),
('2016 BA14',	'COMET',	'Pan-STARRS',	'22-January-2016'),
('2019 LD2',	'COMET',	'ATLAS-MLO',	'10-June-2019'),
('C/1858 L1',	'COMET',	'Giovanni Battista Donati',	'2-June-1858')