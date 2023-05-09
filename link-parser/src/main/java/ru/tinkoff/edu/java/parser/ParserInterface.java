package ru.tinkoff.edu.java.parser;

import org.jetbrains.annotations.Nullable;
import ru.tinkoff.edu.java.parser.parseresult.ParseResult;

public interface ParserInterface {
    @Nullable ParseResult parse(String url);
}
