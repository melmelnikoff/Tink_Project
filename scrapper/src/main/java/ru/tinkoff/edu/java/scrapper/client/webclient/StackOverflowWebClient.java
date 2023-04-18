package ru.tinkoff.edu.java.scrapper.client.webclient;

import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.client.api.StackOverflowClient;
import ru.tinkoff.edu.java.scrapper.client.dto.GitHubApiResponse;
import ru.tinkoff.edu.java.scrapper.client.dto.StackOverflowApiResponse;
import ru.tinkoff.edu.java.scrapper.client.dto.StackOverflowItemApiResponse;

public class StackOverflowWebClient implements StackOverflowClient {

    private static final String BASE_URL = "https://api.stackexchange.com/2.3/questions/";
    private static final String STACKOVERFLOW_MANDATORY_REQUEST_PARAMS = "?order=desc&sort=activity&site=stackoverflow";


    private final WebClient webClient;
    private final String  baseUrl;

    public StackOverflowWebClient(WebClient webClient) {
        this.webClient = webClient;
        this.baseUrl = BASE_URL;
    }

    public StackOverflowWebClient(WebClient webClient, String baseUrl) {
        this.webClient = webClient;
        this.baseUrl = baseUrl;
    }


    @Override
    public StackOverflowApiResponse fetchQuestion(String id) {
        return webClient
                .get()
                .uri(baseUrl + id + STACKOVERFLOW_MANDATORY_REQUEST_PARAMS)
                .retrieve()
                .bodyToMono(StackOverflowApiResponse.class)
                .block();
    }
}
