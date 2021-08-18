package dev.andrylat.carsharing.services.validators;

import dev.andrylat.carsharing.exceptions.ObjectValidationException;
import dev.andrylat.carsharing.models.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarValidatorTest {
    ObjectValidator<Car> carValidator;

    @BeforeEach
    void setUp() {
        carValidator = new CarValidator();
    }

    @Test
    public void validate_ShouldThrowException_WhenValidatedObjectIsNull() {
        Car validatedCar = null;

        assertThrows(ObjectValidationException.class, () -> carValidator.validate(validatedCar));
    }

    @Test
    public void validate_ShouldThrowException_WhenValidatedObjectIsEmpty() {
        Car validatedCar = new Car();

        assertThrows(ObjectValidationException.class, () -> carValidator.validate(validatedCar));
    }

    @Test
    public void validate_ShouldThrowException_WhenValidatedObjectIsInvalid() {
        Car validatedCar = new Car();
        validatedCar.setId(-1L);
        validatedCar.setModelId(-1L);
        validatedCar.setRegistrationPlate("");
        validatedCar.setRentCostPerMin(-1);
        validatedCar.setColor("");
        validatedCar.setPhoto("");

        assertThrows(ObjectValidationException.class, () -> carValidator.validate(validatedCar));
    }

    @Test
    public void validate_ShouldNotThrowException_WhenValidatedObjectIsValid() {
        Car validatedCar = new Car();
        validatedCar.setId(1L);
        validatedCar.setModelId(7L);
        validatedCar.setRegistrationPlate("AA1234BB");
        validatedCar.setRentCostPerMin(10);
        validatedCar.setColor("red");
        validatedCar.setPhoto("car_1.png");

        assertDoesNotThrow(() -> carValidator.validate(validatedCar));
    }

}