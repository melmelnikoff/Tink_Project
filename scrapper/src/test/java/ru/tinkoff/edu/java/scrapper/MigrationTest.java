package ru.tinkoff.edu.java.scrapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MigrationTest extends IntegrationEnvironment {

    @Test
    @Transactional
    @Rollback
    void migrationsAreRunningSuccessfully() throws Exception {
        //given
        try (var connection = DB_CONTAINER.createConnection("")) {
            PreparedStatement preparedStatementForUpdate = connection.prepareStatement
                ("INSERT INTO tg_chat(id, created_at) VALUES (1, now())");

            PreparedStatement preparedStatementForQuery = connection.prepareStatement("SELECT * FROM tg_chat");

            //when
            int resultUpdate = preparedStatementForUpdate.executeUpdate();

            ResultSet resultQuery = preparedStatementForQuery.executeQuery();
            resultQuery.next();

            //then
            assertAll(
                () -> assertEquals(1, resultUpdate),
                () -> assertEquals(1, resultQuery.getLong("id"))
            );
        }
    }
}
