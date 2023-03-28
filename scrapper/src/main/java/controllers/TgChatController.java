package controllers;

import dto.ApiErrorResponse;
import dto.ChatRequest;
import dto.ChatResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tg-chat")
public class TgChatController {

    @PostMapping("/{id}")
    public ResponseEntity<?> registerChat(@PathVariable int id) {
        if (id <= 0) {
            ApiErrorResponse error = new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), "Invalid request");
            return ResponseEntity.badRequest().body(error);
        }

        ChatResponse response = new ChatResponse("Chat registered");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteChat(@PathVariable int id) {
        if (id <= 0) {
            ApiErrorResponse error = new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), "Invalid request");
            return ResponseEntity.badRequest().body(error);
        }

        ChatResponse response = new ChatResponse("Chat deleted");
        return ResponseEntity.ok(response);
    }
}