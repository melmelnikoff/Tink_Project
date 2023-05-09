package ru.tinkoff.edu.java.scrapper.client.dto;

import java.util.List;

public record LinkUpdateRequest(
    Long id,
    String url,
    String description,
    List<Long> tgChatIds
) {
}
