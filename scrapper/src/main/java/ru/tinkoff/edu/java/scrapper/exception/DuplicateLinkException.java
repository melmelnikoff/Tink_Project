package ru.tinkoff.edu.java.scrapper.exception;

public class DuplicateLinkException extends RuntimeException {
    public DuplicateLinkException(String message) {
        super(message);
    }
}
