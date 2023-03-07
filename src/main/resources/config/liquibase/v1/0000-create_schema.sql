--liquibase formatted sql

--changeset cavemanfrak:01 contextFilter:ddl
CREATE USER IF NOT EXISTS marine PASSWORD 'password';

--changeset cavemanfrak:02 contextFilter:ddl
CREATE SCHEMA IF NOT EXISTS marine AUTHORIZATION marine;