package dev.andrylat.carsharing.exceptions;

public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
