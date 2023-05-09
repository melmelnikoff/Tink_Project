package ru.tinkoff.edu.java.bot.processor.message;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.Map;

public interface MessageSender {

    SendMessage sendTemplateId(Long tgChatId, String templateName, Map<String, Object> root);

    SendMessage sendMessage(Update update, String text);

    SendMessage sendTemplateUpdate(Update update, String templateName, Map<String, Object> root);

}

