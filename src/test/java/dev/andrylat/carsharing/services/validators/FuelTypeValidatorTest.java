package dev.andrylat.carsharing.services.validators;

import dev.andrylat.carsharing.exceptions.ObjectValidationException;
import dev.andrylat.carsharing.models.FuelType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FuelTypeValidatorTest {
    ObjectValidator<FuelType> fuelTypeValidator;

    @BeforeEach
    void setUp() {
        fuelTypeValidator = new FuelTypeValidator();
    }

    @Test
    public void validate_ShouldThrowException_WhenValidatedObjectIsNull() {
        FuelType validatedFuelType = null;

        assertThrows(ObjectValidationException.class, () -> fuelTypeValidator.validate(validatedFuelType));
    }

    @Test
    public void validate_ShouldThrowException_WhenValidatedObjectIsEmpty() {
        FuelType validatedFuelType = new FuelType();

        assertThrows(ObjectValidationException.class, () -> fuelTypeValidator.validate(validatedFuelType));
    }

    @Test
    public void validate_ShouldThrowException_WhenValidatedObjectIsInvalid() {
        FuelType validatedFuelType = new FuelType(1, null);

        assertThrows(ObjectValidationException.class, () -> fuelTypeValidator.validate(validatedFuelType));
    }

    @Test
    public void validate_ShouldNotThrowException_WhenValidatedObjectIsValid() {
        FuelType validatedFuelType = new FuelType(1, "gasoline");

        assertDoesNotThrow(() -> fuelTypeValidator.validate(validatedFuelType));
    }

}