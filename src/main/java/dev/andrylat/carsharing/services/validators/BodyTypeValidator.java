package dev.andrylat.carsharing.services.validators;

import dev.andrylat.carsharing.exceptions.ObjectValidationException;
import dev.andrylat.carsharing.models.BodyType;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class BodyTypeValidator implements ObjectValidator<BodyType> {
    @Override
    public void validate(BodyType bodyType) {
        if (bodyType == null) {
            throw new ObjectValidationException("Inserted/updated body type is null");
        }

        checkConstraintsViolations(bodyType);
    }

    private void checkConstraintsViolations(BodyType bodyType) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<BodyType>> constraintViolations = validator.validate(bodyType);

        if (!constraintViolations.isEmpty()) {
            StringBuilder validationMessages = new StringBuilder();

            for (ConstraintViolation<BodyType> constraintViolation : constraintViolations) {
                validationMessages.append(constraintViolation.getMessage());
                validationMessages.append(". ");
            }

            throw new ObjectValidationException("Inserted/updated body type is invalid. "
                    + "Such constraints were violated: "
                    + validationMessages);
        }
    }

}
