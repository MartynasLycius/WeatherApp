-- Create database
CREATE DATABASE weatherapp;


-- Create table
CREATE TABLE public.users
(
    id        SERIAL PRIMARY KEY,
    username  VARCHAR(255) NOT NULL,
    password  VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    CONSTRAINT user_username_unique UNIQUE (username)
);

-- Insert sample data
INSERT INTO public.users (username, password, full_name)
VALUES ('john_doe', 'password123', 'John Doe'),
       ('jane_smith', 'pass456', 'Jane Smith'),
       ('admin', 'admin123', 'Admin User');


-- Create table
CREATE TABLE public."location"
(
    id        serial4      NOT NULL,
    "name"    varchar(255) NOT NULL,
    admin1    varchar(255) NULL,
    admin2    varchar(255) NULL,
    admin3    varchar(255) NULL,
    latitude  float8       NOT NULL,
    longitude float8       NOT NULL,
    user_id   int4         NOT NULL,
    CONSTRAINT location_pkey PRIMARY KEY (id)
);