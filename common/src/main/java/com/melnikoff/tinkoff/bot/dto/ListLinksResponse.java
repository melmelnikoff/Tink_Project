package com.melnikoff.tinkoff.bot.dto;

import java.util.List;

public record ListLinksResponse(
    List<LinkResponse> links,
    int size
) {
}
