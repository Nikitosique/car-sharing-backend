package dev.andrylat.carsharing.exceptions;

public class QueryParametersMismatchException extends RuntimeException {
    public QueryParametersMismatchException(String exceptionMessage) {
        super(exceptionMessage);
    }

}
