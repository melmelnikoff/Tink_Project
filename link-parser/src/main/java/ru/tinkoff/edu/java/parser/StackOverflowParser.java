package ru.tinkoff.edu.java.parser;

import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.parser.parseresult.ParseResult;

import java.util.Objects;

@Component
public class StackOverflowParser extends AbstractParser {
    private static final String HOST = "stackoverflow.com";

    @Override
    protected ParseResult parseUrl(String[] splitUrl) {
        if (!Objects.equals(splitUrl[1], HOST) || splitUrl.length <= 4){
            return null;
        }

        //Types of link:
        //https://stackoverflow.com/questions/ID/QUESTION
        return new ParseResult.StackOverflowQuestionId(splitUrl[3]);

    }
}
