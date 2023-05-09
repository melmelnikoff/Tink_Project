package ru.tinkoff.edu.java.scrapper.service.jooq;

import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.entity.TgChat;
import ru.tinkoff.edu.java.scrapper.exception.ResourceNotFoundException;
import ru.tinkoff.edu.java.scrapper.repository.jooq.JooqTgChatRepository;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;

@RequiredArgsConstructor
public class JooqTgChatService implements TgChatService {

    private final JooqTgChatRepository tgChatRepository;

    @Override
    @Transactional
    public void register(long tgChatId) {
        tgChatRepository.save(new TgChat().setId(tgChatId).setCreatedAt(OffsetDateTime.now()));
    }

    @Override
    public void unregister(long tgChatId) {
        TgChat tgChat = tgChatRepository.findById(tgChatId).orElseThrow(
            () -> new ResourceNotFoundException("chat doesn't exists")
        );
        tgChatRepository.deleteById(tgChat.getId());

    }
}
