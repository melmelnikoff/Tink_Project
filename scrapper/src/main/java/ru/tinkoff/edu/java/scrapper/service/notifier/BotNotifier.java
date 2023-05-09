package ru.tinkoff.edu.java.scrapper.service.notifier;

import java.util.Collection;
import ru.tinkoff.edu.java.scrapper.entity.Link;
import ru.tinkoff.edu.java.scrapper.entity.TgChat;

public interface BotNotifier {

    void notifyBot(Link link, String description, Collection<TgChat> tgChats);
}
