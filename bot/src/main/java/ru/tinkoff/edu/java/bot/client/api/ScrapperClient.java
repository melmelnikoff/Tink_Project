package ru.tinkoff.edu.java.bot.client.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import ru.tinkoff.edu.java.bot.dto.LinkResponse;
import ru.tinkoff.edu.java.bot.dto.ListLinksResponse;

public interface ScrapperClient {

    LinkResponse linksDelete(Long tgChatId, String url);

    ListLinksResponse linksGet(Long tgChatId);

    LinkResponse linksPost(Long tgChatId, String url);

    Void tgChatIdDelete(Long tgChatId);

    Void tgChatIdPost(Long tgChatId);
}
