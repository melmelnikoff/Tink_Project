package ru.tinkoff.edu.java.scrapper.client.api;

import ru.tinkoff.edu.java.scrapper.client.dto.LinkUpdateRequest;

public interface BotClient {
    void updatesPost(LinkUpdateRequest linkUpdateRequest);
}
