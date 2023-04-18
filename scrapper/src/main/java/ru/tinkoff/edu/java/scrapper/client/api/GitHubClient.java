package ru.tinkoff.edu.java.scrapper.client.api;


import ru.tinkoff.edu.java.scrapper.client.dto.GitHubApiResponse;

public interface GitHubClient {
    GitHubApiResponse fetchRepository(String userName, String repository);
}
