create schema hibernate4_mapping;


select * from hibernate4_mapping.humans h ;

DELETE FROM hibernate4_mapping.humans
WHERE id between 1 and 6;

alter table hibernate4_mapping.humans
add column gender varchar(255) not null;

alter table hibernate4_mapping.humans
drop column gender;

ALTER TABLE hibernate4_mapping.humans 
ALTER COLUMN gender TYPE varchar(50) USING gender::varchar(50);

Alter TABLE hibernate4_mapping.cars 
ADD COLUMN created_date data;


delete from hibernate4_mapping.humans where id =1;

ALTER TABLE hibernate4_mapping.humans ADD is_active boolean NULL;

alter table hibernate4_mapping.humans
drop column isactive;

alter table hibernate4_mapping.humans
add column birthday date;