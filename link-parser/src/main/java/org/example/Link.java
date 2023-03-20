package org.example;
import java.net.URI;
import java.net.URISyntaxException;

public sealed interface Link permits GitHubLink, StackOverflowLink, NullLink {
    String parse();
}

record GitHubLink(String user, String repo) implements Link {
    public String parse() {
        return user + " - " + repo;
    }
}

record StackOverflowLink(String id) implements Link {
    public String parse() {
        return id;
    }
}

record NullLink() implements Link {
    public String parse() {
        return null;
    }
}

