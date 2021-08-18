package dev.andrylat.carsharing.services.validators;

import dev.andrylat.carsharing.exceptions.ObjectValidationException;
import dev.andrylat.carsharing.models.RentSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.postgresql.util.PGInterval;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class RentSessionValidatorTest {
    private ObjectValidator<RentSession> rentSessionValidator;

    @BeforeEach
    void setUp() {
        rentSessionValidator = new RentSessionValidator();
    }

    @Test
    public void validate_ShouldThrowException_WhenValidatedObjectIsNull() {
        RentSession validatedRentSession = null;

        assertThrows(ObjectValidationException.class, () -> rentSessionValidator.validate(validatedRentSession));
    }

    @Test
    public void validate_ShouldThrowException_WhenValidatedObjectIsEmpty() {
        RentSession validatedRentSession = new RentSession();

        assertThrows(ObjectValidationException.class, () -> rentSessionValidator.validate(validatedRentSession));
    }

    @Test
    public void validate_ShouldThrowException_WhenValidatedObjectIsInvalid() {
        RentSession validatedRentSession = new RentSession();
        validatedRentSession.setId(-1L);
        validatedRentSession.setCustomerId(-1L);
        validatedRentSession.setCarId(-1L);
        validatedRentSession.setRentSessionCost(-1);
        validatedRentSession.setRentTimeInterval(null);

        assertThrows(ObjectValidationException.class, () -> rentSessionValidator.validate(validatedRentSession));
    }

    @Test
    public void validate_ShouldNotThrowException_WhenValidatedObjectIsValid() throws SQLException {
        RentSession validatedRentSession = new RentSession();
        validatedRentSession.setId(1L);
        validatedRentSession.setCustomerId(1L);
        validatedRentSession.setCarId(1L);
        validatedRentSession.setRentSessionCost(1);
        validatedRentSession.setRentTimeInterval(new PGInterval("30 day 23 hour 59 minutes 59 second"));

        assertDoesNotThrow(() -> rentSessionValidator.validate(validatedRentSession));
    }

}