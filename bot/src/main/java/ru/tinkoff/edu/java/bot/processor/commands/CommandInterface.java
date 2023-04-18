package ru.tinkoff.edu.java.bot.processor.commands;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface CommandInterface {
    String command();

    String description();

    SendMessage process(Update update);


    default BotCommand toApiCommand() {
        return new BotCommand(command(), description());
    }

    default boolean supports(Update update){
        return update.message() != null &&
                update.message().text() != null &&
                update.message().text().startsWith(command());
    }
}
