package ru.tinkoff.edu.java.scrapper.jpa;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.IntegrationEnvironment;
import ru.tinkoff.edu.java.scrapper.ScrapperApplication;
import ru.tinkoff.edu.java.scrapper.entity.Link;
import ru.tinkoff.edu.java.scrapper.repository.jpa.JpaLinkRepository;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(classes = ScrapperApplication.class)
public class JpaLinkRepositoryTest extends IntegrationEnvironment {
    @Autowired
    JpaLinkRepository jpaLinkRepository;

    private static Link makeTestLink() {
        return new Link().setUrl("url.com");
    }

    @Test
    @Transactional
    @Rollback
    public void findLinkByUrl__dbHasLinkWithUrl_success() {
        //given
        Link link = makeTestLink();

        //when
        jpaLinkRepository.save(link);
        Optional<Link> foundLink = jpaLinkRepository.findByUrl("url.com");

        //then
        assertAll(
            () -> AssertionsForClassTypes.assertThat(foundLink).isNotEmpty(),
            () -> AssertionsForClassTypes.assertThat(foundLink.get().getUrl()).isEqualTo(link.getUrl())
        );

    }

    @Test
    @Transactional
    @Rollback
    @Sql("/sql/add_links.sql")
    public void update__dbHasLinkToUpdate_success() {
        //given
        Link link = jpaLinkRepository.findByUrl("https://link.com").get();

        //when
        link.setLastCheckTime(OffsetDateTime.now())
            .setUpdatedAt(OffsetDateTime.now())
            .setUpdatesCount(10);

        jpaLinkRepository.update(
            link.getLastCheckTime(),
            link.getUpdatedAt(),
            link.getUpdatesCount(),
            link.getId()
        );

        //then
        assertAll(
            () -> assertThat(jpaLinkRepository.findByUrl("https://link.com").get().getUpdatesCount())
                .isEqualTo(10)
        );

    }

    @Test
    void findCheckedLongTimeAgoLinks__dbIsEmpty_returnsEmptyList() {
        assertThat(jpaLinkRepository.findCheckedLongTimeAgoLinks(5)).isEmpty();
    }

    @Test
    @Transactional
    @Rollback
    @Sql("/sql/add_links_with_last_check_time.sql")
    void findCheckedLongTimeAgoLinks__dbHasLinks_success() {
        List<Link> links = jpaLinkRepository.findCheckedLongTimeAgoLinks(4);
        assertAll(
            () -> AssertionsForClassTypes.assertThat(links.get(0).getUrl()).isEqualTo("https://link4.com"),
            () -> AssertionsForClassTypes.assertThat(links.get(1).getUrl()).isEqualTo("https://link2.com"),
            () -> AssertionsForClassTypes.assertThat(links.get(2).getUrl()).isEqualTo("https://link3.com"),
            () -> AssertionsForClassTypes.assertThat(links.get(3).getUrl()).isEqualTo("https://link1.com")
        );

    }
}
