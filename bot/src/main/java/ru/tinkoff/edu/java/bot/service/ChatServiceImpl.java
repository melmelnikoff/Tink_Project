package ru.tinkoff.edu.java.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.client.api.ScrapperClient;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{
    private final ScrapperClient scrapperClient;

    @Override
    public void registerChat(long tgChatId) {
        scrapperClient.tgChatIdPost(tgChatId);
    }
}
