package dev.andrylat.carsharing.controllers;

import dev.andrylat.carsharing.exceptions.AssignmentException;
import dev.andrylat.carsharing.exceptions.ObjectValidationException;
import dev.andrylat.carsharing.exceptions.QueryParametersMismatchException;
import org.postgresql.util.PSQLException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(QueryParametersMismatchException.class)
    ModelAndView handleQueryParametersMismatch(QueryParametersMismatchException exception) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("exceptionMessage", exception.getMessage());
        modelAndView.setViewName("errors/queryParametersException");

        return modelAndView;
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    ModelAndView handleRecordNotFound(EmptyResultDataAccessException exception) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("exceptionMessage", exception.getMessage());
        modelAndView.setViewName("errors/recordNotFoundException");

        return modelAndView;
    }

    @ExceptionHandler(PSQLException.class)
    ModelAndView handlePsqlException(PSQLException exception) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("exceptionMessage", exception.getMessage());
        modelAndView.setViewName("errors/psqlException");

        return modelAndView;
    }

    @ExceptionHandler(ObjectValidationException.class)
    ModelAndView handleObjectValidationException(ObjectValidationException exception) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("exceptionMessage", exception.getMessage());
        modelAndView.setViewName("errors/objectValidationException");

        return modelAndView;
    }

    @ExceptionHandler(AssignmentException.class)
    ModelAndView handleAssignmentException(AssignmentException exception) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("exceptionMessage", exception.getMessage());
        modelAndView.setViewName("errors/objectValidationException");

        return modelAndView;
    }

}
