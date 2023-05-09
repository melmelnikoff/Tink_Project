package ru.tinkoff.edu.java.scrapper.service.jooq;

import java.net.URI;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.parser.Parser;
import ru.tinkoff.edu.java.scrapper.entity.Link;
import ru.tinkoff.edu.java.scrapper.exception.LinkParserException;
import ru.tinkoff.edu.java.scrapper.exception.ResourceNotFoundException;
import ru.tinkoff.edu.java.scrapper.repository.jooq.JooqLinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.jooq.JooqSubscriptionRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

@RequiredArgsConstructor
public class JooqLinkService implements LinkService {
    private static final String PARSER_EXCEPTION_MESSAGE = "Can't parse this link";

    private final JooqLinkRepository linkRepository;
    private final JooqSubscriptionRepository subscriptionRepository;
    private final Parser linkParser;

    @Override
    @Transactional
    public Link add(long tgChatId, URI url) {
        if (linkParser.parse(url.toString()) == null) {
            throw new LinkParserException(PARSER_EXCEPTION_MESSAGE);
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
            throw new LinkParserException(PARSER_EXCEPTION_MESSAGE);
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
