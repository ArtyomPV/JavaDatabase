create schema hibernate13_mappingCollections;

CREATE TABLE user_messages (
    user_id INT,
    message VARCHAR(255)
);


-- drop table if exists user_answer;

create table user_answer (
user_id int,
index int,
answer varchar(255)
);

select * from users;

CREATE table user_phone (
user_id int,
name varchar(255),
phone varchar(255)
);

drop table if exists author_achievement;

CREATE TABLE author_achievement (
    user_id INT,
    achievement_index INT,
    achievement VARCHAR(255)
);

select * from author_achievement;
select * from hibernate13_mappingCollections.author;