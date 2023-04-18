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

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UntrackLinkCommand implements CommandInterface {
    private static final String UNTRACK_REPLY = "Enter the link you want to stop tracking:";
    private static final String UNTRACK_REPLY_ERROR = "The link for untrack was entered incorrectly, try again:";

    private final MessageSenderImpl messageSender;
    private final LinkServiceImpl linkService;

    @Override
    public String command() {
        return "/untrack";
    }

    @Override
    public String description() {
        return "stop tracking link";
    }

    @Override
    public SendMessage process(Update update) {
        if(isReply(update)){
            String link = update.message().text();
            Optional<LinkResponse> linkResponse = linkService.untrackLink(update.message().chat().id(), link);

            return linkResponse.isPresent() ?
                    messageSender.sendMessage(update, "Stop tracking link %s".formatted(link)) :
                    messageSender.sendMessage(update, UNTRACK_REPLY_ERROR).replyMarkup(new ForceReply());
        }

        return messageSender.sendMessage(update, UNTRACK_REPLY).replyMarkup(new ForceReply());
    }

    @Override
    public BotCommand toApiCommand() {
        return CommandInterface.super.toApiCommand();
    }

    @Override
    public boolean supports(Update update) {
        return update.message() != null &&
                update.message().text() != null &&
                update.message().text().startsWith(command())
                || isReply(update);
    }

    private static boolean isReply(Update update) {
        Message reply = update.message().replyToMessage();
        return reply != null && (reply.text().equals(UNTRACK_REPLY) || reply.text().equals(UNTRACK_REPLY_ERROR));
    }
}
