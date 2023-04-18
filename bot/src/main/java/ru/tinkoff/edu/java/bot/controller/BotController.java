package ru.tinkoff.edu.java.bot.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.api.UpdatesApi;
import org.openapitools.model.LinkUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.bot.client.api.ScrapperClient;
import ru.tinkoff.edu.java.bot.dto.LinkResponse;
import ru.tinkoff.edu.java.bot.service.AlertUpdatesService;


@RestController
@RequiredArgsConstructor
@Slf4j
public class BotController implements UpdatesApi {

    private final ScrapperClient scrapperClient;
    private final AlertUpdatesService alertUpdatesService;

    @Override
    public ResponseEntity<Void> updatesPost(LinkUpdateRequest linkUpdateRequest) {
        alertUpdatesService.alertUpdates(linkUpdateRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}



