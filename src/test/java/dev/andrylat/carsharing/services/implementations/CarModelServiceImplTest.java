package dev.andrylat.carsharing.services.implementations;

import dev.andrylat.carsharing.dao.CarModelDAO;
import dev.andrylat.carsharing.exceptions.QueryParametersMismatchException;
import dev.andrylat.carsharing.models.CarModel;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarModelServiceImplTest {

    @Mock
    private CarModelDAO carModelDAO;

    @InjectMocks
    private CarModelServiceImpl carModelServiceImpl;

    private CarModel carModel;
    private List<CarModel> carModels;

    @BeforeEach
    void setUp() {
        carModels = new ArrayList<>();

        CarModel temporal = new CarModel();
        temporal.setId(1);
        temporal.setBodyId(1);
        temporal.setBrandId(5);
        temporal.setName("Type-345");
        temporal.setEngineDisplacement(1.8);
        temporal.setFuelId(2);
        temporal.setGearboxType("manual");
        temporal.setProductionYear(2017);
        carModels.add(temporal);

        temporal = new CarModel();
        temporal.setId(2);
        temporal.setBodyId(2);
        temporal.setBrandId(3);
        temporal.setName("Model-CE1");
        temporal.setEngineDisplacement(2.0);
        temporal.setFuelId(1);
        temporal.setGearboxType("automatic");
        temporal.setProductionYear(2020);
        carModels.add(temporal);

        carModel = carModels.get(0);
    }

    @Test
    public void getRecordsNumber_ShouldReturnZero_WhenTableIsEmpty() {
        long expected = 0L;

        when(carModelDAO.getRecordsNumber()).thenReturn(0L);

        long actual = carModelServiceImpl.getRecordsNumber();
        assertEquals(expected, actual);

        verify(carModelDAO).getRecordsNumber();
    }

    @Test
    public void getRecordsNumber_ShouldReturnRecordsNumber_WhenTableIsNotEmpty() {
        long recordsNumber = carModels.size();
        long expected = 2L;

        when(carModelDAO.getRecordsNumber()).thenReturn(recordsNumber);

        long actual = carModelServiceImpl.getRecordsNumber();
        assertEquals(expected, actual);

        verify(carModelDAO).getRecordsNumber();
    }

    @Test
    public void getAll_ShouldThrownException_WhenMethodParametersAreInvalid() {
        int pageNumber = -1;
        int pageSize = -1;

        assertThrows(QueryParametersMismatchException.class, () -> carModelServiceImpl.getAll(pageNumber, pageSize));

        verify(carModelDAO, never()).getAll(anyInt(), anyInt());
    }

    @Test
    void getAll_ShouldReturnRecords_WhenMethodParametersAreValid() {
        int pageNumber = 0;
        int pageSize = 10;

        List<CarModel> expected = new ArrayList<>();
        CarModel temporal = new CarModel();
        temporal.setId(1L);
        temporal.setBodyId(1L);
        temporal.setBrandId(5L);
        temporal.setName("Type-345");
        temporal.setEngineDisplacement(1.8);
        temporal.setFuelId(2L);
        temporal.setGearboxType("manual");
        temporal.setProductionYear(2017);
        expected.add(temporal);

        temporal = new CarModel();
        temporal.setId(2L);
        temporal.setBodyId(2L);
        temporal.setBrandId(3L);
        temporal.setName("Model-CE1");
        temporal.setEngineDisplacement(2.0);
        temporal.setFuelId(1L);
        temporal.setGearboxType("automatic");
        temporal.setProductionYear(2020);
        expected.add(temporal);

        when(carModelDAO.getAll(anyInt(), anyInt())).thenReturn(carModels);

        List<CarModel> actual = carModelServiceImpl.getAll(pageNumber, pageSize);
        assertEquals(expected, actual);

        verify(carModelDAO).getAll(anyInt(), anyInt());
    }

    @Test
    void getById_ShouldThrownException_WhenMethodParameterIsInvalid() {
        long id = -1L;

        assertThrows(QueryParametersMismatchException.class, () -> carModelServiceImpl.getById(id));

        verify(carModelDAO, never()).getById(anyLong());
    }

    @Test
    void getById_ShouldReturnRecord_WhenMethodParameterIsValid() {
        long id = 1L;

        CarModel expected = new CarModel();
        expected.setId(1L);
        expected.setBodyId(1L);
        expected.setBrandId(5L);
        expected.setName("Type-345");
        expected.setEngineDisplacement(1.8);
        expected.setFuelId(2L);
        expected.setGearboxType("manual");
        expected.setProductionYear(2017);

        when(carModelDAO.getById(anyLong())).thenReturn(carModel);

        CarModel actual = carModelServiceImpl.getById(id);
        assertEquals(expected, actual);

        verify(carModelDAO).getById(anyLong());
    }

}