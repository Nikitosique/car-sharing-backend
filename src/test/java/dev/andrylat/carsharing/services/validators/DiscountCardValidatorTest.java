package dev.andrylat.carsharing.services.validators;

import dev.andrylat.carsharing.exceptions.ObjectValidationException;
import dev.andrylat.carsharing.models.DiscountCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiscountCardValidatorTest {
    ObjectValidator<DiscountCard> discountCardValidator;

    @BeforeEach
    void setUp() {
        discountCardValidator = new DiscountCardValidator();
    }

    @Test
    public void validate_ShouldThrowException_WhenValidatedObjectIsNull() {
        DiscountCard validatedDiscountCard = null;

        assertThrows(ObjectValidationException.class, () -> discountCardValidator.validate(validatedDiscountCard));
    }

    @Test
    public void validate_ShouldThrowException_WhenValidatedObjectIsEmpty() {
        DiscountCard validatedDiscountCard = new DiscountCard();

        assertThrows(ObjectValidationException.class, () -> discountCardValidator.validate(validatedDiscountCard));
    }

    @Test
    public void validate_ShouldThrowException_WhenValidatedObjectIsInvalid() {
        DiscountCard validatedDiscountCard = new DiscountCard();
        validatedDiscountCard.setId(0);
        validatedDiscountCard.setDiscountValue(-1);
        validatedDiscountCard.setCardNumber("");

        assertThrows(ObjectValidationException.class, () -> discountCardValidator.validate(validatedDiscountCard));
    }

    @Test
    public void validate_ShouldNotThrowException_WhenValidatedObjectIsValid() {
        DiscountCard validatedDiscountCard = new DiscountCard();
        validatedDiscountCard.setId(1);
        validatedDiscountCard.setDiscountValue(1);
        validatedDiscountCard.setCardNumber("12rDdf43ghye4");

        assertDoesNotThrow(() -> discountCardValidator.validate(validatedDiscountCard));
    }

}