package ru.tinkoff.edu.java.scrapper.jdbc;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.IntegrationEnvironment;
import ru.tinkoff.edu.java.scrapper.ScrapperApplication;
import ru.tinkoff.edu.java.scrapper.entity.Link;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.JdbcLinkRepository;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(classes = ScrapperApplication.class)
public class JdbcLinkRepositoryTest extends IntegrationEnvironment {

    @Autowired
    JdbcLinkRepository linkRepository;

    private static Link makeTestLink() {
        return new Link().setUrl("url.com");
    }

    @Test
    @Transactional
    @Rollback
    @Sql("/sql/add_links.sql")
    public void findAll__dbHasLinks_success() {
        assertThat(linkRepository.findAll()).hasSize(2);
    }

    @Test
    @Transactional
    @Rollback
    public void save__saveLink_success() {
        //given
        Link link = makeTestLink();

        //when
        linkRepository.save(link);
        Link foundLink = linkRepository.findAll().get(0);

        //then
        assertAll(
            () -> assertThat(foundLink.getUrl()).isEqualTo(link.getUrl())
        );
    }

    @Test
    @Transactional
    @Rollback
    public void findLinkByUrl__dbHasLinkWithUrl_success() {
        //given
        Link link = makeTestLink();

        //when
        linkRepository.save(link);
        Optional<Link> foundLink = linkRepository.findLinkByUrl("url.com");

        //then
        assertAll(
            () -> assertThat(foundLink).isNotEmpty(),
            () -> assertThat(foundLink.get().getUrl()).isEqualTo(link.getUrl())
        );
    }

    @Test
    @Transactional
    @Rollback
    @Sql("/sql/add_links.sql")
    public void findById__dbHasLinksWithId_success() {
        long id1 = linkRepository.findLinkByUrl("https://link.com").get().getId();
        long id2 = linkRepository.findLinkByUrl("https://newlink.com").get().getId();

        assertAll(
            () -> assertThat(linkRepository.findById(id1)).isNotEmpty(),
            () -> assertThat(linkRepository.findById(id2)).isNotEmpty()
        );
    }

    @Test
    @Transactional
    @Rollback
    @Sql("/sql/add_links.sql")
    public void deleteById__dbHasLinkWithId_success() {
        //given
        var id = linkRepository.findLinkByUrl("https://link.com").get().getId();

        //when
        linkRepository.deleteById(id);

        //then
        assertThat(linkRepository.findAll()).hasSize(1);

    }

    @Test
    void findCheckedLongTimeAgoLinks__dbIsEmpty_returnsEmptyList() {
        assertThat(linkRepository.findCheckedLongTimeAgoLinks(5)).isEmpty();
    }

    @Test
    @Transactional
    @Rollback
    @Sql("/sql/add_links_with_last_check_time.sql")
    void findCheckedLongTimeAgoLinks__dbHasLinks_success() {
        List<Link> links = linkRepository.findCheckedLongTimeAgoLinks(4);
        assertAll(
            () -> assertThat(links.get(0).getUrl()).isEqualTo("https://link4.com"),
            () -> assertThat(links.get(1).getUrl()).isEqualTo("https://link2.com"),
            () -> assertThat(links.get(2).getUrl()).isEqualTo("https://link3.com"),
            () -> assertThat(links.get(3).getUrl()).isEqualTo("https://link1.com")
        );

    }
}

//TODO: more tests

