package ru.tinkoff.edu.java.scrapper.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.IntegrationEnvironment;
import ru.tinkoff.edu.java.scrapper.entity.TgChat;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.JdbcLinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.JdbcTgChatRepository;

import static java.time.OffsetDateTime.now;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
public class JdbcTgChatRepositoryTest extends IntegrationEnvironment {

    @Autowired
    JdbcTgChatRepository tgChatRepository;

    @Autowired
    JdbcLinkRepository linkRepository;


    private static TgChat makeTestChat() {
        return new TgChat()
                .setId(1L)
                .setCreatedAt(now());
    }

    @Test
    @Transactional
    @Rollback
    @Sql("/sql/add_chats.sql")
    public void findAll__dbHasChats_success() {
        assertThat(tgChatRepository.findAll()).hasSize(2);
    }

    @Test
    @Transactional
    @Rollback
    public void save__saveChat_success() {
        //given
        TgChat chat = makeTestChat();

        //when
        tgChatRepository.save(chat);
        TgChat foundChat = tgChatRepository.findAll().get(0);

        //then
        assertAll(
                () -> assertThat(foundChat.getId()).isEqualTo(1L)
        );
    }


    @Test
    @Transactional
    @Rollback
    @Sql("/sql/add_chats.sql")
    public void findById__dbHasChatWithId_success() {
        assertAll(
                () -> assertThat(tgChatRepository.findById(1L)).isNotEmpty(),
                () -> assertThat(tgChatRepository.findById(2L)).isNotEmpty()
        );
    }


    @Test
    @Transactional
    @Rollback
    @Sql("/sql/add_chats.sql")
    public void deleteById__dbHasChatWithId_success() {
        tgChatRepository.deleteById(1L);

        assertThat(tgChatRepository.findAll()).hasSize(1);

    }

}

//TODO: more tests