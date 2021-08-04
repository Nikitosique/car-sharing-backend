package dev.andrylat.carsharing.services.implementations;

import dev.andrylat.carsharing.dao.CarDAO;
import dev.andrylat.carsharing.exceptions.QueryParametersMismatchException;
import dev.andrylat.carsharing.models.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {
    @Mock
    private CarDAO carDAO;

    @InjectMocks
    private CarServiceImpl carServiceImpl;

    private Car car;
    private List<Car> cars;

    @BeforeEach
    void setUp() {
        cars = new ArrayList<>();

        Car temporal = new Car();
        temporal.setId(1L);
        temporal.setModelId(7L);
        temporal.setRegistrationPlate("AA1234BB");
        temporal.setRentCostPerMin(10);
        temporal.setColor("red");
        temporal.setPhoto("car_1.png");
        cars.add(temporal);

        temporal = new Car();
        temporal.setId(2L);
        temporal.setModelId(32L);
        temporal.setRegistrationPlate("QZ1WD403");
        temporal.setRentCostPerMin(20);
        temporal.setColor("white");
        temporal.setPhoto("car_2.png");
        cars.add(temporal);

        car = cars.get(0);
    }

    @Test
    public void getRecordsNumber_ShouldReturnZero_WhenTableIsEmpty() {
        long expected = 0L;

        when(carDAO.getRecordsNumber()).thenReturn(0L);

        long actual = carServiceImpl.getRecordsNumber();
        assertEquals(expected, actual);

        verify(carDAO).getRecordsNumber();
    }

    @Test
    public void getRecordsNumber_ShouldReturnRecordsNumber_WhenTableIsNotEmpty() {
        long recordsNumber = cars.size();
        long expected = 2L;

        when(carDAO.getRecordsNumber()).thenReturn(recordsNumber);

        long actual = carServiceImpl.getRecordsNumber();
        assertEquals(expected, actual);

        verify(carDAO).getRecordsNumber();
    }

    @Test
    public void getAll_ShouldThrownException_WhenMethodParametersAreInvalid() {
        int pageNumber = -1;
        int pageSize = -1;

        assertThrows(QueryParametersMismatchException.class, () -> carServiceImpl.getAll(pageNumber, pageSize));

        verify(carDAO, never()).getAll(anyInt(), anyInt());
    }

    @Test
    void getAll_ShouldReturnRecords_WhenMethodParametersAreValid() {
        int pageNumber = 0;
        int pageSize = 10;

        List<Car> expected = new ArrayList<>();
        Car temporal = new Car();
        temporal.setId(1L);
        temporal.setModelId(7L);
        temporal.setRegistrationPlate("AA1234BB");
        temporal.setRentCostPerMin(10);
        temporal.setColor("red");
        temporal.setPhoto("car_1.png");
        expected.add(temporal);

        temporal = new Car();
        temporal.setId(2L);
        temporal.setModelId(32L);
        temporal.setRegistrationPlate("QZ1WD403");
        temporal.setRentCostPerMin(20);
        temporal.setColor("white");
        temporal.setPhoto("car_2.png");
        expected.add(temporal);

        when(carDAO.getAll(anyInt(), anyInt())).thenReturn(cars);

        List<Car> actual = carServiceImpl.getAll(pageNumber, pageSize);
        assertEquals(expected, actual);

        verify(carDAO).getAll(anyInt(), anyInt());
    }

    @Test
    void getById_ShouldThrownException_WhenMethodParameterIsInvalid() {
        long id = -1L;

        assertThrows(QueryParametersMismatchException.class, () -> carServiceImpl.getById(id));

        verify(carDAO, never()).getById(anyLong());
    }

    @Test
    void getById_ShouldReturnRecord_WhenMethodParameterIsValid() {
        long id = 1L;

        Car expected = new Car();
        expected.setId(1L);
        expected.setModelId(7L);
        expected.setRegistrationPlate("AA1234BB");
        expected.setRentCostPerMin(10);
        expected.setColor("red");
        expected.setPhoto("car_1.png");

        when(carDAO.getById(anyLong())).thenReturn(car);

        Car actual = carServiceImpl.getById(id);
        assertEquals(expected, actual);

        verify(carDAO).getById(anyLong());
    }

}