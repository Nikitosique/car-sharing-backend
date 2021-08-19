package dev.andrylat.carsharing.services.validators;

import dev.andrylat.carsharing.exceptions.ObjectValidationException;
import dev.andrylat.carsharing.models.RentSession;
import org.postgresql.util.PGInterval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class RentSessionValidator implements ObjectValidator<RentSession> {
    private static final String INVALID_TIME_INTERVAL_VALUE = "0 years 0 mons 0 days 0 hours 0 mins 0.0 secs";
    private static final Logger LOGGER = LoggerFactory.getLogger("validatorsLogger");

    @Override
    public void validate(RentSession rentSession) {
        if (rentSession == null) {
            throw new ObjectValidationException("Inserted/updated rent session is null");
        }

        checkTimeInterval(rentSession);
        checkConstraintsViolations(rentSession);

        LOGGER.debug("Rent session object was successfully validated");
    }

    private void checkConstraintsViolations(RentSession rentSession) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<RentSession>> constraintViolations = validator.validate(rentSession);

        if (!constraintViolations.isEmpty()) {
            StringBuilder validationMessages = new StringBuilder();

            for (ConstraintViolation<RentSession> constraintViolation : constraintViolations) {
                validationMessages.append(constraintViolation.getMessage());
                validationMessages.append(". ");
            }

            throw new ObjectValidationException("Inserted/updated rent session is invalid. "
                    + "Such constraints were violated: "
                    + validationMessages);
        }
    }

    private void checkTimeInterval(RentSession rentSession) {
        PGInterval interval = rentSession.getRentTimeInterval();

        if (interval == null || INVALID_TIME_INTERVAL_VALUE.equals(interval.getValue())) {
            throw new ObjectValidationException("Inserted/updated rent session is invalid. "
                    + "Invalid rent time interval.");
        }
    }

}
