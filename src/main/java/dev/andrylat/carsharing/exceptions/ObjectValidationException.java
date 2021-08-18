package dev.andrylat.carsharing.exceptions;

public class ObjectValidationException extends RuntimeException {
    public ObjectValidationException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
