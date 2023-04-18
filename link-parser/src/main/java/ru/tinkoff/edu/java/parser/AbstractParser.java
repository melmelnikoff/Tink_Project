package ru.tinkoff.edu.java.parser;


import org.springframework.lang.Nullable;
import ru.tinkoff.edu.java.parser.parseresult.ParseResult;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractParser implements ParserInterface{

    @Override
    public @Nullable ParseResult parse(String url) {
        if(!isValid(url)){
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
        }

        // If there was an Exception
        // while creating URL object
        catch (Exception e) {
            return false;
        }
    }
}
