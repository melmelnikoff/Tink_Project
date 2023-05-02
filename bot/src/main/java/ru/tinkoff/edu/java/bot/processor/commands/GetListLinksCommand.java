package ru.tinkoff.edu.java.bot.processor.commands;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.processor.message.MessageSenderImpl;
import ru.tinkoff.edu.java.bot.dto.LinkResponse;
import ru.tinkoff.edu.java.bot.service.LinkServiceImpl;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class GetListLinksCommand implements CommandInterface {

    private final MessageSenderImpl messageSender;
    private final LinkServiceImpl linkService;

    @Override
    public String command() {
        return "/links";
    }

    @Override
    public String description() {
        return "get all tracked links";
    }

    @Override
    public SendMessage process(Update update) {
        List<LinkResponse> links = linkService.getAllLinks(update.message().chat().id());
        if (links.size() == 0){
           return messageSender.sendMessage(update,
                   "You are not tracking links yet. Try using /track to start tracking links");
        }
        return messageSender.sendTemplateUpdate(update, "links.ftl", Map.of("links", links));
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
