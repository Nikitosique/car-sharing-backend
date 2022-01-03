package dev.andrylat.carsharing.services.validators;

import dev.andrylat.carsharing.exceptions.ObjectValidationException;
import dev.andrylat.carsharing.models.DiscountCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class DiscountCardValidator implements ObjectValidator<DiscountCard> {
    private static final Logger LOGGER = LoggerFactory.getLogger("validatorsLogger");

    @Override
    public void validate(DiscountCard discountCard) {
        if (discountCard == null) {
            throw new ObjectValidationException("Inserted/updated discount card is null");
        }

        checkConstraintsViolations(discountCard);

        LOGGER.debug("Discount card object was successfully validated");
    }

    private void checkConstraintsViolations(DiscountCard discountCard) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<DiscountCard>> constraintViolations = validator.validate(discountCard);

        if (!constraintViolations.isEmpty()) {
            StringBuilder validationMessages = new StringBuilder();

            for (ConstraintViolation<DiscountCard> constraintViolation : constraintViolations) {
                validationMessages.append(constraintViolation.getMessage());
                validationMessages.append(". ");
            }

            throw new ObjectValidationException("Inserted/updated discount card is invalid. "
                    + "Such constraints were violated: "
                    + validationMessages);
        }
    }

}
