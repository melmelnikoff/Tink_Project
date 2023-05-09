package ru.tinkoff.edu.java.parser.configuration;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.parser.GitHubParser;
import ru.tinkoff.edu.java.parser.Parser;
import ru.tinkoff.edu.java.parser.ParserInterface;
import ru.tinkoff.edu.java.parser.StackOverflowParser;

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
