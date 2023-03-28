package controllers;

import dto.*;
import io.swagger.v3.oas.annotations.links.Link;

import java.util.ArrayList;
import java.util.List;

public class LinksController {
    private List<Link> links;

    public LinksController() {
        links = new ArrayList<>();
    }

    public ListLinksResponse listLinks() {
        return new ListLinksResponse(links);
    }


    public ChatResponse handleMessage(ChatRequest request) {
        // Здесь можно реализовать логику обработки сообщений в чате
        // Например, поиск ссылок по ключевым словам в сообщении
        // и возвращение списка ссылок в ответе
        return new ChatResponse("Спасибо, я получил ваше сообщение!");
    }

}
