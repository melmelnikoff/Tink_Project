package ru.tinkoff.edu.java.scrapper;

import liquibase.Liquibase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.DirectoryResourceAccessor;
import org.jooq.DSLContext;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.PlatformTransactionManager;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.tinkoff.edu.java.scrapper.configuration.acess.JdbcAccessConfig;
import ru.tinkoff.edu.java.scrapper.configuration.acess.JpaAccessConfig;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.JdbcLinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.JdbcSubscriptionRepository;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.JdbcTgChatRepository;
import ru.tinkoff.edu.java.scrapper.repository.jooq.JooqLinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.jooq.JooqSubscriptionRepository;
import ru.tinkoff.edu.java.scrapper.repository.jpa.JpaLinkRepository;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

@Testcontainers
@ContextConfiguration(classes = IntegrationEnvironment.IntegrationEnvironmentConfiguration.class)
public abstract class IntegrationEnvironment {
    @TestConfiguration
    static class IntegrationEnvironmentConfiguration {

        @Bean
        public DataSource dataSource() {

            return DataSourceBuilder.create()
                    .url(DB_CONTAINER.getJdbcUrl())
                    .username(DB_CONTAINER.getUsername())
                    .password(DB_CONTAINER.getPassword())
                    .build();
        }
    }

    public static JdbcDatabaseContainer<?> DB_CONTAINER;

    static {
        DB_CONTAINER = new PostgreSQLContainer<>("postgres:14")
                .withDatabaseName("scrapper")
                .withUsername("postgres")
                .withPassword("password");
        DB_CONTAINER.start();

        var changeLogPath = new File(".").toPath().toAbsolutePath()
                .getParent().getParent().resolve("migrations");

        try (var connection = DriverManager.getConnection(DB_CONTAINER.getJdbcUrl(),
                DB_CONTAINER.getUsername(), DB_CONTAINER.getPassword())) {

            var changeLogDir = new DirectoryResourceAccessor(changeLogPath);

            var database = new PostgresDatabase();
            database.setConnection(new JdbcConnection(connection));

            Liquibase liquibase = new Liquibase("master.xml", changeLogDir, database);
            liquibase.update();
        } catch (IOException | SQLException | LiquibaseException e) {
            throw new RuntimeException(e);
        }
    }

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", DB_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", DB_CONTAINER::getUsername);
        registry.add("spring.datasource.password", DB_CONTAINER::getPassword);
    }

}
