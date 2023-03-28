package bot.controller;

import dto.ApiErrorResponse;
import dto.LinkUpdate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BotController {
    @PostMapping("/updates")
    public ResponseEntity<?> processUpdate(@RequestBody LinkUpdate linkUpdate) {
        //TODO: обработка запроса и возвращение ответа
        return ResponseEntity.ok().build();
    }
}