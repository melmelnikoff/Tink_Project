package ru.tinkoff.edu.java.parser.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.parser.GitHubParser;
import ru.tinkoff.edu.java.parser.Parser;
import ru.tinkoff.edu.java.parser.ParserInterface;
import ru.tinkoff.edu.java.parser.StackOverflowParser;
import ru.tinkoff.edu.java.parser.parseresult.ParseResult;

import java.util.List;

@Configuration
public class ParserConfig {
    @Bean
    public GitHubParser githubParser() {
        return new GitHubParser();
    }

    @Bean
    public StackOverflowParser stackOverflowUriParser() {
        return new StackOverflowParser();
    }

    @Bean
    public Parser parser(List<ParserInterface> parsers) {
        return new Parser(parsers);
    }
}
