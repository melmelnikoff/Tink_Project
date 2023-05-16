package ru.tinkoff.edu.java.bot.controller;

import java.util.Arrays;
import org.jetbrains.annotations.NotNull;
import org.openapitools.model.ApiErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.tinkoff.edu.java.bot.exception.IncorrectRequestParameterException;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends
    ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
        HttpMessageNotReadableException ex,
        @NotNull HttpHeaders headers,
        @NotNull HttpStatusCode status,
        @NotNull WebRequest request
    ) {
        ApiErrorResponse response = new ApiErrorResponse();
        response.setDescription("Incorrect JSON");
        response.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        response.setExceptionName(ex.getClass().getName());
        response.setExceptionMessage(ex.getMessage());
        response.setStacktrace(Arrays.stream(ex.getStackTrace()).map(StackTraceElement::toString).toList());
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler({IncorrectRequestParameterException.class})
    public ResponseEntity<Object> handleIncorrectRequestParameterException(IncorrectRequestParameterException ex) {
        ApiErrorResponse response = new ApiErrorResponse();
        response.setDescription("Incorrect request parameter");
        response.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        response.setExceptionName(ex.getClass().getName());
        response.setExceptionMessage(ex.getMessage());
        response.setStacktrace(Arrays.stream(ex.getStackTrace()).map(StackTraceElement::toString).toList());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
