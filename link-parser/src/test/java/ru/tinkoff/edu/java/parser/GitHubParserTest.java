package ru.tinkoff.edu.java.parser;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.tinkoff.edu.java.parser.parseresult.ParseResult;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class GitHubParserTest {

    private final GitHubParser gitHubParser = new GitHubParser();

    private static Stream<Arguments> getValidLinksUsersAndRepos() {
        return Stream.of(
            Arguments.of("https://github.com/melmelnikoff/Tink_Project", "melmelnikoff", "Tink_project"),
            Arguments.of("https://github.com/sanyarnd/servers/tree/master/handystuff", "sanyarnd", "servers"),
            Arguments.of("https://github.com/pengrad/java-telegram-bot-api/tree/", "pengrad", "java-telegram-bot-api")
        );
    }

    private static Stream<Arguments> getInvalidLinks() {
        return Stream.of(
            "https://github.com/",
            "https://github.com/melmelnikoff/Tink_Project",
            "https://stackoverflow.com/questions/14743516/create-map-in-java",
            "",
            "https://github.com/melmelnikoff/Tink_Project"

        ).map(Arguments::of);
    }

    @ParameterizedTest
    @MethodSource("getValidLinksUsersAndRepos")
    void parse__parseValidUrl_returnStringUserAndStringRepo(String validLink, String user, String repo) {
        //given

        //when
        var parsedUrl = (ParseResult.GitHubUserRepository) gitHubParser.parse(validLink);

        //then
        assertAll(
            () -> assertEquals(user, parsedUrl.userName(), ""),
            () -> assertEquals(repo, parsedUrl.repository())
        );
    }

    @ParameterizedTest
    @MethodSource("getInvalidLinks")
    void parse__parseInvalidUrl_returnNull(String invalidLink) {
        //given

        //when
        var parsedUrl = gitHubParser.parse(invalidLink);

        //then
        assertNull(parsedUrl, "Invalid link was parsed");
    }
}
