package ru.tinkoff.edu.java.scrapper.service.notifier;

import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import ru.tinkoff.edu.java.scrapper.client.dto.LinkUpdateRequest;
import ru.tinkoff.edu.java.scrapper.entity.Link;
import ru.tinkoff.edu.java.scrapper.entity.TgChat;

@RequiredArgsConstructor
public class QueueBotNotifier implements BotNotifier {

    private final RabbitTemplate template;
    private final Queue queue;

    @Override
    public void notifyBot(Link link, String description, Collection<TgChat> tgChats) {
        LinkUpdateRequest request = new LinkUpdateRequest(
            link.getId(),
            link.getUrl(),
            description,
            tgChats.stream().map(TgChat::getId).toList()

        );

        template.convertAndSend(queue.getName(), request);

    }
}
