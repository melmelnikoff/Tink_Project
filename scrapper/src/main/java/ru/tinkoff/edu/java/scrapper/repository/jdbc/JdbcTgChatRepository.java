package ru.tinkoff.edu.java.scrapper.repository.jdbc;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.tinkoff.edu.java.scrapper.entity.TgChat;
import ru.tinkoff.edu.java.scrapper.repository.TgChatRepository;

@RequiredArgsConstructor
public class JdbcTgChatRepository implements TgChatRepository {

    private static final String SAVE_SQL = """
        insert into tg_chat(id, created_at) values (:id, :createdAt) returning *
        """;
    private static final String FIND_ALL_SQL = """
        select id, created_at from tg_chat;
        """;
    private static final String FIND_BY_ID_SQL = """
        select id, created_at from tg_chat where id = :id
        """;
    private static final String DELETE_BY_ID_SQL = """
        delete FROM tg_chat where id = :id
        """;
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<TgChat> rowMapper = new DataClassRowMapper<>(TgChat.class);

    @Override
    public void save(TgChat tgChat) {
        jdbcTemplate.queryForObject(
            SAVE_SQL,
            new BeanPropertySqlParameterSource(tgChat),
            rowMapper
        );
    }

    @Override
    public List<TgChat> findAll() {
        return jdbcTemplate.query(
            FIND_ALL_SQL,
            rowMapper
        );
    }

    @Override
    public Optional<TgChat> findById(Long id) {
        return Optional.ofNullable(
            DataAccessUtils.singleResult(
                jdbcTemplate.query(FIND_BY_ID_SQL, Map.of("id", id), rowMapper)
            )
        );
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update(DELETE_BY_ID_SQL, Map.of("id", id));

    }
}
