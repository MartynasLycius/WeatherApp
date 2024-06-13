-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.20 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.7.0.6850
-- --------------------------------------------------------



CREATE DATABASE IF NOT EXISTS `weather` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `weather`;
create table user
(
    id        int auto_increment
        primary key,
    password  varchar(255) null,
    user_name varchar(255) null,
    constraint UKlqjrcobrh9jc8wpcar64q1bfh
        unique (user_name),
    constraint user_name
        unique (user_name)
)
    collate = utf8mb4_0900_ai_ci;

create table user_favourites
(
    id        int auto_increment
        primary key,
    city      varchar(255) null,
    country   varchar(255) null,
    latitude  double       null,
    longitude double       null,
    user_id   int          not null,
    constraint UKaj90vruvn7iok8r5dws6aidu6
        unique (city, country, latitude, longitude),
    constraint FKj2kht57b5ftwc4nkpn3vbc5b3
        foreign key (user_id) references user (id)
)
    collate = utf8mb4_0900_ai_ci;

