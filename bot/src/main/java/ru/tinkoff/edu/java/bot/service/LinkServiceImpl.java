package ru.tinkoff.edu.java.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.client.api.ScrapperClient;
import ru.tinkoff.edu.java.bot.dto.LinkResponse;

import javax.swing.text.html.Option;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LinkServiceImpl implements LinkService {
    private final ScrapperClient scrapperClient;

    @Override
    public List<LinkResponse> getAllLinks(Long tgChatId) {
        return scrapperClient.linksGet(tgChatId).links();
    }

    @Override
    public Optional<LinkResponse> trackLink(Long tgChatId, URI link) {
        return scrapperClient.linksPost(tgChatId, link);
    }

    @Override
    public Optional<LinkResponse> untrackLink(Long tgChatId, URI link) {
        return scrapperClient.linksDelete(tgChatId, link);
    }
}
