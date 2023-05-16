package ru.tinkoff.edu.java.scrapper.service.jpa;

import java.net.URI;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.parser.Parser;
import ru.tinkoff.edu.java.scrapper.entity.Link;
import ru.tinkoff.edu.java.scrapper.entity.TgChat;
import ru.tinkoff.edu.java.scrapper.exception.LinkParserException;
import ru.tinkoff.edu.java.scrapper.exception.ResourceNotFoundException;
import ru.tinkoff.edu.java.scrapper.repository.jpa.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.jpa.JpaTgChatRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

@Slf4j
@RequiredArgsConstructor
public class JpaLinkService implements LinkService {
    private static final String CHAT_NOT_FOUND = "Chat doesn't exist";

    private final JpaLinkRepository linkRepository;
    private final JpaTgChatRepository tgChatRepository;
    private final Parser linkParser;

    @Override
    @Transactional
    public Link add(long tgChatId, URI url) {
        if (linkParser.parse(url.toString()) == null) {
            log.info("cant parse");
            throw new LinkParserException("Can't parse this link");

        }
        Link link = new Link().setUrl(url.toString());

        TgChat tgChat = tgChatRepository
            .findById(tgChatId)
            .orElseThrow(() -> new EmptyResultDataAccessException(CHAT_NOT_FOUND, 1));

        tgChat.getLinks().add(link);

        return linkRepository.save(link);
    }

    @Override
    @Transactional
    public Link remove(long tgChatId, URI url) {
        Link link = linkRepository
            .findByUrl(url.toString())
            .orElseThrow(() -> new ResourceNotFoundException("Link doesn't exist"));

        TgChat tgChat = tgChatRepository
            .findById(tgChatId)
            .orElseThrow(() -> new ResourceNotFoundException(CHAT_NOT_FOUND));

        tgChat.getLinks().remove(link);

        return link;
    }

    @Override
    @Transactional
    public Collection<Link> listAll(long tgChatId) {
        TgChat tgChat = tgChatRepository
            .findById(tgChatId)
            .orElseThrow(() -> new ResourceNotFoundException(CHAT_NOT_FOUND));

        return tgChat.getLinks();
    }

}
