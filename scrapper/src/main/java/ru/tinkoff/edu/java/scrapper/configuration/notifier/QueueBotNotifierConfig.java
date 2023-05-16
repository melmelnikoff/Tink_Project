package ru.tinkoff.edu.java.scrapper.configuration.notifier;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.service.notifier.QueueBotNotifier;

@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "true")
public class QueueBotNotifierConfig {

    private final RabbitTemplate template;

    @Bean
    public QueueBotNotifier queueBotNotifier(Queue queue) {
        return new QueueBotNotifier(template, queue);

    }
}
