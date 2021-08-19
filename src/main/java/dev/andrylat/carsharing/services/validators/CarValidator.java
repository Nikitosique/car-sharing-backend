package dev.andrylat.carsharing.services.validators;

import dev.andrylat.carsharing.exceptions.ObjectValidationException;
import dev.andrylat.carsharing.models.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class CarValidator implements ObjectValidator<Car> {
    private static final Logger LOGGER = LoggerFactory.getLogger("validatorsLogger");

    @Override
    public void validate(Car car) {
        if (car == null) {
            throw new ObjectValidationException("Inserted/updated car is null");
        }

        checkConstraintsViolations(car);

        LOGGER.debug("Car object was successfully validated");
    }

    private void checkConstraintsViolations(Car car) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Car>> constraintViolations = validator.validate(car);

        if (!constraintViolations.isEmpty()) {
            StringBuilder validationMessages = new StringBuilder();

            for (ConstraintViolation<Car> constraintViolation : constraintViolations) {
                validationMessages.append(constraintViolation.getMessage());
                validationMessages.append(". ");
            }

            throw new ObjectValidationException("Inserted/updated car is invalid. "
                    + "Such constraints were violated: "
                    + validationMessages);
        }
    }

}
