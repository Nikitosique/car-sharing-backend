package dev.andrylat.carsharing.services.implementations;

import dev.andrylat.carsharing.dao.CarModelDAO;
import dev.andrylat.carsharing.exceptions.ObjectValidationException;
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

    @Test
    public void add_ShouldThrowException_WhenAddedObjectIsNull() {
        CarModel objectToAdd = null;

        assertThrows(ObjectValidationException.class, () -> carModelServiceImpl.add(objectToAdd));

        verify(carModelDAO, never()).add(any());
    }

    @Test
    public void add_ShouldThrowException_WhenAddedObjectIsInvalid() {
        CarModel objectToAdd = new CarModel();
        objectToAdd.setId(-1L);
        objectToAdd.setBodyId(-1L);
        objectToAdd.setBrandId(-1L);
        objectToAdd.setFuelId(-1L);
        objectToAdd.setEngineDisplacement(-2.3);
        objectToAdd.setProductionYear(-1);
        objectToAdd.setName("");
        objectToAdd.setGearboxType("");

        assertThrows(ObjectValidationException.class, () -> carModelServiceImpl.add(objectToAdd));

        verify(carModelDAO, never()).add(any());
    }

    @Test
    public void add_ShouldAddObject_WhenAddedObjectIsValid() {
        CarModel objectToAdd = new CarModel();
        objectToAdd.setId(1);
        objectToAdd.setBodyId(1);
        objectToAdd.setBrandId(5);
        objectToAdd.setName("Type-345");
        objectToAdd.setEngineDisplacement(1.8);
        objectToAdd.setFuelId(2);
        objectToAdd.setGearboxType("manual");
        objectToAdd.setProductionYear(2017);

        CarModel expected = new CarModel();
        expected.setId(1);
        expected.setBodyId(1);
        expected.setBrandId(5);
        expected.setName("Type-345");
        expected.setEngineDisplacement(1.8);
        expected.setFuelId(2);
        expected.setGearboxType("manual");
        expected.setProductionYear(2017);

        when(carModelDAO.add(any())).thenReturn(carModel);

        CarModel actual = carModelServiceImpl.add(objectToAdd);
        assertEquals(expected, actual);

        verify(carModelDAO).add(any());
    }

    @Test
    public void updateById_ShouldThrowException_WhenUpdatedObjectIsNull() {
        CarModel objectToUpdate = null;

        assertThrows(ObjectValidationException.class, () -> carModelServiceImpl.updateById(objectToUpdate));

        verify(carModelDAO, never()).updateById(any());
    }

    @Test
    public void updateById_ShouldThrowException_WhenUpdatedObjectIsInvalid() {
        CarModel objectToUpdate = new CarModel();
        objectToUpdate.setId(-1L);
        objectToUpdate.setBodyId(-1L);
        objectToUpdate.setBrandId(-1L);
        objectToUpdate.setFuelId(-1L);
        objectToUpdate.setEngineDisplacement(-2.3);
        objectToUpdate.setProductionYear(-1);
        objectToUpdate.setName("");
        objectToUpdate.setGearboxType("");

        assertThrows(ObjectValidationException.class, () -> carModelServiceImpl.updateById(objectToUpdate));

        verify(carModelDAO, never()).updateById(any());
    }

    @Test
    public void updateById_ShouldUpdateObject_WhenUpdatedObjectIsValid() {
        CarModel objectToUpdate = new CarModel();
        objectToUpdate.setId(1);
        objectToUpdate.setBodyId(1);
        objectToUpdate.setBrandId(5);
        objectToUpdate.setName("Type-345");
        objectToUpdate.setEngineDisplacement(1.8);
        objectToUpdate.setFuelId(2);
        objectToUpdate.setGearboxType("manual");
        objectToUpdate.setProductionYear(2017);

        boolean expected = true;

        when(carModelDAO.updateById(any())).thenReturn(true);

        boolean actual = carModelServiceImpl.updateById(objectToUpdate);
        assertEquals(expected, actual);

        verify(carModelDAO).updateById(any());
    }

    @Test
    public void deleteById_ShouldThrowException_WhenMethodParameterIsInvalid() {
        long id = -1L;

        assertThrows(QueryParametersMismatchException.class, () -> carModelServiceImpl.deleteById(id));

        verify(carModelDAO, never()).deleteById(anyLong());
    }

    @Test
    public void deleteById_DeleteRecord_WhenMethodParameterIsValid() {
        long id = 1L;
        boolean expected = true;

        when(carModelDAO.deleteById(anyLong())).thenReturn(true);

        boolean actual = carModelServiceImpl.deleteById(id);
        assertEquals(expected, actual);

        verify(carModelDAO).deleteById(anyLong());
    }

}