package dev.andrylat.carsharing.services.validators;

import dev.andrylat.carsharing.exceptions.ObjectValidationException;
import dev.andrylat.carsharing.models.CarBrand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarBrandValidatorTest {
    private ObjectValidator<CarBrand> carBrandValidator;

    @BeforeEach
    void setUp() {
        carBrandValidator = new CarBrandValidator();
    }

    @Test
    public void validate_ShouldThrowException_WhenValidatedObjectIsNull() {
        CarBrand validatedCarBrand = null;

        assertThrows(ObjectValidationException.class, () -> carBrandValidator.validate(validatedCarBrand));
    }

    @Test
    public void validate_ShouldThrowException_WhenValidatedObjectIsEmpty() {
        CarBrand validatedCarBrand = new CarBrand();

        assertThrows(ObjectValidationException.class, () -> carBrandValidator.validate(validatedCarBrand));
    }

    @Test
    public void validate_ShouldThrowException_WhenValidatedObjectIsInvalid() {
        CarBrand validatedCarBrand = new CarBrand(1, null);

        assertThrows(ObjectValidationException.class, () -> carBrandValidator.validate(validatedCarBrand));
    }

    @Test
    public void validate_ShouldNotThrowException_WhenValidatedObjectIsValid() {
        CarBrand validatedCarBrand = new CarBrand(1, "audi");

        assertDoesNotThrow(() -> carBrandValidator.validate(validatedCarBrand));
    }

}