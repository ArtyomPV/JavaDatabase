insert into test (name) values (null);

select * from test;

alter table test
add column level int;

update test set
level = 23 where id =2;


START TRANSACTION;
insert into test(name, level)
values('task2', 12);
commit;

select * from test;