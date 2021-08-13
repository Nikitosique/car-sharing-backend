package dev.andrylat.carsharing.services.validators;

import dev.andrylat.carsharing.exceptions.ObjectValidationException;
import dev.andrylat.carsharing.models.CarModel;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class CarModelValidator implements ObjectValidator<CarModel> {
    @Override
    public void validate(CarModel carModel) {
        if (carModel == null) {
            throw new ObjectValidationException("Inserted/updated car model is null");
        }

        checkConstraintsViolations(carModel);
    }

    private void checkConstraintsViolations(CarModel carModel) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<CarModel>> constraintViolations = validator.validate(carModel);

        if (!constraintViolations.isEmpty()) {
            StringBuilder validationMessages = new StringBuilder();

            for (ConstraintViolation<CarModel> constraintViolation : constraintViolations) {
                validationMessages.append(constraintViolation.getMessage());
                validationMessages.append(". ");
            }

            throw new ObjectValidationException("Inserted/updated car model is invalid. "
                    + "Such constraints were violated: "
                    + validationMessages);
        }
    }

}
