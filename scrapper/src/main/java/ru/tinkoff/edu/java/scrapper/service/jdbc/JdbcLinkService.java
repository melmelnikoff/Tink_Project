package ru.tinkoff.edu.java.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.parser.Parser;
import ru.tinkoff.edu.java.scrapper.entity.Link;
import ru.tinkoff.edu.java.scrapper.entity.TgChat;
import ru.tinkoff.edu.java.scrapper.exception.DuplicateLinkException;
import ru.tinkoff.edu.java.scrapper.exception.LinkParserException;
import ru.tinkoff.edu.java.scrapper.exception.ResourceNotFoundException;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.JdbcLinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.JdbcSubscriptionRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

import java.net.URI;
import java.util.Collection;

@RequiredArgsConstructor
@Service
public class JdbcLinkService implements LinkService {

    private final JdbcLinkRepository linkRepository;
    private final JdbcSubscriptionRepository subscriptionRepository;
    private final Parser linkParser;


    @Override
    @Transactional
    public Link add(long tgChatId, URI url) {
        if (linkParser.parse(url.toString()) == null) {
            throw new LinkParserException("Can't parse this link");
        }

        Link link = linkRepository.findLinkByUrl(url)
                .orElseGet(() -> linkRepository.save(Link.builder().url(url).build()));

        subscriptionRepository.addLinkToChat(tgChatId, link);

        return link;
    }

    @Override
    @Transactional
    public Link remove(long tgChatId, URI url) {
        if (linkParser.parse(url.toString()) == null) {
            throw new LinkParserException("Can't parse this link");
        }

        Link link = linkRepository.findLinkByUrl(url)
                .orElseThrow(() -> new ResourceNotFoundException("Link not found"));

        subscriptionRepository.deleteLinkFromChat(tgChatId, link);

        return link;
    }

    @Override
    @Transactional
    public Collection<Link> listAll(long tgChatId) {
        return subscriptionRepository.findLinksByChatId(tgChatId);
    }


    private static Link buildLink(URI url) {
        return Link.builder().url(url).build();
    }
}
