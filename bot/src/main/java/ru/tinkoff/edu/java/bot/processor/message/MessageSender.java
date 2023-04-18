package ru.tinkoff.edu.java.bot.processor.message;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.Map;

public interface MessageSender {

    SendMessage sendTemplate(Long tgChatId, String templateName, Map<String, Object> root);

    default SendMessage sendMessage(Update update, String text){
        return sendMessage(update, text);
    }
    default SendMessage sendTemplate(Update update, String templateName, Map<String, Object> root){
        return sendTemplate(update, templateName, Map.of());
    }

}

