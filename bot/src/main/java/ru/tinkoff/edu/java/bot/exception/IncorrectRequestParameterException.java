package ru.tinkoff.edu.java.bot.exception;

public class IncorrectRequestParameterException extends RuntimeException{

    public IncorrectRequestParameterException(String message) {
        super(message);
    }
}
