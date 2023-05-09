package ru.tinkoff.edu.java.parser;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.tinkoff.edu.java.parser.parseresult.ParseResult;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ParserTest {

    List<ParserInterface> parsers = List.of(new GitHubParser(), new StackOverflowParser());
    Parser parser = new Parser(parsers);

    private static Stream<Arguments> getValidLinksUsersAndRepos() {
        return Stream.of(
            Arguments.of("https://github.com/melmelnikoff/Tink_Project", "melmelnikoff", "Tink_project"),
            Arguments.of("https://github.com/sanyarnd/servers/tree/master/handystuff", "sanyarnd", "servers"),
            Arguments.of("https://github.com/pengrad/java-telegram-bot-api/tree/", "pengrad", "java-telegram-bot-api")
        );
    }

    private static Stream<Arguments> getValidLinksAndQuestionID() {
        return Stream.of(
            Arguments.of("https://stackoverflow.com/questions/65066846/dockerfile-copy-zip-and-open-it", "65066846"),
            Arguments.of("https://stackoverflow.com/questions/1200621/how-do-i", "1200621"),
            Arguments.of("https://stackoverflow.com/questions/75868186/pandas", "75868186")
        );
    }

    private static Stream<Arguments> getInvalidLinks() {
        return Stream.of(
            "https://stackoverflow.com/questions",
            "https://stackoverflow.com/questions/ 1200621/how-do-iM",
            "https://stackoverflow.com/questions/create-map-in-java",
            "",
            "https://github.com/",
            "https://github.com/melmelnikoff",
            "https://github.com/melmelnikoff/ Tink_Project"
        ).map(Arguments::of);
    }

    @ParameterizedTest
    @MethodSource("getValidLinksUsersAndRepos")
    void parse__parseValidUrl_returnStringUserAndStringRepo(String validLink, String user, String repo) {
        //given

        //when
        var parsedUrl = (ParseResult.GitHubUserRepository) parser.parse(validLink);

        //then
        assertAll(
            () -> assertEquals(user, parsedUrl.userName(), ""),
            () -> assertEquals(repo, parsedUrl.repository())
        );
    }

    @ParameterizedTest
    @MethodSource("getValidLinksAndQuestionID")
    void parse__parseValidUrl_returnStringQuestionId(String validLink, String questionId) {
        //given

        //when
        var parsedUrl = (ParseResult.StackOverflowQuestionId) parser.parse(validLink);

        //then
        assertEquals(questionId, parsedUrl.questionId());
    }

    @ParameterizedTest
    @MethodSource("getInvalidLinks")
    void parse__parseInvalidLink_returnNull(String invalidLink) {
        //given
        //when(parserInterface.parse(invalidLink)).thenReturn(null);

        //when
        var parsedLink = parser.parse(invalidLink);

        //then
        assertNull(parsedLink);

    }
}
