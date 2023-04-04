package ru.tinkoff.edu.java.bot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Component
public class ListCommand extends BotCommand {

    public ListCommand() {
        super("list", "Показать список отслеживаемых ссылок");
    }

    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        // Реализуйте код, который будет выполняться при вызове команды /list
    }
}