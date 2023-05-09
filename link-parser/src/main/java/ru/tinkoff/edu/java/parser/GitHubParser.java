package ru.tinkoff.edu.java.parser;

import java.util.Objects;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.parser.parseresult.ParseResult;

@Component
public class GitHubParser extends AbstractParser {
    private static final String HOST = "github.com";
    private static final Integer MIN_LENGTH = 4;
    private static final Integer POSITION_OF_USER = 2;
    private static final Integer POSITION_OF_REPO = 3;



    @Override
    protected ParseResult parseUrl(String[] splitUrl) {
        if (!Objects.equals(splitUrl[1], HOST) || splitUrl.length < MIN_LENGTH) {
            return null;
        }

        //Types of link:
        //https://github.com/USERNAME/REPOSITORY/
        return new ParseResult.GitHubUserRepository(splitUrl[POSITION_OF_USER], splitUrl[POSITION_OF_REPO]);

    }
}
