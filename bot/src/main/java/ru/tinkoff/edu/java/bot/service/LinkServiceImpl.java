package ru.tinkoff.edu.java.bot.service;

import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.dto.LinkResponse;

import java.util.List;
import java.util.Optional;

@Component
public class LinkServiceImpl implements LinkService {

    @Override
    public List<LinkResponse> getAllLinks(Long tgChatId) {
        return List.of();
    }

    @Override
    public Optional<LinkResponse> trackLink(Long tgChatId, String link) {
        return Optional.empty();
    }

    @Override
    public Optional<LinkResponse> untrackLink(Long tgChatId, String link) {
        return Optional.empty();
    }
}
