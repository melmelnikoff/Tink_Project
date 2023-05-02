package ru.tinkoff.edu.java.scrapper.jdbc;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.IntegrationEnvironment;
import ru.tinkoff.edu.java.scrapper.entity.Link;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.JdbcLinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.JdbcSubscriptionRepository;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.JdbcTgChatRepository;

import static java.time.OffsetDateTime.now;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JdbcSubscriptionRepositoryTest extends IntegrationEnvironment {

    @Autowired
    JdbcSubscriptionRepository subscriptionRepository;

    @Autowired
    JdbcLinkRepository linkRepository;

    @Autowired
    JdbcTgChatRepository tgChatRepository;

    @Test
    @Transactional
    @Rollback
    @Sql("/sql/add_subscriptions.sql")
    public void findLinksByChatId__dbHasLinksById_success() {
        assertAll(
                () -> assertThat(subscriptionRepository.findLinksByChatId(1L)).hasSize(2),
                () -> assertThat(subscriptionRepository.findLinksByChatId(2L)).hasSize(1)

        );
    }

    @Test
    @Transactional
    @Rollback
    @Sql("/sql/add_subscriptions.sql")
    public void findChatsByLinkId__dbHasChatsById_success() {
        assertAll(
                () -> assertThat(subscriptionRepository.findChatsByLinkId(1L)).hasSize(2),
                () -> assertThat(subscriptionRepository.findChatsByLinkId(2L)).hasSize(1)

        );
    }

    @Test
    @Transactional
    @Rollback
    @Sql("/sql/add_subscriptions.sql")
    void addLinkToChat__dbHasChatAndLink_addsLink() {
        //given
        Link link = linkRepository.findById(2L).orElseThrow();

        //when
        subscriptionRepository.addLinkToChat(2L, link);

        //then
        assertThat(subscriptionRepository.findLinksByChatId(2L)).hasSize(2);
    }


    @Test
    @Transactional
    @Rollback
    @Sql("/sql/add_subscriptions.sql")
    void addLinkToChat__chatAlreadyHasLink_throws() {
        Link link = linkRepository.findAll().get(0);

        assertThatThrownBy(() -> subscriptionRepository.addLinkToChat(1L, link))
                .isInstanceOf(DataAccessException.class);
    }

    @Test
    @Transactional
    @Rollback
    @Sql("/sql/add_subscriptions.sql")
    void deleteLinkFromChat__dbHasChatAndLink_addsLink() {
        //given
        Link link = linkRepository.findById(2L).orElseThrow();

        //when
        subscriptionRepository.deleteLinkFromChat(1L, link);

        //then
        assertThat(subscriptionRepository.findLinksByChatId(2L)).hasSize(1);
    }
}

//TODO: more tests