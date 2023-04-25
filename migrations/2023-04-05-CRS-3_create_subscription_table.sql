--liquibase formatted sql

--changeset melnikoff:add-chat_link-table
create table if not exists subscription (
    tg_chat_id  bigint  not null,
    link_id     bigint  not null,

    primary key (tg_chat_id, link_id),
    foreign key (tg_chat_id) references tg_chat (id) on delete cascade,
    foreign key (link_id) references link (id) on delete cascade
);