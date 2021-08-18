package dev.andrylat.carsharing.services.validators;

import dev.andrylat.carsharing.exceptions.ObjectValidationException;
import dev.andrylat.carsharing.models.CarModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarModelValidatorTest {
    ObjectValidator<CarModel> carModelValidator;

    @BeforeEach
    void setUp() {
        carModelValidator = new CarModelValidator();
    }

    @Test
    public void validate_ShouldThrowException_WhenValidatedObjectIsNull() {
        CarModel validatedBodyType = null;

        assertThrows(ObjectValidationException.class, () -> carModelValidator.validate(validatedBodyType));
    }

    @Test
    public void validate_ShouldThrowException_WhenValidatedObjectIsEmpty() {
        CarModel validatedBodyType = new CarModel();

        assertThrows(ObjectValidationException.class, () -> carModelValidator.validate(validatedBodyType));
    }

    @Test
    public void validate_ShouldThrowException_WhenValidatedObjectIsInvalid() {
        CarModel validatedBodyType = new CarModel();
        validatedBodyType.setId(-1L);
        validatedBodyType.setBodyId(-1L);
        validatedBodyType.setBrandId(-1L);
        validatedBodyType.setFuelId(-1L);
        validatedBodyType.setEngineDisplacement(-2.3);
        validatedBodyType.setProductionYear(-1);
        validatedBodyType.setName("");
        validatedBodyType.setGearboxType("");

        assertThrows(ObjectValidationException.class, () -> carModelValidator.validate(validatedBodyType));
    }

    @Test
    public void validate_ShouldNotThrowException_WhenValidatedObjectIsValid() {
        CarModel validatedBodyType = new CarModel();
        validatedBodyType.setId(1L);
        validatedBodyType.setBodyId(2L);
        validatedBodyType.setBrandId(3L);
        validatedBodyType.setFuelId(4L);
        validatedBodyType.setEngineDisplacement(2.3);
        validatedBodyType.setProductionYear(2020);
        validatedBodyType.setName("Model T");
        validatedBodyType.setGearboxType("automatic");

        assertDoesNotThrow(() -> carModelValidator.validate(validatedBodyType));
    }

}