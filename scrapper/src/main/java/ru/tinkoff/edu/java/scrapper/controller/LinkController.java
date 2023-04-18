package ru.tinkoff.edu.java.scrapper.controller;


import lombok.RequiredArgsConstructor;
import org.openapitools.api.LinksApi;
import org.openapitools.model.AddLinkRequest;
import org.openapitools.model.LinkResponse;
import org.openapitools.model.ListLinksResponse;
import org.openapitools.model.RemoveLinkRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.scrapper.entity.Link;
import ru.tinkoff.edu.java.scrapper.exception.DuplicateLinkException;
import ru.tinkoff.edu.java.scrapper.exception.ResourceNotFoundException;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

import java.net.URISyntaxException;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class LinkController implements LinksApi {

    private final LinkService linkService;


    @Override
    public ResponseEntity<LinkResponse> linksDelete(Long tgChatId, RemoveLinkRequest removeLinkRequest)
            throws ResourceNotFoundException, URISyntaxException {

        Link link = linkService.remove(tgChatId, removeLinkRequest.getLink());
        return ResponseEntity.status(HttpStatus.OK)
                .body(convertToLinkResponse(link));
    }

    @Override
    public ResponseEntity<ListLinksResponse> linksGet(Long tgChatId)
            throws ResourceNotFoundException, URISyntaxException {

        Collection<Link> links = linkService.listAll(tgChatId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(convertToListLinkResponse(links));
    }


    @Override
    public ResponseEntity<LinkResponse> linksPost(Long tgChatId, AddLinkRequest addLinkRequest)
            throws DuplicateLinkException, URISyntaxException {

        Link link = linkService.add(tgChatId, addLinkRequest.getLink());
        return ResponseEntity.status(HttpStatus.OK)
                .body(convertToLinkResponse(link));
    }


    private LinkResponse convertToLinkResponse(Link link) throws URISyntaxException {
        return LinkResponse.builder()
                .id(link.getId())
                .url(link.getUrl())
                .build();
    }

    private ListLinksResponse convertToListLinkResponse(Collection<Link> linksToResponse) throws URISyntaxException {
        return ListLinksResponse.builder()
                .links(linksToResponse.stream().map(link -> {
                    try {
                        return convertToLinkResponse(link);
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                }).toList())
                .size(linksToResponse.size())
                .build();
    }

}
