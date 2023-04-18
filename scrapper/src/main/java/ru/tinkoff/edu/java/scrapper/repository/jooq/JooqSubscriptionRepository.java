package ru.tinkoff.edu.java.scrapper.repository.jooq;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.entity.Link;
import ru.tinkoff.edu.java.scrapper.entity.TgChat;
import ru.tinkoff.edu.java.scrapper.repository.LinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.SubscriptionRepository;
import ru.tinkoff.edu.java.scrapper.repository.TgChatRepository;

import java.util.List;

import static ru.tinkoff.edu.java.scrapper.entity.jooq.Tables.*;

@Repository
@RequiredArgsConstructor
public class JooqSubscriptionRepository implements SubscriptionRepository {

    private final DSLContext create;
    private final LinkRepository linkRepository;
    private final TgChatRepository tgChatRepository;

    @Override
    public List<Link> findLinksByChatId(Long id) {
        return create
                .select(LINK.ID, LINK.URL, LINK.LAST_CHECK_TIME, LINK.UPDATED_AT, LINK.UPDATES_COUNT)
                .from(LINK)
                .join(SUBSCRIPTION)
                .on(LINK.ID.eq(SUBSCRIPTION.LINK_ID))
                .where(SUBSCRIPTION.TG_CHAT_ID.eq(id))
                .fetchInto(Link.class);
    }

    @Override
    public List<TgChat> findChatsByLinkId(Long id) {
        var link = linkRepository
                .findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException("Link doesn't exist", 1));

        return create
                .select(TG_CHAT.ID, TG_CHAT.CREATED_AT)
                .from(TG_CHAT)
                .join(SUBSCRIPTION)
                .on(TG_CHAT.ID.eq(SUBSCRIPTION.TG_CHAT_ID))
                .where(SUBSCRIPTION.LINK_ID.eq(link.getId()))
                .fetchInto(TgChat.class);
    }

    @Override
    public void addLinkToChat(Long chatId, Link link) {
        var tgChat = tgChatRepository
                .findById(chatId)
                .orElseThrow(() -> new EmptyResultDataAccessException("Chat doesn't exist", 1));
        link = linkRepository
                .findLinkByUrl(link.getUrl())
                .orElseThrow(() -> new EmptyResultDataAccessException("Link doesn't exist", 1));

        create.insertInto(SUBSCRIPTION)
                .columns(SUBSCRIPTION.TG_CHAT_ID, SUBSCRIPTION.LINK_ID)
                .values(tgChat.getId(), link.getId())
                .execute();

    }

    @Override
    public void deleteLinkFromChat(Long chatId, Link link) {
        var tgChat = tgChatRepository
                .findById(chatId)
                .orElseThrow(() -> new EmptyResultDataAccessException("Chat doesn't exist", 1));
        link = linkRepository
                .findLinkByUrl(link.getUrl())
                .orElseThrow(() -> new EmptyResultDataAccessException("Link doesn't exist", 1));

        int updatedRows = create
                .delete(SUBSCRIPTION)
                .where(SUBSCRIPTION.TG_CHAT_ID.eq(tgChat.getId()).and(SUBSCRIPTION.LINK_ID.eq(link.getId())))
                .execute();

        if (updatedRows == 0) {
            throw new EmptyResultDataAccessException("Link doesn't belong to chat", 1);
        }

    }
}