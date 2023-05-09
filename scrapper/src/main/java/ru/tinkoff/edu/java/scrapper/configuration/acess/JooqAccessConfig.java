package ru.tinkoff.edu.java.scrapper.configuration.acess;

import org.jooq.DSLContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.parser.Parser;
import ru.tinkoff.edu.java.scrapper.client.api.GitHubClient;
import ru.tinkoff.edu.java.scrapper.client.api.StackOverflowClient;
import ru.tinkoff.edu.java.scrapper.repository.jooq.JooqLinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.jooq.JooqSubscriptionRepository;
import ru.tinkoff.edu.java.scrapper.repository.jooq.JooqTgChatRepository;
import ru.tinkoff.edu.java.scrapper.service.jooq.JooqLinkService;
import ru.tinkoff.edu.java.scrapper.service.jooq.JooqLinksUpdaterImpl;
import ru.tinkoff.edu.java.scrapper.service.jooq.JooqTgChatService;
import ru.tinkoff.edu.java.scrapper.service.notifier.BotNotifier;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jooq") //TODO ???
public class JooqAccessConfig {

    @Bean
    public JooqTgChatRepository jooqChatRepository(DSLContext dslContext) {
        return new JooqTgChatRepository(dslContext);
    }

    @Bean
    public JooqLinkRepository jooqLinkRepository(DSLContext dslContext) {
        return new JooqLinkRepository(dslContext);
    }

    @Bean
    public JooqSubscriptionRepository jooqSubscriptionRepository(
        DSLContext dslContext,
        JooqLinkRepository linkRepository,
        JooqTgChatRepository tgChatRepository
    ) {

        return new JooqSubscriptionRepository(dslContext, linkRepository, tgChatRepository);
    }

    @Bean
    public JooqTgChatService jooqChatService(JooqTgChatRepository tgChatRepository) {
        return new JooqTgChatService(tgChatRepository);
    }

    @Bean
    public JooqLinkService jooqLinkService(
        JooqLinkRepository linkRepository,
        JooqSubscriptionRepository subscriptionRepository,
        Parser linkParser
    ) {
        return new JooqLinkService(linkRepository, subscriptionRepository, linkParser);
    }

    @Bean
    public JooqLinksUpdaterImpl jooqLinksUpdaterImpl(
        GitHubClient gitHubClient,
        StackOverflowClient stackOverflowClient,
        JooqLinkRepository linkRepository,
        JooqSubscriptionRepository subscriptionRepository,
        Parser linkParser,
        BotNotifier botNotifier
    ) {
        return new JooqLinksUpdaterImpl(
            gitHubClient, stackOverflowClient, linkRepository, subscriptionRepository, linkParser, botNotifier);
    }
}
