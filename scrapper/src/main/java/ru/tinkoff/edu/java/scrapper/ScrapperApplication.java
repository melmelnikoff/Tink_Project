package ru.tinkoff.edu.java.scrapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.tinkoff.edu.java.parser.configuration.ParserConfig;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationConfig;

@Slf4j
@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(ApplicationConfig.class)
@Import(ParserConfig.class)
public class ScrapperApplication {
    public static void main(String[] args) {
        var ctx = SpringApplication.run(ScrapperApplication.class, args);
        ApplicationConfig config = ctx.getBean(ApplicationConfig.class);
        log.debug(config.test());
    }

}
