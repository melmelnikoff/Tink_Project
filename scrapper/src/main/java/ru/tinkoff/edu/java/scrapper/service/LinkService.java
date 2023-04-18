package ru.tinkoff.edu.java.scrapper.service;

import ru.tinkoff.edu.java.scrapper.entity.Link;

import java.net.URI;
import java.util.Collection;

public interface LinkService {
    Link add(long tgChatId, URI url);

    Link remove(long tgChatId, URI url);

    Collection<Link> listAll(long tgChatId);
}
