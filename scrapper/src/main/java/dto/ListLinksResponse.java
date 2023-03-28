package dto;

import java.util.List;

public class ListLinksResponse {
    private List<LinkResponse> links;

    public <LinkResponse> ListLinksResponse(List<LinkResponse> links) {
        this.links = (List<dto.LinkResponse>) links;
    }

    public List<LinkResponse> getLinks() {
        return links;
    }

    public void setLinks(List<LinkResponse> links) {
        this.links = links;
    }
}