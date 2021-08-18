package dev.andrylat.carsharing.services.validators;

public interface ObjectValidator<T> {
    public void validate(T objectToValidate);
}
