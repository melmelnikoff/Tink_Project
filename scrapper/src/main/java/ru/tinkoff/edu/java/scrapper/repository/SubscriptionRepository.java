package ru.tinkoff.edu.java.scrapper.repository;

import java.util.List;
import ru.tinkoff.edu.java.scrapper.entity.Link;
import ru.tinkoff.edu.java.scrapper.entity.TgChat;

public interface SubscriptionRepository {
    List<Link> findLinksByChatId(Long id);

    List<TgChat> findChatsByLinkId(Long id);

    void addLinkToChat(Long chatId, Link link);

    void deleteLinkFromChat(Long chatId, Link link);

}
