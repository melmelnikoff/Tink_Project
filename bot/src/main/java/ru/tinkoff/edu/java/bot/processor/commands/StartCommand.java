package ru.tinkoff.edu.java.bot.processor.commands;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.processor.message.MessageSenderImpl;
import ru.tinkoff.edu.java.bot.service.ChatService;

@Slf4j
@Component
@RequiredArgsConstructor
public class StartCommand implements CommandInterface {
    private final MessageSenderImpl messageSender;
    private final ChatService chatService;

    @Override
    public String command() {
        return "/start";
    }

    @Override
    public String description() {
        return "get a welcome message";
    }

    @Override
    public SendMessage process(Update update) {
        chatService.registerChat(update.message().chat().id());
        log.info("New user with id {} is added", update.message().from().id());
        return messageSender.sendMessage(update, "Welcome!");
    }

    @Override
    public boolean supports(Update update) {
        return CommandInterface.super.supports(update);
    }

    @Override
    public BotCommand toApiCommand() {
        return CommandInterface.super.toApiCommand();
    }
}
