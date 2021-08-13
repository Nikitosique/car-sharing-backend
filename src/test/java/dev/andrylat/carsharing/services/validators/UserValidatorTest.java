package dev.andrylat.carsharing.services.validators;

import dev.andrylat.carsharing.exceptions.ObjectValidationException;
import dev.andrylat.carsharing.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserValidatorTest {
    ObjectValidator<User> userValidator;

    @BeforeEach
    void setUp() {
        userValidator = new UserValidator();
    }

    @Test
    public void validate_ShouldThrowException_WhenValidatedObjectIsNull() {
        User validatedUser = null;

        assertThrows(ObjectValidationException.class, () -> userValidator.validate(validatedUser));
    }

    @Test
    public void validate_ShouldThrowException_WhenValidatedObjectIsEmpty() {
        User validatedUser = new User();

        assertThrows(ObjectValidationException.class, () -> userValidator.validate(validatedUser));
    }

    @Test
    public void validate_ShouldThrowException_WhenValidatedObjectIsInvalid() {
        User validatedUser = new User();
        validatedUser.setId(-1L);
        validatedUser.setEmail("");
        validatedUser.setPassword("");
        validatedUser.setDiscountCardId(1L);
        validatedUser.setType("");

        assertThrows(ObjectValidationException.class, () -> userValidator.validate(validatedUser));
    }

    @Test
    public void validate_ShouldNotThrowException_WhenValidatedObjectIsValid() {
        User validatedUser = new User();
        validatedUser.setId(1);
        validatedUser.setEmail("user1@gmail.com");
        validatedUser.setPassword("3NreW8R");
        validatedUser.setDiscountCardId(1L);
        validatedUser.setType("customer");

        assertDoesNotThrow(() -> userValidator.validate(validatedUser));
    }

}