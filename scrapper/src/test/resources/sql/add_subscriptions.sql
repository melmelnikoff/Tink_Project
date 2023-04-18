INSERT INTO tg_chat(id, created_at)
VALUES (1, now()),
       (2, now());

INSERT INTO link(url)
VALUES ('https://url.com'),
       ('https://newurl.com');

INSERT INTO subscription(tg_chat_id, link_id)
VALUES (1, 1),
       (1, 2),
       (2, 1);