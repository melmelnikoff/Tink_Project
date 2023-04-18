package ru.tinkoff.edu.java.scrapper.repository;

import ru.tinkoff.edu.java.scrapper.entity.TgChat;

import java.util.List;
import java.util.Optional;

public interface TgChatRepository {
    void save(TgChat tgChat);

    List<TgChat> findAll();

    Optional<TgChat> findById(Long id);

    void deleteById(Long id);
}
