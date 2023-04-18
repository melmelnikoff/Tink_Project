package ru.tinkoff.edu.java.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.entity.TgChat;
import ru.tinkoff.edu.java.scrapper.repository.TgChatRepository;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class JdbcTgChatService implements TgChatService {

    private final TgChatRepository tgChatRepository;

    @Override
    @Transactional
    public void register(long tgChatId) {
        tgChatRepository.save(new TgChat(tgChatId, OffsetDateTime.now()));
    }

    @Override
    public void unregister(long tgChatId) {
        tgChatRepository.deleteById(tgChatId);

    }
}
