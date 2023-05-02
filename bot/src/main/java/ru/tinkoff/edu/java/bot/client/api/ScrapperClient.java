package ru.tinkoff.edu.java.bot.client.api;

import ru.tinkoff.edu.java.bot.dto.LinkResponse;
import ru.tinkoff.edu.java.bot.dto.ListLinksResponse;

import java.net.URI;
import java.util.Optional;

public interface ScrapperClient {

    Optional<LinkResponse> linksDelete(Long tgChatId, URI url);

    ListLinksResponse linksGet(Long tgChatId);

    Optional<LinkResponse> linksPost(Long tgChatId, URI url);

    Void tgChatIdDelete(Long tgChatId);

    Void tgChatIdPost(Long tgChatId);
}
