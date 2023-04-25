package ru.tinkoff.edu.java.scrapper.repository.jooq;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.entity.Link;
import ru.tinkoff.edu.java.scrapper.repository.LinkRepository;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static ru.tinkoff.edu.java.scrapper.entity.jooq.Tables.LINK;

@Primary
@RequiredArgsConstructor
@Repository
public class JooqLinkRepository implements LinkRepository {
    private final DSLContext create;

    @Override
    public Link save(Link link) {
        return create
                .insertInto(LINK, LINK.URL)
                .values(link.getUrl().toString())
                .returningResult(LINK.fields())
                .fetchOneInto(Link.class);
    }

    @Override
    public void update(Link link) {
        create.update(LINK)
                .set(LINK.LAST_CHECK_TIME, link.getLastCheckTime())
                .set(LINK.UPDATED_AT, link.getUpdatedAt())
                .set(LINK.UPDATES_COUNT, link.getUpdatesCount())
                .where(LINK.ID.eq(link.getId()))
                .execute();
    }

    @Override
    public List<Link> findAll() {
        return create
                .select(LINK.ID, LINK.URL, LINK.LAST_CHECK_TIME, LINK.UPDATED_AT, LINK.UPDATES_COUNT)
                .from(LINK)
                .fetchInto(Link.class);
    }

    @Override
    public Optional<Link> findById(Long id) {
        return create
                .select(LINK.ID, LINK.URL, LINK.LAST_CHECK_TIME, LINK.UPDATED_AT, LINK.UPDATES_COUNT)
                .from(LINK)
                .where(LINK.ID.eq(id))
                .fetchOptionalInto(Link.class);
    }

    @Override
    public Optional<Link> findLinkByUrl(URI url) {
        return create
                .select(LINK.ID, LINK.URL, LINK.LAST_CHECK_TIME, LINK.UPDATED_AT, LINK.UPDATES_COUNT)
                .from(LINK)
                .where(LINK.URL.eq(url.toString()))
                .fetchOptionalInto(Link.class);
    }

    @Override
    public void deleteById(Long id) {
        create.delete(LINK).where(LINK.ID.eq(id)).execute();

    }

    @Override
    public List<Link> findCheckedLongTimeAgoLinks(int limit) {
        return create
                .select(LINK.ID, LINK.URL, LINK.LAST_CHECK_TIME, LINK.UPDATED_AT, LINK.UPDATES_COUNT)
                .from(LINK)
                .orderBy(LINK.LAST_CHECK_TIME.asc().nullsFirst())
                .limit(limit)
                .fetchInto(Link.class);
    }

}