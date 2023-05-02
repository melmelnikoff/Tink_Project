package ru.tinkoff.edu.java.bot.client.webclient;

import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.ApiErrorResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.bot.client.api.ScrapperClient;
import ru.tinkoff.edu.java.bot.exception.ApiClientErrorException;
import ru.tinkoff.edu.java.bot.exception.ApiInternalServerErrorException;
import ru.tinkoff.edu.java.bot.dto.AddLinkRequest;
import ru.tinkoff.edu.java.bot.dto.LinkResponse;
import ru.tinkoff.edu.java.bot.dto.ListLinksResponse;
import ru.tinkoff.edu.java.bot.dto.RemoveLinkRequest;

import java.net.URI;
import java.util.Optional;

@Slf4j
public class ScrapperWebClient implements ScrapperClient {
    private static final String BASE_URL = "http://localhost:8080";
    private static final String LINKS_URI = "/links";
    private static final String TG_CHAT_URI = "/tg-chat/{id}";
    private static final String TG_CHAT_ID_HEADER = "Tg-Chat-Id";


    private final String baseUrl;
    private final WebClient webClient;

    public ScrapperWebClient(WebClient webClient) {
        this.webClient = webClient;
        this.baseUrl = BASE_URL;
    }

    public ScrapperWebClient(String baseUrl, WebClient webClient) {
        this.baseUrl = baseUrl;
        this.webClient = webClient;
    }

    @Override
    public Optional<LinkResponse> linksDelete(Long tgChatId, URI url) {
        return webClient
                .method(HttpMethod.DELETE)
                .uri(BASE_URL + LINKS_URI)
                .header(TG_CHAT_ID_HEADER, tgChatId.toString())
                .bodyValue(new RemoveLinkRequest(url))
                .retrieve()
                .bodyToMono(LinkResponse.class)
                .onErrorResume(exception -> Mono.empty())
                .blockOptional();
    }

    @Override
    public ListLinksResponse linksGet(Long tgChatId) {
        return webClient
                .get()
                .uri(BASE_URL + LINKS_URI)
                .header(TG_CHAT_ID_HEADER, tgChatId.toString())
                .retrieve()
                .bodyToMono(ListLinksResponse.class)
                .onErrorResume(exception -> Mono.empty())
                .block();
    }

    @Override
    public Optional<LinkResponse> linksPost(Long tgChatId, URI url) {
        return webClient
                .post()
                .uri(BASE_URL + LINKS_URI)
                .header(TG_CHAT_ID_HEADER, tgChatId.toString())
                .body(BodyInserters.fromValue(new AddLinkRequest(url)))
                .retrieve()
                .bodyToMono(LinkResponse.class)
                .onErrorResume(exception -> Mono.empty())
                .blockOptional();
    }

    @Override
    public Void tgChatIdDelete(Long tgChatId) {
        return webClient
                .delete()
                .uri(BASE_URL + TG_CHAT_URI, tgChatId)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        resp -> onClientErrorInternal(resp, "deleting chat")
                )
                .onStatus(
                        HttpStatusCode::is5xxServerError,
                        resp -> onServerErrorInternal(resp, "deleting chat")
                )
                .bodyToMono(Void.class).block();
    }

    @Override
    public Void tgChatIdPost(Long tgChatId) {
        return webClient
                .post()
                .uri(BASE_URL + TG_CHAT_URI, tgChatId)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        resp -> onClientErrorInternal(resp, "registering new chat")
                )
                .onStatus(
                        HttpStatusCode::is5xxServerError,
                        resp -> onServerErrorInternal(resp, "registering new chat")
                )
                .bodyToMono(Void.class).block();
    }



    private Mono<? extends RuntimeException> onClientErrorInternal(ClientResponse resp, String when) {
        log.error("Incorrect Scrapper API request while " + when);
        return resp.bodyToMono(ApiErrorResponse.class).map(ApiClientErrorException::new);
    }

    private Mono<? extends RuntimeException> onServerErrorInternal(ClientResponse resp, String when) {
        log.error("Scrapper API server error while " + when);
        return resp.bodyToMono(ApiErrorResponse.class).map(ApiInternalServerErrorException::new);
    }
}
