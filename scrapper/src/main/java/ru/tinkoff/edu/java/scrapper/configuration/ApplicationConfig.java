package ru.tinkoff.edu.java.scrapper.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.annotation.Validated;
import ru.tinkoff.edu.java.scrapper.configuration.acess.AccessType;

import java.time.Duration;


@EnableScheduling
@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
        @NotNull String test,
        @NotNull Scheduler scheduler,
        @NotNull AccessType databaseAccessType,
        @NotNull String exchangeName,
        @NotNull String queueName,
        @NotNull Boolean useQueue
) {
    public record Scheduler(Duration interval, Integer limit) {
    }
}
