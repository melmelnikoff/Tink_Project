package ru.tinkoff.edu.java.bot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.LinkUpdateRequest;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.processor.message.MessageSender;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AlertUpdatesServiceImpl implements AlertUpdatesService{

    private final TelegramBot telegramBot;
    private final MessageSender messageSender;


    @Override
    public void alertUpdates(LinkUpdateRequest linkUpdateRequest) {
        Map<String, Object> model = Map.of("description", linkUpdateRequest.getDescription(),
                "url", linkUpdateRequest.getUrl());

        for (long chatId : linkUpdateRequest.getTgChatIds()) {
            SendMessage message = messageSender.sendTemplate(chatId, "update.ftl", model);
            telegramBot.execute(message);
        }
    }
}
