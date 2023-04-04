package ru.tinkoff.edu.java.bot;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;

public interface TelegramBotSecond {
    void execute(TelegramBot bot, User user, Chat chat, String[] arguments);
}
