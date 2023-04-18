package ru.tinkoff.edu.java.scrapper.controller;


import org.openapitools.model.ApiErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.tinkoff.edu.java.scrapper.exception.DuplicateLinkException;
import ru.tinkoff.edu.java.scrapper.exception.IncorrectRequestParameterException;
import ru.tinkoff.edu.java.scrapper.exception.LinkParserException;
import ru.tinkoff.edu.java.scrapper.exception.ResourceNotFoundException;


@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends
        ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        ApiErrorResponse response = ApiErrorResponse.builder()
                .description("Incorrect request body")
                .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .exceptionName(ex.getClass().getName())
                .exceptionMessage(ex.getMessage())
                .stacktrace(ex.getStackTrace())
                .build();
        return new ResponseEntity<>(response, status);
    }


    @ExceptionHandler({MethodArgumentTypeMismatchException.class,})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        ApiErrorResponse response = ApiErrorResponse.builder()
                .description("Incorrect argument")
                .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .exceptionName(ex.getClass().getName())
                .exceptionMessage(ex.getMessage())
                .stacktrace(ex.getStackTrace())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({IncorrectRequestParameterException.class,})
    public ResponseEntity<Object> handleIncorrectRequestParameterException(IncorrectRequestParameterException ex) {
        ApiErrorResponse response = ApiErrorResponse.builder()
                .description("Incorrect request parameter")
                .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .exceptionName(ex.getClass().getName())
                .exceptionMessage(ex.getMessage())
                .stacktrace(ex.getStackTrace())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({ResourceNotFoundException.class,})
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ApiErrorResponse response = ApiErrorResponse.builder()
                .description("Not found")
                .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .exceptionName(ex.getClass().getName())
                .exceptionMessage(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DuplicateLinkException.class,})
    public ResponseEntity<Object> handleDuplicateLinkException(DuplicateLinkException ex) {
        ApiErrorResponse response = ApiErrorResponse.builder()
                .description("Duplicate links")
                .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .exceptionName(ex.getClass().getName())
                .exceptionMessage(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({LinkParserException.class,})
    public ResponseEntity<Object> handleLinkParserException(LinkParserException ex) {
        ApiErrorResponse response = ApiErrorResponse.builder()
                .description("Incorrect link")
                .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .exceptionName(ex.getClass().getName())
                .exceptionMessage(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}