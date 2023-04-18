--liquibase formatted sql

--changeset melnikof:init_db
CREATE TABLE IF NOT EXISTS chats
(
    id bigint PRIMARY KEY
);