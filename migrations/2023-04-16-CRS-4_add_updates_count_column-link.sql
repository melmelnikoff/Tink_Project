--liquibase formatted sql

--changeset melmelnikoff:add-updates_count_column-link
alter table link add updates_count int;
