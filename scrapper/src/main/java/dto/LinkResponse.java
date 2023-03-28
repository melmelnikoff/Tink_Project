package dto;

import io.swagger.v3.oas.annotations.links.Link;

public class LinkResponse {
    private String url;
    private String description;

    public LinkResponse(String url, String description) {
        this.url = url;
        this.description = description;
    }

    public LinkResponse(Link link) {

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}