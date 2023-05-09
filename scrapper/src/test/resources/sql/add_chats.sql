TRUNCATE TABLE tg_chat RESTART IDENTITY CASCADE;

INSERT INTO tg_chat(id, created_at)
VALUES (1, now());

INSERT INTO tg_chat(id, created_at)
VALUES (2, now());