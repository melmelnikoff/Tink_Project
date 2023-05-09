package ru.tinkoff.edu.java.scrapper.configuration.acess;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.tinkoff.edu.java.parser.Parser;
import ru.tinkoff.edu.java.scrapper.client.api.GitHubClient;
import ru.tinkoff.edu.java.scrapper.client.api.StackOverflowClient;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.JdbcLinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.JdbcSubscriptionRepository;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.JdbcTgChatRepository;
import ru.tinkoff.edu.java.scrapper.service.jdbc.JdbcLinkService;
import ru.tinkoff.edu.java.scrapper.service.jdbc.JdbcLinksUpdaterImpl;
import ru.tinkoff.edu.java.scrapper.service.jdbc.JdbcTgChatService;
import ru.tinkoff.edu.java.scrapper.service.notifier.BotNotifier;

@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfig {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Bean
    public JdbcTgChatRepository jdbcTgChatRepository() {
        return new JdbcTgChatRepository(jdbcTemplate);
    }

    @Bean
    public JdbcLinkRepository jdbcLinkRepository() {
        return new JdbcLinkRepository(jdbcTemplate);
    }

    @Bean
    public JdbcSubscriptionRepository jdbcSubscriptionRepository(
        JdbcLinkRepository linkRepository,
        JdbcTgChatRepository tgChatRepository
    ) {
        return new JdbcSubscriptionRepository(linkRepository, tgChatRepository, jdbcTemplate);
    }

    @Bean
    public JdbcTgChatService jdbcChatService(JdbcTgChatRepository tgChatRepository) {
        return new JdbcTgChatService(tgChatRepository);
    }

    @Bean
    public JdbcLinkService jdbcLinkService(
        JdbcLinkRepository linkRepository,
        JdbcSubscriptionRepository subscriptionRepository,
        Parser linkParser
    ) {
        return new JdbcLinkService(linkRepository, subscriptionRepository, linkParser);
    }

    @Bean
    public JdbcLinksUpdaterImpl jdbcLinksUpdaterImpl(
        GitHubClient gitHubClient,
        StackOverflowClient stackOverflowClient,
        JdbcLinkRepository linkRepository,
        JdbcSubscriptionRepository subscriptionRepository,
        Parser linkParser,
        BotNotifier botNotifier
    ) {
        return new JdbcLinksUpdaterImpl(
            gitHubClient, stackOverflowClient, linkRepository, subscriptionRepository, linkParser, botNotifier);
    }

}
