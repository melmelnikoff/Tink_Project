package ru.tinkoff.edu.java.bot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Component
public class UntrackCommand extends BotCommand {

    public UntrackCommand() {
        super("untrack", "Прекратить отслеживание ссылки");
    }

    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        //код, который будет выполняться при вызове команды /untrack
    }
}