package ru.tinkoff.edu.java.scrapper.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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

@RestController
@RequiredArgsConstructor
public class LinkController implements LinksApi {

    private final LinkService linkService;

    @Override
    @SneakyThrows
    public ResponseEntity<LinkResponse> linksDelete(Long tgChatId, RemoveLinkRequest removeLinkRequest)
        throws ResourceNotFoundException {

        Link link = linkService.remove(tgChatId, removeLinkRequest.getLink());
        return ResponseEntity.status(HttpStatus.OK)
            .body(convertToLinkResponse(link));
    }

    @Override
    public ResponseEntity<ListLinksResponse> linksGet(Long tgChatId)
        throws ResourceNotFoundException {

        Collection<Link> links = linkService.listAll(tgChatId);
        return ResponseEntity.status(HttpStatus.OK)
            .body(convertToListLinkResponse(links));
    }

    @Override
    @SneakyThrows
    public ResponseEntity<LinkResponse> linksPost(Long tgChatId, AddLinkRequest addLinkRequest)
        throws DuplicateLinkException {

        Link link = linkService.add(tgChatId, addLinkRequest.getLink());
        return ResponseEntity.status(HttpStatus.OK)
            .body(convertToLinkResponse(link));
    }

    private LinkResponse convertToLinkResponse(Link link) throws URISyntaxException {
        LinkResponse linkResponse = new LinkResponse();
        linkResponse.setId(link.getId());
        linkResponse.setUrl(URI.create(link.getUrl()));
        return linkResponse;

    }

    private ListLinksResponse convertToListLinkResponse(Collection<Link> linksToResponse) {
        ListLinksResponse listLinksResponse = new ListLinksResponse();
        listLinksResponse.setLinks(
            linksToResponse.stream().map(link -> {
                try {
                    return convertToLinkResponse(link);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }).toList()
        );
        listLinksResponse.setSize(linksToResponse.size());
        return listLinksResponse;
    }

}
