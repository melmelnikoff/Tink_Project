package ru.tinkoff.edu.java.bot.service.receiver;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.LinkUpdateRequest;
import ru.tinkoff.edu.java.bot.processor.message.MessageSender;

@RequiredArgsConstructor
public class UpdatesReceiver {

    private static final String UPDATE_TEMPLATE = "update.ftl";
    private final TelegramBot telegramBot;
    private final MessageSender messageSender;

    public void alertUpdates(LinkUpdateRequest linkUpdateRequest) {
        Map<String, Object> model = Map.of("description", linkUpdateRequest.getDescription(),
            "url", linkUpdateRequest.getUrl()
        );

        for (long chatId : linkUpdateRequest.getTgChatIds()) {
            SendMessage message = messageSender.sendTemplateId(chatId, UPDATE_TEMPLATE, model);
            telegramBot.execute(message);
        }
    }
}
