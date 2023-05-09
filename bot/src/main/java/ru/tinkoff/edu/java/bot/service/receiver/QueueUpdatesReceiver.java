package ru.tinkoff.edu.java.bot.service.receiver;

import com.pengrad.telegrambot.model.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.LinkUpdateRequest;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Slf4j
@RequiredArgsConstructor
public class QueueUpdatesReceiver {

    private final UpdatesReceiver updatesReceiver;

    @RabbitListener(queues = "${app.queue-name}")
    public void receiveUpdates(LinkUpdateRequest linkUpdateRequest) {
        log.info("From Queue");
        updatesReceiver.alertUpdates(linkUpdateRequest);
    }

    @RabbitListener(queues = "${app.queue-name}.dlq")
    public void processFailedMessagesRequeue(Message failedMessage) {
        log.error("Error while receiving update: " + failedMessage);
    }
}
