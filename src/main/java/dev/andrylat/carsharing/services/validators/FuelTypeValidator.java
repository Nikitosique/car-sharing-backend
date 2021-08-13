package dev.andrylat.carsharing.services.validators;

import dev.andrylat.carsharing.exceptions.ObjectValidationException;
import dev.andrylat.carsharing.models.FuelType;
import dev.andrylat.carsharing.models.FuelType;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class FuelTypeValidator implements ObjectValidator<FuelType> {
    @Override
    public void validate(FuelType fuelType) {
        if (fuelType == null) {
            throw new ObjectValidationException("Inserted/updated fuel type is null");
        }

        checkConstraintsViolations(fuelType);
    }

    private void checkConstraintsViolations(FuelType fuelType) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<FuelType>> constraintViolations = validator.validate(fuelType);

        if (!constraintViolations.isEmpty()) {
            StringBuilder validationMessages = new StringBuilder();

            for (ConstraintViolation<FuelType> constraintViolation : constraintViolations) {
                validationMessages.append(constraintViolation.getMessage());
                validationMessages.append(". ");
            }

            throw new ObjectValidationException("Inserted/updated fuel type is invalid. "
                    + "Such constraints were violated: "
                    + validationMessages);
        }
    }

}
