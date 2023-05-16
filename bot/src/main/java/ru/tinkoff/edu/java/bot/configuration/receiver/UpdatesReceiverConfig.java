package ru.tinkoff.edu.java.bot.configuration.receiver;

import com.pengrad.telegrambot.TelegramBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.bot.processor.message.MessageSender;
import ru.tinkoff.edu.java.bot.service.receiver.UpdatesReceiver;

@Configuration
public class UpdatesReceiverConfig {
    @Bean
    public UpdatesReceiver httpUpdatesReceiver(TelegramBot telegramBot, MessageSender messageSender) {
        return new UpdatesReceiver(telegramBot, messageSender);
    }
}
