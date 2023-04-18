package ru.tinkoff.edu.java.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.parser.Parser;
import ru.tinkoff.edu.java.scrapper.entity.Link;
import ru.tinkoff.edu.java.scrapper.exception.DuplicateLinkException;
import ru.tinkoff.edu.java.scrapper.exception.LinkParserException;
import ru.tinkoff.edu.java.scrapper.exception.ResourceNotFoundException;
import ru.tinkoff.edu.java.scrapper.repository.LinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.SubscriptionRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

import java.net.URI;
import java.util.Collection;

@RequiredArgsConstructor
@Service
public class JdbcLinkService implements LinkService {

    private final LinkRepository linkRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final Parser linkParser;


    @Override
    @Transactional
    public Link add(long tgChatId, URI url) {
        if (linkParser.parse(url.toString()) == null) {
            throw new LinkParserException("Can't parse this link");
        }

        Link link = buildLink(url);
        if (linkRepository.findLinkByUrl(link.getUrl()).isEmpty()) {
            link = linkRepository.save(link);
        } else {
            link = linkRepository.findLinkByUrl(link.getUrl()).get();
        }


        try {
            subscriptionRepository.addLinkToChat(tgChatId, link);
        } catch (DuplicateKeyException e) {
            throw new DuplicateLinkException("This link already tracking");
        }
        return link;
    }

    @Override
    @Transactional
    public Link remove(long tgChatId, URI url) {
        if (linkParser.parse(url.toString()) == null) {
            throw new LinkParserException("Can't parse this link");
        }

        Link link = buildLink(url);

        try {
            link = linkRepository.findLinkByUrl(link.getUrl()).get();
            subscriptionRepository.deleteLinkFromChat(tgChatId, link);
        } catch (RuntimeException e) {
            throw new ResourceNotFoundException("Link not found");
        }
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
