package ru.tinkoff.edu.java.parser.parseresult;

public sealed interface ParseResult {
    record GitHubUserRepository(String userName, String repository) implements ParseResult {}
    record StackOverflowQuestionId(String questionId) implements ParseResult {}
}
