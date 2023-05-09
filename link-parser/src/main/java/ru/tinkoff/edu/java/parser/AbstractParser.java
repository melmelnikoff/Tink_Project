package ru.tinkoff.edu.java.parser;

import java.net.URL;
import org.jetbrains.annotations.Nullable;
import ru.tinkoff.edu.java.parser.parseresult.ParseResult;

public abstract class AbstractParser implements ParserInterface {

    @Override
    public @Nullable ParseResult parse(String url) {
        if (!isValid(url)) {
            return null;
        }

        String urlForSplit = url.replaceAll("//", "/");
        return parseUrl(urlForSplit.split("/"));

    }

    protected abstract @Nullable ParseResult parseUrl(String[] splitUrl);

    private boolean isValid(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {  // If there was an Exception
            return false;        // while creating URL object
        }
    }
}
