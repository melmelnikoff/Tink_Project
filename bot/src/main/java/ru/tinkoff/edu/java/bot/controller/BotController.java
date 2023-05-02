package ru.tinkoff.edu.java.bot.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.api.UpdatesApi;
import org.openapitools.model.LinkUpdateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.bot.service.receiver.UpdatesReceiver;


@RestController
@RequiredArgsConstructor
@Slf4j
public class BotController implements UpdatesApi {

    private final UpdatesReceiver updatesReceiver;

    @Override
    public ResponseEntity<Void> updatesPost(LinkUpdateRequest linkUpdateRequest) {
        updatesReceiver.receiveUpdates(linkUpdateRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}



