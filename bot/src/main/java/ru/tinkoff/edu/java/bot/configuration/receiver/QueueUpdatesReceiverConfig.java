package ru.tinkoff.edu.java.bot.configuration.receiver;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.bot.service.receiver.QueueUpdatesReceiver;
import ru.tinkoff.edu.java.bot.service.receiver.UpdatesReceiver;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "true")
public class QueueUpdatesReceiverConfig {

    @Bean
    public QueueUpdatesReceiver queueUpdatesReceiver(UpdatesReceiver updatesReceiver) {
        return new QueueUpdatesReceiver(updatesReceiver);
    }
}
