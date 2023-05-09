package ru.tinkoff.edu.java.scrapper.repository.jdbc;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.tinkoff.edu.java.scrapper.entity.Link;
import ru.tinkoff.edu.java.scrapper.repository.LinkRepository;

@RequiredArgsConstructor
@Slf4j
public class JdbcLinkRepository implements LinkRepository {
    private static final String URL_KEY = "url";
    private static final String ID_KEY = "id";

    private static final String SAVE_SQL = """
        insert into link(url) values (:url) returning *
        """;
    private static final String UPDATE_SQL = """
        update link set last_check_time = :lastCheckTime, updated_at = :updatedAt,
        updates_count = :updatesCount where id = :id
        """;
    private static final String FIND_ALL_SQL = """
        select id, url, last_check_time, updated_at, updates_count from link
        """;
    private static final String FIND_BY_ID_SQL = """
        select id, url, last_check_time, updated_at, updates_count from link where id = :id
        """;
    private static final String FIND_LINK_BY_URL_SQL = """
        select id, url, last_check_time, updated_at, updates_count from link where url = :url
        """;
    private static final String FIND_CHECKED_LONG_TIME_AGO_SQL = """
        select id, url, last_check_time, updated_at, updates_count from LINK
        order by last_check_time nulls first limit :limit
        """;
    private static final String DELETE_BY_ID_SQL = """
        delete from link where id = :id
        """;
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Link> rowMapper = new DataClassRowMapper<>(Link.class);

    @Override
    public Link save(Link link) {
        return jdbcTemplate.queryForObject(
            SAVE_SQL,
            Map.of(URL_KEY, link.getUrl()),
            rowMapper
        );
    }

    public void update(Link link) {
        jdbcTemplate.update(
            UPDATE_SQL,
            Map.of("lastCheckTime", link.getLastCheckTime(),
                "updatedAt", link.getUpdatedAt(),
                "updatesCount", link.getUpdatesCount(),
                ID_KEY, link.getId()
            )
        );
    }

    @Override
    public List<Link> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, rowMapper);
    }

    @Override
    public Optional<Link> findById(Long id) {
        return Optional.ofNullable(
            DataAccessUtils.singleResult(
                jdbcTemplate.query(FIND_BY_ID_SQL, Map.of(ID_KEY, id), rowMapper)
            )
        );
    }

    @Override
    public Optional<Link> findLinkByUrl(String url) {
        return Optional.ofNullable(
            DataAccessUtils.singleResult(
                jdbcTemplate.query(FIND_LINK_BY_URL_SQL, Map.of(URL_KEY, url), rowMapper)
            )
        );
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update(DELETE_BY_ID_SQL, Map.of(ID_KEY, id));
    }

    @Override
    public List<Link> findCheckedLongTimeAgoLinks(int limit) {
        return jdbcTemplate.query(FIND_CHECKED_LONG_TIME_AGO_SQL, Map.of("limit", limit), rowMapper);
    }
}
