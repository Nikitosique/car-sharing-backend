package dev.andrylat.carsharing.controllers;

import dev.andrylat.carsharing.exceptions.QueryParametersMismatchException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GeneralExceptionHandler {
    @ExceptionHandler(QueryParametersMismatchException.class)
    String handleQueryParametersMismatch() {
        return "errors/queryParametersError";
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    String handleRecordNotFound() {
        return "errors/recordNotFoundError";
    }

}
