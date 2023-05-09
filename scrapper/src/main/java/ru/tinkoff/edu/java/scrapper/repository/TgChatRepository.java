package ru.tinkoff.edu.java.scrapper.repository;

import java.util.List;
import java.util.Optional;
import ru.tinkoff.edu.java.scrapper.entity.TgChat;

public interface TgChatRepository {
    void save(TgChat tgChat);

    List<TgChat> findAll();

    Optional<TgChat> findById(Long id);

    void deleteById(Long id);
}
