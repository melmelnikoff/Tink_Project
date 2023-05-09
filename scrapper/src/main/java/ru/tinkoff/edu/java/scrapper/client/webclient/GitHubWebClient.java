package ru.tinkoff.edu.java.scrapper.client.webclient;

import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.client.api.GitHubClient;
import ru.tinkoff.edu.java.scrapper.client.dto.GitHubApiResponse;

public class GitHubWebClient implements GitHubClient {
    private static final String BASE_URL = "https://api.github.com/repos/";
    private static final String SLASH = "/";

    private final WebClient webClient;
    private final String baseUrl;

    public GitHubWebClient(WebClient webClient) {
        this.webClient = webClient;
        this.baseUrl = BASE_URL;
    }

    public GitHubWebClient(WebClient webClient, String baseUrl) {
        this.webClient = webClient;
        this.baseUrl = baseUrl;
    }

    @Override
    public GitHubApiResponse fetchRepository(String userName, String repository) {
        return webClient
            .get()
            .uri(baseUrl + userName + SLASH + repository)
            .retrieve()
            .bodyToMono(GitHubApiResponse.class)
            .block();
    }
}
