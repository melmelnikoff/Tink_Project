package ru.tinkoff.edu.java.scrapper.client.webclient;

import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.scrapper.client.api.BotClient;
import ru.tinkoff.edu.java.scrapper.client.dto.LinkUpdateRequest;

public class BotWebClient implements BotClient {
    private static final String BASE_URL = "http://localhost:8081";
    private static final String UPDATE = "/updates";

    private final WebClient webClient;
    private final String baseUrl;

    public BotWebClient(WebClient webClient) {
        this.webClient = webClient;
        this.baseUrl = BASE_URL;
    }

    public BotWebClient(WebClient webClient, String baseUrl) {
        this.webClient = webClient;
        this.baseUrl = baseUrl;
    }

    @Override
    public void updatesPost(LinkUpdateRequest linkUpdateRequest) {
        webClient
            .post()
            .uri(BASE_URL + UPDATE)
            .body(BodyInserters.fromValue(linkUpdateRequest))
            .retrieve()
            .toBodilessEntity()
            .onErrorResume((ex) -> Mono.empty())
            .block();

    }
}
