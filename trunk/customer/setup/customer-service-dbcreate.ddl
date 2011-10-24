
CREATE DATABASE koku1 DEFAULT CHARACTER SET = 'utf8';

-- create users
CREATE USER koku1@localhost IDENTIFIED BY 'koku1';
GRANT ALL PRIVILEGES ON koku1.* TO koku1@localhost;

