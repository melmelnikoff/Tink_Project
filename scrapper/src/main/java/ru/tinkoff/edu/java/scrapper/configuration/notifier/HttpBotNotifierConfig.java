package ru.tinkoff.edu.java.scrapper.configuration.notifier;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.client.api.BotClient;
import ru.tinkoff.edu.java.scrapper.service.notifier.HttpBotNotifier;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "false")
public class HttpBotNotifierConfig {

    @Bean
    public HttpBotNotifier httpBotNotifier(BotClient botClient) {
        return new HttpBotNotifier(botClient);
    }
}
