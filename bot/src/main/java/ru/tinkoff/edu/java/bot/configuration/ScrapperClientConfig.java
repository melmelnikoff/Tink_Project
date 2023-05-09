package ru.tinkoff.edu.java.bot.configuration;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import ru.tinkoff.edu.java.bot.client.api.ScrapperClient;
import ru.tinkoff.edu.java.bot.client.webclient.ScrapperWebClient;

@Configuration
public class ScrapperClientConfig {

    public static final int TIMEOUT = 1000;
    private static final String BASE_URL = "https://localhost:8080";

    @Bean
    public WebClient webClient() {
        final HttpClient httpClient = HttpClient
            .create()
            .compress(true)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIMEOUT)
            .doOnConnected(connection -> {
                connection.addHandlerLast(new ReadTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS));
                connection.addHandlerLast(new WriteTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS));
            });

        return WebClient.builder()
            .baseUrl(BASE_URL)
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .build();
    }

    @Bean
    public ScrapperClient scrapperWebClient(WebClient webClient) {
        return new ScrapperWebClient(webClient);
    }
}
