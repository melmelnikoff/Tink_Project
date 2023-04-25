--liquibase formatted sql

--changeset melnikoff:add-updates_count_column-link
alter table link add updates_count int;