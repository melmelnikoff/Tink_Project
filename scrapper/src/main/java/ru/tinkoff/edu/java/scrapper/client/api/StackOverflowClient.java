package ru.tinkoff.edu.java.scrapper.client.api;

import ru.tinkoff.edu.java.scrapper.client.dto.StackOverflowApiResponse;

public interface StackOverflowClient {
    StackOverflowApiResponse fetchQuestion(String id);
}
