package ru.tinkoff.edu.java.bot.processor.commands;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.processor.message.MessageSenderImpl;

@Component
@RequiredArgsConstructor
public class HelpCommand implements CommandInterface {
    private static final String HELP_TEMPLATE = "help.ftl";
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
        return messageSender.sendTemplateUpdate(update, HELP_TEMPLATE, Map.of("commands", commands));
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
