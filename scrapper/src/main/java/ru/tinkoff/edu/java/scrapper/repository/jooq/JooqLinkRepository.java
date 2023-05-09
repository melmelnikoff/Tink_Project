package ru.tinkoff.edu.java.scrapper.repository.jooq;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import ru.tinkoff.edu.java.scrapper.entity.Link;
import ru.tinkoff.edu.java.scrapper.repository.LinkRepository;
import static ru.tinkoff.edu.java.scrapper.entity.jooq.Tables.LINK;

@RequiredArgsConstructor
public class JooqLinkRepository implements LinkRepository {
    private final DSLContext create;

    @Override
    public Link save(Link link) {
        return create
            .insertInto(LINK, LINK.URL)
            .values(link.getUrl())
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
    public Optional<Link> findLinkByUrl(String url) {
        return create
            .select(LINK.ID, LINK.URL, LINK.LAST_CHECK_TIME, LINK.UPDATED_AT, LINK.UPDATES_COUNT)
            .from(LINK)
            .where(LINK.URL.eq(url))
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
