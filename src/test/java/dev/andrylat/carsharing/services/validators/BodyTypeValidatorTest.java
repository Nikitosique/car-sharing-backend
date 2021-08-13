package dev.andrylat.carsharing.services.validators;

import dev.andrylat.carsharing.exceptions.ObjectValidationException;
import dev.andrylat.carsharing.models.BodyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BodyTypeValidatorTest {
    ObjectValidator<BodyType> bodyTypeValidator;

    @BeforeEach
    void setUp() {
        bodyTypeValidator = new BodyTypeValidator();
    }

    @Test
    public void validate_ShouldThrowException_WhenValidatedObjectIsNull() {
        BodyType validatedBodyType = null;

        assertThrows(ObjectValidationException.class, () -> bodyTypeValidator.validate(validatedBodyType));
    }

    @Test
    public void validate_ShouldThrowException_WhenValidatedObjectIsEmpty() {
        BodyType validatedBodyType = new BodyType();

        assertThrows(ObjectValidationException.class, () -> bodyTypeValidator.validate(validatedBodyType));
    }

    @Test
    public void validate_ShouldThrowException_WhenValidatedObjectIsInvalid() {
        BodyType validatedBodyType = new BodyType(1, null);

        assertThrows(ObjectValidationException.class, () -> bodyTypeValidator.validate(validatedBodyType));
    }

    @Test
    public void validate_ShouldNotThrowException_WhenValidatedObjectIsValid() {
        BodyType validatedBodyType = new BodyType(1, "sedan");

        assertDoesNotThrow(() -> bodyTypeValidator.validate(validatedBodyType));
    }

}