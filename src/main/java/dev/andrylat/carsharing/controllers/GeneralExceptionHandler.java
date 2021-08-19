package dev.andrylat.carsharing.controllers;

import dev.andrylat.carsharing.exceptions.AssignmentException;
import dev.andrylat.carsharing.exceptions.ObjectValidationException;
import dev.andrylat.carsharing.exceptions.QueryParametersMismatchException;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GeneralExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger("errorsLogger");

    @ExceptionHandler(QueryParametersMismatchException.class)
    ModelAndView handleQueryParametersMismatch(QueryParametersMismatchException exception) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("exceptionMessage", exception.getMessage());
        modelAndView.setViewName("errors/queryParametersException");

        LOGGER.error("Error caused by invalid query parameters. ", exception);

        return modelAndView;
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    ModelAndView handleRecordNotFound(EmptyResultDataAccessException exception) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("exceptionMessage", exception.getMessage());
        modelAndView.setViewName("errors/recordNotFoundException");

        LOGGER.error("Error while getting record from table. ", exception);

        return modelAndView;
    }

    @ExceptionHandler(PSQLException.class)
    ModelAndView handlePsqlException(PSQLException exception) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("exceptionMessage", exception.getMessage());
        modelAndView.setViewName("errors/psqlException");

        LOGGER.error("Error while executing database query. ", exception);

        return modelAndView;
    }

    @ExceptionHandler(ObjectValidationException.class)
    ModelAndView handleObjectValidationException(ObjectValidationException exception) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("exceptionMessage", exception.getMessage());
        modelAndView.setViewName("errors/objectValidationException");

        LOGGER.error("Error while validating objects for inserting/updating into database. ", exception);

        return modelAndView;
    }

    @ExceptionHandler(AssignmentException.class)
    ModelAndView handleAssignmentException(AssignmentException exception) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("exceptionMessage", exception.getMessage());
        modelAndView.setViewName("errors/objectValidationException");

        LOGGER.error("Error while assigning/unassigning customer to/from manager. ", exception);

        return modelAndView;
    }

}
