--liquibase formatted sql

--changeset melnikoff:add-tg_chat-table
create table if not exists tg_chat (
    id              bigint not null,
    created_at      timestamp with time zone not null ,

    primary key (id)
);