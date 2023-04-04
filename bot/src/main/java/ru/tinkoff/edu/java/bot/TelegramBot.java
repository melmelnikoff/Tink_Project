package ru.tinkoff.edu.java.bot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Optional;

@Component
public class TelegramBot extends TelegramLongPollingBot implements TelegramBotSecond {

    private final ApplicationConfig config;
    private final List<BotCommand> commands;

    public TelegramBot(ApplicationConfig config, List<BotCommand> commands) {
        this.config = config;
        this.commands = commands;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            if (messageText.startsWith("/")) {
                String commandText = messageText.split(" ")[0].substring(1);
                Optional<BotCommand> optionalCommand = commands.stream()
                        .filter(c -> c.getCommand().equals(commandText))
                        .findFirst();

                if (optionalCommand.isPresent()) {
                    BotCommand command = optionalCommand.get();
                    execute(this, update.getMessage().getFrom(), update.getMessage().getChat(),
                            messageText.split(" ").length > 1 ? messageText.split(" ", 2)[1].split(" ") : new String[0]);
                } else {
                    sendMessage(chatId, "Неизвестная команда");
                }
            }
        }
    }

    @Override
    public void execute(TelegramBot bot, User user, Chat chat, String[] arguments) {
        bot.sendMessage(chat.getId(), "Hello, " + user.getFirstName() + "!");
    }

    @Override
    public String getBotUsername() {
        return "YOUR_BOT_NAME";
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    private void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}