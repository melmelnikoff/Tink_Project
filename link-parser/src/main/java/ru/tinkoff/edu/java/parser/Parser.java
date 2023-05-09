package ru.tinkoff.edu.java.parser;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.parser.parseresult.ParseResult;

@Service
@RequiredArgsConstructor
public class Parser {

    private final List<ParserInterface> parsers;

    public ParseResult parse(String url) {
        return parsers
            .stream()
            .map(parser -> parser.parse(url))
            .filter(Objects::nonNull)
            .findFirst()
            .orElse(null);
    }
}
