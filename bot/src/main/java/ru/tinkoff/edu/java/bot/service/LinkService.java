package ru.tinkoff.edu.java.bot.service;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import ru.tinkoff.edu.java.bot.dto.LinkResponse;

public interface LinkService {
    List<LinkResponse> getAllLinks(Long tgChatId);

    Optional<LinkResponse> trackLink(Long tgChatId, URI link);

    Optional<LinkResponse> untrackLink(Long tgChatId, URI link);

}
