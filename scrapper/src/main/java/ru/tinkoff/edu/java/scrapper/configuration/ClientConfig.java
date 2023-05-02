package ru.tinkoff.edu.java.scrapper.configuration;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import ru.tinkoff.edu.java.scrapper.client.api.BotClient;
import ru.tinkoff.edu.java.scrapper.client.api.GitHubClient;
import ru.tinkoff.edu.java.scrapper.client.api.StackOverflowClient;
import ru.tinkoff.edu.java.scrapper.client.webclient.BotWebClient;
import ru.tinkoff.edu.java.scrapper.client.webclient.GitHubWebClient;
import ru.tinkoff.edu.java.scrapper.client.webclient.StackOverflowWebClient;

import java.util.concurrent.TimeUnit;

@Configuration
public class ClientConfig {

    @Value("${webclient.timeout}")
    int timeout;

    @Bean
    public WebClient webClient() {
        final HttpClient httpClient = HttpClient
                .create()
                .compress(true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, timeout)
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(timeout, TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(timeout, TimeUnit.MILLISECONDS));
                });

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    @Bean
    public GitHubClient gitHubWebClient(WebClient webClient,
                                        @Value("${github.url.base}") String baseUrl) {
        return new GitHubWebClient(webClient, baseUrl);
    }

    @Bean
    public StackOverflowClient stackOverflowWebClient(WebClient webClient,
                                                      @Value("${stackoverflow.url.base}") String baseUrl) {
        return new StackOverflowWebClient(webClient, baseUrl);
    }

    @Bean
    BotClient botWebClient(WebClient webClient,
                           @Value("${bot.url.base}") String baseUrl) {
        return new BotWebClient(webClient, baseUrl);
    }

}
