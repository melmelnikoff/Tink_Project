package ru.tinkoff.edu.java.scrapper.service.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.entity.TgChat;
import ru.tinkoff.edu.java.scrapper.exception.ResourceNotFoundException;
import ru.tinkoff.edu.java.scrapper.repository.jpa.JpaTgChatRepository;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;

@RequiredArgsConstructor
public class JpaTgChatService implements TgChatService {
    private final JpaTgChatRepository jpaTgChatRepository;

    @Override
    @Transactional
    public void register(long tgChatId) {
        jpaTgChatRepository.save(new TgChat().setId(tgChatId));

    }

    @Override
    public void unregister(long tgChatId) {
        TgChat tgChat = jpaTgChatRepository.findById(tgChatId).orElseThrow(
            () -> new ResourceNotFoundException("chat doesn't exists")
        );
        jpaTgChatRepository.deleteById(tgChat.getId());
    }
}
