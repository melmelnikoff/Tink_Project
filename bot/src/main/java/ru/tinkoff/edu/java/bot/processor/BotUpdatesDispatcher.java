package ru.tinkoff.edu.java.bot.processor;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BotUpdatesDispatcher implements UpdatesListener {
    private static final String MESSAGE = "I don't have this command. Use /help to see all my commands";

    private final TelegramBot bot;

    private final CommandProcessor commandProcessor;

    @Override
    public int process(List<Update> updates) {
        int lastProcessedId = CONFIRMED_UPDATES_NONE;

        for (Update update : updates) {
            if (update.message() == null) {
                continue;
            }

            try {
                bot.execute(commandProcessor.process(update).orElse(unexpectedMessage(update)));

                lastProcessedId = update.updateId();
            } catch (RuntimeException ex) {

                return lastProcessedId;
            }
        }

        return CONFIRMED_UPDATES_ALL;
    }

    private SendMessage unexpectedMessage(Update update) {
        return new SendMessage(update.message().chat().id(), MESSAGE);
    }

    public List<BotCommand> getCommands() {
        return commandProcessor.getCommands();
    }
}
