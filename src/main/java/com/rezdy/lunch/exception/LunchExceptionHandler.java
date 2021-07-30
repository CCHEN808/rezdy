package com.rezdy.lunch.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeParseException;

@RestControllerAdvice
public class LunchExceptionHandler {

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(DateTimeParseException exception) {
        return new ResponseEntity<>(new ErrorResponse().setErrorMessage(exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //catch and return 404 when Recipe Not Found
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ResponseStatusException exception) {
        return new ResponseEntity<>(new ErrorResponse().setErrorMessage(exception.getMessage()), HttpStatus.NOT_FOUND);
    }
    //any other exception catch here display message 'An Unexpected Error Occurred' shouldn't show client what's really happen
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleValidationException(Exception exception) {
        return new ResponseEntity<>(new ErrorResponse().setErrorMessage("An Unexpected Error Occurred"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
