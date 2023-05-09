package ru.tinkoff.edu.java.bot.processor;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.processor.commands.CommandInterface;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommandProcessor {
    private final List<CommandInterface> commands;

    public Optional<SendMessage> process(Update update) {
        return findRequiredCommand(update).map(p -> p.process(update));
    }

    private Optional<CommandInterface> findRequiredCommand(Update update) {
        return commands.stream().filter(p -> p.supports(update)).findAny();
    }

    public List<BotCommand> getCommands() {
        return commands
            .stream()
            .map(CommandInterface::toApiCommand)
            .toList();
    }

}
