package ru.tinkoff.edu.java.scrapper.repository;

import ru.tinkoff.edu.java.scrapper.entity.Link;
import ru.tinkoff.edu.java.scrapper.entity.TgChat;

import java.util.List;

public interface SubscriptionRepository {
    List<Link> findLinksByChatId(Long id);

    List<TgChat> findChatsByLinkId(Long id);

    void addLinkToChat(Long chatId, Link link);

    void deleteLinkFromChat(Long chatId, Link link);


}
