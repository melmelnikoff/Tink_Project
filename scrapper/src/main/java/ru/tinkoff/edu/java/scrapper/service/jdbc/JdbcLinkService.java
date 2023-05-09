package ru.tinkoff.edu.java.scrapper.service.jdbc;

import java.net.URI;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.parser.Parser;
import ru.tinkoff.edu.java.scrapper.entity.Link;
import ru.tinkoff.edu.java.scrapper.exception.LinkParserException;
import ru.tinkoff.edu.java.scrapper.exception.ResourceNotFoundException;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.JdbcLinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.JdbcSubscriptionRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {
    private static final String EXCEPTION_MESSAGE = "Can't parse this link";

    private final JdbcLinkRepository linkRepository;
    private final JdbcSubscriptionRepository subscriptionRepository;
    private final Parser linkParser;

    @Override
    @Transactional
    public Link add(long tgChatId, URI url) {
        if (linkParser.parse(url.toString()) == null) {
            throw new LinkParserException(EXCEPTION_MESSAGE);
        }

        Link link = linkRepository.findLinkByUrl(url.toString())
            .orElseGet(() -> linkRepository.save(new Link().setUrl(url.toString())));

        subscriptionRepository.addLinkToChat(tgChatId, link);

        return link;
    }

    @Override
    @Transactional
    public Link remove(long tgChatId, URI url) {
        if (linkParser.parse(url.toString()) == null) {
            throw new LinkParserException(EXCEPTION_MESSAGE);
        }

        Link link = linkRepository.findLinkByUrl(url.toString())
            .orElseThrow(() -> new ResourceNotFoundException("Link not found"));

        subscriptionRepository.deleteLinkFromChat(tgChatId, link);

        return link;
    }

    @Override
    @Transactional
    public Collection<Link> listAll(long tgChatId) {
        return subscriptionRepository.findLinksByChatId(tgChatId);
    }

}
