package ru.tinkoff.edu.java.scrapper.repository.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.entity.Link;
import ru.tinkoff.edu.java.scrapper.entity.TgChat;
import ru.tinkoff.edu.java.scrapper.repository.LinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.SubscriptionRepository;
import ru.tinkoff.edu.java.scrapper.repository.TgChatRepository;

import java.util.List;
import java.util.Map;

@Primary
@Repository
@RequiredArgsConstructor
public class JdbcSubscriptionRepository implements SubscriptionRepository {

    private final LinkRepository linkRepository;
    private final TgChatRepository tgChatRepository;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final RowMapper<Link> linkRowMapper = new DataClassRowMapper<>(Link.class);
    private final RowMapper<TgChat> tgChatRowMapper = new DataClassRowMapper<>(TgChat.class);

    private static final String ADD_LINK_TO_CHAT_SQL = """
            insert into subscription(tg_chat_id, link_id) VALUES (:chatId, :linkId);
            """;

    private static final String DELETE_LINK_FROM_CHAT_SQL = """
            delete from subscription where tg_chat_id = :chatId and link_id = :linkId;
            """;

    private static final String FIND_LINKS_BY_CHAT_ID_SQL = """
            select id, url
            from link l
            join subscription cl ON l.id = cl.link_id
            where cl.tg_chat_id = :id
            """;

    private static final String FIND_CHATS_BY_LINK_ID_SQL = """
            select id, created_at
            from tg_chat c
            join subscription cl ON c.id = cl.tg_chat_id
            where cl.link_id = :id
            """;


    @Override
    public List<Link> findLinksByChatId(Long id) {
        return jdbcTemplate.query(FIND_LINKS_BY_CHAT_ID_SQL, Map.of("id", id), linkRowMapper);
    }

    @Override
    public List<TgChat> findChatsByLinkId(Long id) {
        Link link = linkRepository
                .findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException("Link doesn't exist", 1));
        return jdbcTemplate.query(FIND_CHATS_BY_LINK_ID_SQL, Map.of("id", link.getId()), tgChatRowMapper);
    }

    @Override
    public void addLinkToChat(Long chatId, Link link) {
        TgChat tgChat = tgChatRepository
                .findById(chatId)
                .orElseThrow(() -> new EmptyResultDataAccessException("Chat doesn't exist", 1));
        link = linkRepository
                .findLinkByUrl(link.getUrl())
                .orElseThrow(() -> new EmptyResultDataAccessException("Link doesn't exist", 1));


        jdbcTemplate.update(
                ADD_LINK_TO_CHAT_SQL,
                Map.of("chatId", tgChat.getId(),
                        "linkId", link.getId()));
    }

    @Override
    public void deleteLinkFromChat(Long chatId, Link link) {
        TgChat tgChat = tgChatRepository
                .findById(chatId)
                .orElseThrow(() -> new EmptyResultDataAccessException("Chat doesn't exist", 1));
        link = linkRepository
                .findLinkByUrl(link.getUrl())
                .orElseThrow(() -> new EmptyResultDataAccessException("Link doesn't exist", 1));

        int updatedRows = jdbcTemplate.update(DELETE_LINK_FROM_CHAT_SQL,
                Map.of("chatId", tgChat.getId(),
                        "linkId", link.getId()));

        if (updatedRows == 0) {
            throw new EmptyResultDataAccessException("Link doesn't belong to chat", 1);
        }
    }
}
