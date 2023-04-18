package ru.tinkoff.edu.java.parser;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.tinkoff.edu.java.parser.parseresult.ParseResult;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class StackOverflowParserTest {

    private final StackOverflowParser stackOverflowParser = new StackOverflowParser();

    @ParameterizedTest
    @MethodSource("getValidLinksAndQuestionID")
    void parse__parseValidUrl_returnStringQuestionId(String validLink, String questionId){
        //given

        //when
        var parsedUrl = (ParseResult.StackOverflowQuestionId)stackOverflowParser.parse(validLink);

        //then
        assertEquals(questionId, parsedUrl.questionId());
    }

    @ParameterizedTest
    @MethodSource("getInvalidLinks")
    void parse__parseInvalidUrl_returnNull(String invalidLink){
        //given

        //when
        var parsedUrl = stackOverflowParser.parse(invalidLink);

        //then
        assertNull(parsedUrl, "Invalid link was parsed");
    }




    private static Stream<Arguments> getValidLinksAndQuestionID(){
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
                "https://github.com/TivM/ lab8"

        ).map(Arguments::of);
    }





}



