package dev.andrylat.carsharing.services.validators;

import dev.andrylat.carsharing.exceptions.ObjectValidationException;
import dev.andrylat.carsharing.models.CarBrand;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class CarBrandValidator implements ObjectValidator<CarBrand> {
    @Override
    public void validate(CarBrand carBrand) {
        if (carBrand == null) {
            throw new ObjectValidationException("Inserted/updated car brand is null");
        }

        checkConstraintsViolations(carBrand);
    }

    private void checkConstraintsViolations(CarBrand carBrand) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<CarBrand>> constraintViolations = validator.validate(carBrand);

        if (!constraintViolations.isEmpty()) {
            StringBuilder validationMessages = new StringBuilder();

            for (ConstraintViolation<CarBrand> constraintViolation : constraintViolations) {
                validationMessages.append(constraintViolation.getMessage());
                validationMessages.append(". ");
            }

            throw new ObjectValidationException("Inserted/updated car brand is invalid. "
                    + "Such constraints were violated: "
                    + validationMessages);
        }
    }

}
