package ru.tinkoff.edu.java.scrapper.repository.jdbc;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.tinkoff.edu.java.scrapper.entity.Link;
import ru.tinkoff.edu.java.scrapper.entity.TgChat;
import ru.tinkoff.edu.java.scrapper.repository.SubscriptionRepository;

//убрать subscription repository

@RequiredArgsConstructor
public class JdbcSubscriptionRepository implements SubscriptionRepository {
    private static final String LINK_ERROR_MESSAGE = "Link doesn't exist";
    private static final String CHAT_ERROR_MESSAGE = "Chat doesn't exist";
    private static final String LINK_ID_KEY = "linkId";
    private static final String CHAT_ID_KEY = "chatId";

    private static final String ADD_LINK_TO_CHAT_SQL = """
        insert into subscription(tg_chat_id, link_id) values (:chatId, :linkId);
        """;
    private static final String DELETE_LINK_FROM_CHAT_SQL = """
        delete from subscription where tg_chat_id = :chatId and link_id = :linkId;
        """;
    private static final String FIND_LINKS_BY_CHAT_ID_SQL = """
        select id, url
        from link l
        join subscription cl on l.id = cl.link_id
        where cl.tg_chat_id = :id
        """;
    private static final String FIND_CHATS_BY_LINK_ID_SQL = """
        select id, created_at
        from tg_chat c
        join subscription cl on c.id = cl.tg_chat_id
        where cl.link_id = :id
        """;
    private final JdbcLinkRepository linkRepository;
    private final JdbcTgChatRepository tgChatRepository;
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Link> linkRowMapper = new DataClassRowMapper<>(Link.class);
    private final RowMapper<TgChat> tgChatRowMapper = new DataClassRowMapper<>(TgChat.class);

    @Override
    public List<Link> findLinksByChatId(Long id) {
        return jdbcTemplate.query(FIND_LINKS_BY_CHAT_ID_SQL, Map.of("id", id), linkRowMapper);
    }

    @Override
    public List<TgChat> findChatsByLinkId(Long id) {
        Link link = linkRepository
            .findById(id)
            .orElseThrow(() -> new EmptyResultDataAccessException(LINK_ERROR_MESSAGE, 1));
        return jdbcTemplate.query(FIND_CHATS_BY_LINK_ID_SQL, Map.of("id", link.getId()), tgChatRowMapper);
    }

    @Override
    public void addLinkToChat(Long chatId, Link link) {
        TgChat tgChat = tgChatRepository
            .findById(chatId)
            .orElseThrow(() -> new EmptyResultDataAccessException(CHAT_ERROR_MESSAGE, 1));
        Link linkToAdd = linkRepository
            .findLinkByUrl(link.getUrl())
            .orElseThrow(() -> new EmptyResultDataAccessException(LINK_ERROR_MESSAGE, 1));

        jdbcTemplate.update(
            ADD_LINK_TO_CHAT_SQL,
            Map.of(CHAT_ID_KEY, tgChat.getId(),
                LINK_ID_KEY, linkToAdd.getId()
            )
        );
    }

    @Override
    public void deleteLinkFromChat(Long chatId, Link link) {
        TgChat tgChat = tgChatRepository
            .findById(chatId)
            .orElseThrow(() -> new EmptyResultDataAccessException(CHAT_ERROR_MESSAGE, 1));
        Link linkFromChat = linkRepository
            .findLinkByUrl(link.getUrl())
            .orElseThrow(() -> new EmptyResultDataAccessException(LINK_ERROR_MESSAGE, 1));

        int updatedRows = jdbcTemplate.update(
            DELETE_LINK_FROM_CHAT_SQL,
            Map.of(CHAT_ID_KEY, tgChat.getId(),
                LINK_ID_KEY, linkFromChat.getId()
            )
        );

        if (updatedRows == 0) {
            throw new EmptyResultDataAccessException("Link doesn't belong to chat", 1);
        }
    }
}
