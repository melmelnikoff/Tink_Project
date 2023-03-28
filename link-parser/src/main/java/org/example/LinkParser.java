package org.example;

import java.net.URI;
import java.net.URISyntaxException;

public class LinkParser {
    public static Link parse(String urlString) {
        try {
            URI uri = new URI(urlString);
            String host = uri.getHost();
            String path = uri.getPath();
            String[] pathSegments = path.split("/");
            if (host.equals("github.com") && pathSegments.length >= 3) {
                String user = pathSegments[1];
                String repo = pathSegments[2];
                return new GitHubLink(user, repo);
            } else if (host.equals("stackoverflow.com") && pathSegments.length >= 3) {
                String id = pathSegments[2];
                return new StackOverflowLink(id);
            } else {
                return new NullLink();
            }
        } catch (URISyntaxException e) {
            return new NullLink();
        }
    }
}
