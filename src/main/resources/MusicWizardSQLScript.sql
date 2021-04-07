create database music_wizard;
use music_wizard;
drop database music_wizard;

create table user (
id bigint primary key auto_increment,
login varchar(255),      
password varchar(255),
name varchar(255),
lastname varchar(255),
role enum ('ADMIN', 'USER'));

create table artist (
id bigint primary key auto_increment,
name varchar(255));

create table track (
id bigint primary key auto_increment,
release_date date,
title varchar(255),
description varchar(510),
price decimal(10,2) not null, 
filename varchar(255),
artist_id BIGINT, 
constraint foreign key(artist_id) references artist(id));

create table collection (
id bigint primary key auto_increment,
type enum ('COLLECTION', 'ALBUM'),
release_date date,
title varchar(255),
artist_id BIGINT, 
constraint foreign key(artist_id) references artist(id));

create table track_collection (
id bigint primary key auto_increment,
track_id BIGINT,
constraint foreign key(track_id) references track(id),
collection_id BIGINT, 
constraint foreign key(collection_id) references collection(id));

create table purchase_order (
id bigint primary key auto_increment,
order_date datetime not null,
is_paid boolean,
user_id BIGINT,
constraint foreign key(user_id) references user(id));

create table review (
id bigint primary key auto_increment,
content varchar(1020),
track_id BIGINT, 
constraint foreign key(track_id) references track(id),
user_id BIGINT, 
constraint foreign key(user_id) references user(id));
