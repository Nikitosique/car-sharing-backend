package dev.andrylat.carsharing.services.validators;

import dev.andrylat.carsharing.exceptions.ObjectValidationException;
import dev.andrylat.carsharing.models.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class UserValidator implements ObjectValidator<User> {

    @Override
    public void validate(User user) {
        if (user == null) {
            throw new ObjectValidationException("Inserted/updated user is null");
        }

        checkUserCardCompatibility(user);
        checkConstraintsViolations(user);
    }

    private void checkUserCardCompatibility(User user) {
        Long discountCardId = user.getDiscountCardId();

        if ("manager".equals(user.getType()) && discountCardId != -1) {
            throw new ObjectValidationException("Inserted/updated user is invalid. "
                    + "Manager shouldn't have a discount card");
        }

        if ("customer".equals(user.getType()) && discountCardId == 0) {
            throw new ObjectValidationException("Inserted/updated user is invalid. "
                    + "Customer should have a discount card");
        }
    }

    private void checkConstraintsViolations(User user) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        if (!constraintViolations.isEmpty()) {
            StringBuilder validationMessages = new StringBuilder();

            for (ConstraintViolation<User> constraintViolation : constraintViolations) {
                validationMessages.append(constraintViolation.getMessage());
                validationMessages.append(". ");
            }

            throw new ObjectValidationException("Inserted/updated user is invalid. "
                    + "Such constraints were violated: "
                    + validationMessages);
        }
    }

}
