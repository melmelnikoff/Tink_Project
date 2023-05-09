TRUNCATE TABLE link RESTART IDENTITY CASCADE;

INSERT INTO tg_chat(id, created_at)
VALUES (11, now()),
       (22, now());

INSERT INTO link(url)
VALUES ('https://url.com'),
       ('https://newurl.com');

INSERT INTO subscription(tg_chat_id, link_id)
VALUES (11, 1),
       (11, 2),
       (22, 1);