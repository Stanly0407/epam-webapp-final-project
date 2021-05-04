create database music_wizard;

create table user (
id bigint primary key auto_increment,
login varchar(255),      
password varchar(255),
name varchar(255),
lastname varchar(255),
role enum ('ADMIN', 'USER'),
balance decimal(10,2),
status boolean default false);

create table artist (
id bigint primary key auto_increment,
name varchar(255),
filename varchar(255));

create table track (
id bigint primary key auto_increment,
release_date date,
title varchar(255),
price decimal(10,2),
filename varchar(255));

create table track_artist (
id bigint primary key auto_increment,
track_id BIGINT,
constraint foreign key(track_id) references track(id),
artist_id BIGINT,
constraint foreign key(artist_id) references artist(id));

create table purchase_order (
id bigint primary key auto_increment,
order_date datetime default CURRENT_TIMESTAMP,
is_paid boolean default false,
user_id BIGINT,
constraint foreign key(user_id) references user(id));

create table purchase_order_track (
id bigint primary key auto_increment,
order_id BIGINT,
constraint foreign key(order_id) references purchase_order(id),
track_id BIGINT,
constraint foreign key(track_id) references track(id));

create table collection (
id bigint primary key auto_increment,
type enum ('PLAYLIST', 'ALBUM'),
release_date date,
title varchar(255),
filename varchar(255),
artist_id BIGINT,
constraint foreign key(artist_id) references artist(id));

create table track_collection (
id bigint primary key auto_increment,
track_id BIGINT,
constraint foreign key(track_id) references track(id),
collection_id BIGINT, 
constraint foreign key(collection_id) references collection(id));

create table review (
id bigint primary key auto_increment,
content varchar(1020),
track_id BIGINT, 
constraint foreign key(track_id) references track(id),
user_id BIGINT, 
constraint foreign key(user_id) references user(id));

create table bonus (
id bigint primary key auto_increment,
bonus_type enum('DISCOUNT', 'FREE_TRACK'),
title varchar(1020),
description varchar(1020),
track_id BIGINT,
discount_percent int default 100,
valid_from datetime default '1900-01-01',
valid_to datetime default '9999-12-31',
constraint foreign key(track_id) references track(id),
customer_id BIGINT,
constraint foreign key(customer_id) references user(id));