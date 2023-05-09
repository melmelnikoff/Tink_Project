package ru.tinkoff.edu.java.parser;

import java.util.Objects;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.parser.parseresult.ParseResult;

@Component
public class StackOverflowParser extends AbstractParser {
    private static final String HOST = "stackoverflow.com";
    private static final Integer MIN_LENGTH = 4;
    private static final Integer POSITION_OF_QUESTION_ID = 3;

    @Override
    protected ParseResult parseUrl(String[] splitUrl) {
        if (!Objects.equals(splitUrl[1], HOST) || splitUrl.length <= MIN_LENGTH) {
            return null;
        }

        //Types of link:
        //https://stackoverflow.com/questions/ID/QUESTION
        return new ParseResult.StackOverflowQuestionId(splitUrl[POSITION_OF_QUESTION_ID]);

    }
}
