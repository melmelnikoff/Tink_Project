package ru.tinkoff.edu.java.bot.processor.commands;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.processor.message.MessageSenderImpl;
import ru.tinkoff.edu.java.bot.dto.LinkResponse;
import ru.tinkoff.edu.java.bot.service.LinkServiceImpl;

import java.net.URI;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TrackLinkCommand implements CommandInterface {
    private static final String TRACK_REPLY = "Enter the link you want to track:";
    private static final String TRACK_REPLY_ERROR = "The link was entered incorrectly, try again:";

    private final MessageSenderImpl messageSender;
    private final LinkServiceImpl linkService;

    @Override
    public String command() {
        return "/track";
    }

    @Override
    public String description() {
        return "add link to track";
    }

    @Override
    public SendMessage process(Update update) {

        if(isReply(update)){
            String link = update.message().text();
            Optional<LinkResponse> linkResponse = linkService.trackLink(update.message().chat().id(), URI.create(link));

            return linkResponse.isPresent() ?
                    messageSender.sendMessage(update, "Add link %s".formatted(link)) :
                    messageSender.sendMessage(update, TRACK_REPLY_ERROR).replyMarkup(new ForceReply());
        }

        return messageSender.sendMessage(update, TRACK_REPLY).replyMarkup(new ForceReply());

    }

    @Override
    public boolean supports(Update update) {
        return update.message() != null &&
                update.message().text() != null &&
                update.message().text().startsWith(command())
                || isReply(update);
    }

    @Override
    public BotCommand toApiCommand() {
        return CommandInterface.super.toApiCommand();
    }


    private static boolean isReply(Update update) {
        Message reply = update.message().replyToMessage();
        return reply != null && (reply.text().equals(TRACK_REPLY) || reply.text().equals(TRACK_REPLY_ERROR));
    }
}
