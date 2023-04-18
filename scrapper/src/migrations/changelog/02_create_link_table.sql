--liquibase formatted sql

--changeset melnikof:init_db
CREATE SEQUENCE IF NOT EXISTS link_seq;

CREATE TABLE IF NOT EXISTS links
(
    id         bigint PRIMARY KEY DEFAULT nextVal('link_seq'),
    url        text        NOT NULL UNIQUE,
    updated_at timestamp   NOT NULL
);