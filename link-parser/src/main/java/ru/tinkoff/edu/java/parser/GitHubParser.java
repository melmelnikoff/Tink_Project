package ru.tinkoff.edu.java.parser;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.parser.parseresult.ParseResult;

import java.util.Objects;

@Component
public class GitHubParser extends AbstractParser {
    private static final String HOST = "github.com";
    @Override
    protected ParseResult parseUrl(String[] splitUrl) {
        if (!Objects.equals(splitUrl[1], HOST) || splitUrl.length < 4){
            return null;
        }

        //Types of link:
        //https://github.com/USERNAME/REPOSITORY/
        return new ParseResult.GitHubUserRepository(splitUrl[2], splitUrl[3]);

    }
}
