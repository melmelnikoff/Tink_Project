package ru.tinkoff.edu.java.scrapper.repository.jooq;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.tinkoff.edu.java.scrapper.entity.Link;
import ru.tinkoff.edu.java.scrapper.entity.TgChat;
import ru.tinkoff.edu.java.scrapper.repository.SubscriptionRepository;
import static ru.tinkoff.edu.java.scrapper.entity.jooq.Tables.LINK;
import static ru.tinkoff.edu.java.scrapper.entity.jooq.Tables.SUBSCRIPTION;
import static ru.tinkoff.edu.java.scrapper.entity.jooq.Tables.TG_CHAT;

@RequiredArgsConstructor
public class JooqSubscriptionRepository implements SubscriptionRepository {
    private static final String LINK_ERROR_MESSAGE = "Link doesn't exist";
    private static final String CHAT_ERROR_MESSAGE = "Chat doesn't exist";

    private final DSLContext create;
    private final JooqLinkRepository linkRepository;
    private final JooqTgChatRepository tgChatRepository;

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
        Link link = linkRepository
            .findById(id)
            .orElseThrow(() -> new EmptyResultDataAccessException(LINK_ERROR_MESSAGE, 1));

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
        TgChat tgChat = tgChatRepository
            .findById(chatId)
            .orElseThrow(() -> new EmptyResultDataAccessException(CHAT_ERROR_MESSAGE, 1));
        Link linkToAdd = linkRepository
            .findLinkByUrl(link.getUrl())
            .orElseThrow(() -> new EmptyResultDataAccessException(LINK_ERROR_MESSAGE, 1));

        create.insertInto(SUBSCRIPTION)
            .columns(SUBSCRIPTION.TG_CHAT_ID, SUBSCRIPTION.LINK_ID)
            .values(tgChat.getId(), linkToAdd.getId())
            .execute();

    }

    @Override
    public void deleteLinkFromChat(Long chatId, Link link) {
        TgChat tgChat = tgChatRepository
            .findById(chatId)
            .orElseThrow(() -> new EmptyResultDataAccessException(CHAT_ERROR_MESSAGE, 1));
        Link linkToDelete = linkRepository
            .findLinkByUrl(link.getUrl())
            .orElseThrow(() -> new EmptyResultDataAccessException(LINK_ERROR_MESSAGE, 1));

        int updatedRows = create
            .delete(SUBSCRIPTION)
            .where(SUBSCRIPTION.TG_CHAT_ID.eq(tgChat.getId()).and(SUBSCRIPTION.LINK_ID.eq(linkToDelete.getId())))
            .execute();

        if (updatedRows == 0) {
            throw new EmptyResultDataAccessException("Link doesn't belong to chat", 1);
        }

    }
}
