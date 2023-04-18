package ru.tinkoff.edu.java.bot.processor.commands;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.processor.message.MessageSenderImpl;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class HelpCommand implements CommandInterface {
    private final List<CommandInterface> commands;
    private final MessageSenderImpl messageSender;


    @Override
    public String command() {
        return "/help";
    }

    @Override
    public String description() {
        return "show all commands";
    }

    @Override
    public SendMessage process(Update update) {
        return messageSender.sendTemplate(update, "help.ftl", Map.of("commands", commands));
    }

    @Override
    public BotCommand toApiCommand() {
        return CommandInterface.super.toApiCommand();
    }

    @Override
    public boolean supports(Update update) {
        return CommandInterface.super.supports(update);
    }
}
