-- Setup database
DROP DATABASE IF EXISTS preplane;
CREATE DATABASE preplane;
USE preplane;

-- Create all the tables
CREATE TABLE user (
    user_id INT AUTO_INCREMENT NOT NULL,
    username varchar(100) NOT NULL UNIQUE,
    password varchar(200) NOT NULL,
    PRIMARY KEY (user_id)
);