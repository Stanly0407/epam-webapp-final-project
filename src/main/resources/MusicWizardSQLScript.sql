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

insert into user(id, login, password, name, lastname, role, balance) values
(null, 'admin', 1111, 'Admin', null, 'ADMIN', 20.50),
(null, 'user', 1111, 'Svetlana', 'Shelestova', 'USER', 100.00),
(null, 'Ivan', 1111, 'Ivan', 'Ivanov', 'USER', 20.50),
(null, 'Oleg', 1111, 'Oleg', 'Petrov', 'USER', 20.00),
(null, 'Luther', 1111, 'John', 'Luther', 'USER', 150.00);

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
amount int,
status_use boolean default false,
user_id BIGINT,
constraint foreign key(user_id) references user(id));