package ru.tinkoff.edu.java.scrapper.configuration.acess;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.parser.Parser;
import ru.tinkoff.edu.java.scrapper.client.api.GitHubClient;
import ru.tinkoff.edu.java.scrapper.client.api.StackOverflowClient;
import ru.tinkoff.edu.java.scrapper.repository.jpa.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.jpa.JpaTgChatRepository;
import ru.tinkoff.edu.java.scrapper.service.jpa.JpaLinkService;
import ru.tinkoff.edu.java.scrapper.service.jpa.JpaLinksUpdaterImpl;
import ru.tinkoff.edu.java.scrapper.service.jpa.JpaTgChatService;
import ru.tinkoff.edu.java.scrapper.service.notifier.BotNotifier;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfig {

    @Bean
    public JpaLinkService linkService(
        JpaLinkRepository linkRepository,
        JpaTgChatRepository tgChatRepository,
        Parser linkParser
    ) {
        return new JpaLinkService(linkRepository, tgChatRepository, linkParser);
    }

    @Bean
    public JpaTgChatService tgChatService(
        JpaTgChatRepository tgChatRepository
    ) {
        return new JpaTgChatService(tgChatRepository);
    }

    @Bean
    public JpaLinksUpdaterImpl linksUpdater(
        GitHubClient gitHubClient,
        StackOverflowClient stackOverflowClient,
        JpaLinkRepository linkRepository,
        Parser linkParser,
        BotNotifier botNotifier
    ) {
        return new JpaLinksUpdaterImpl(
            gitHubClient, stackOverflowClient, linkRepository, linkParser, botNotifier);
    }

}
