package dev.andrylat.carsharing.services.implementations;

import dev.andrylat.carsharing.dao.FuelTypeDAO;
import dev.andrylat.carsharing.exceptions.ObjectValidationException;
import dev.andrylat.carsharing.exceptions.QueryParametersMismatchException;
import dev.andrylat.carsharing.models.FuelType;
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
class FuelTypeServiceImplTest {

    @Mock
    private FuelTypeDAO fuelTypeDAO;

    @InjectMocks
    private FuelTypeServiceImpl fuelTypeServiceImpl;

    private FuelType fuelType;
    private List<FuelType> fuelTypes;

    @BeforeEach
    void setUp() {
        fuelTypes = new ArrayList<>();
        fuelTypes.add(new FuelType(1L, "gasoline"));
        fuelTypes.add(new FuelType(2L, "electricity"));

        fuelType = fuelTypes.get(0);
    }

    @Test
    public void getRecordsNumber_ShouldReturnZero_WhenTableIsEmpty() {
        long expected = 0;

        when(fuelTypeDAO.getRecordsNumber()).thenReturn(0L);

        long actual = fuelTypeServiceImpl.getRecordsNumber();
        assertEquals(expected, actual);

        verify(fuelTypeDAO).getRecordsNumber();
    }

    @Test
    public void getRecordsNumber_ShouldReturnRecordsNumber_WhenTableIsNotEmpty() {
        long recordsNumber = fuelTypes.size();
        long expected = 2L;

        when(fuelTypeDAO.getRecordsNumber()).thenReturn(recordsNumber);

        long actual = fuelTypeServiceImpl.getRecordsNumber();
        assertEquals(expected, actual);

        verify(fuelTypeDAO).getRecordsNumber();
    }

    @Test
    public void getAll_ShouldThrownException_WhenMethodParametersAreInvalid() {
        int pageNumber = -1;
        int pageSize = -1;

        assertThrows(QueryParametersMismatchException.class, () -> fuelTypeServiceImpl.getAll(pageNumber, pageSize));

        verify(fuelTypeDAO, never()).getAll(anyInt(), anyInt());
    }

    @Test
    void getAll_ShouldReturnRecords_WhenMethodParametersAreValid() {
        int pageNumber = 0;
        int pageSize = 10;

        List<FuelType> expected = new ArrayList<>();
        expected.add(new FuelType(1L, "gasoline"));
        expected.add(new FuelType(2L, "electricity"));

        when(fuelTypeDAO.getAll(anyInt(), anyInt())).thenReturn(fuelTypes);

        List<FuelType> actual = fuelTypeServiceImpl.getAll(pageNumber, pageSize);
        assertEquals(expected, actual);

        verify(fuelTypeDAO).getAll(anyInt(), anyInt());
    }

    @Test
    void getById_ShouldThrownException_WhenMethodParameterIsInvalid() {
        long id = -1L;

        assertThrows(QueryParametersMismatchException.class, () -> fuelTypeServiceImpl.getById(id));

        verify(fuelTypeDAO, never()).getById(anyLong());
    }

    @Test
    void getById_ShouldReturnRecord_WhenMethodParameterIsValid() {
        long id = 1L;
        FuelType expected = new FuelType(1, "gasoline");

        when(fuelTypeDAO.getById(anyLong())).thenReturn(fuelType);

        FuelType actual = fuelTypeServiceImpl.getById(id);
        assertEquals(expected, actual);

        verify(fuelTypeDAO).getById(anyLong());
    }

    @Test
    public void add_ShouldThrowException_WhenAddedObjectIsNull() {
        FuelType objectToAdd = null;

        assertThrows(ObjectValidationException.class, () -> fuelTypeServiceImpl.add(objectToAdd));

        verify(fuelTypeDAO, never()).add(any());
    }

    @Test
    public void add_ShouldThrowException_WhenAddedObjectIsInvalid() {
        FuelType objectToAdd = new FuelType();
        objectToAdd.setId(1L);
        objectToAdd.setName("");

        assertThrows(ObjectValidationException.class, () -> fuelTypeServiceImpl.add(objectToAdd));

        verify(fuelTypeDAO, never()).add(any());
    }

    @Test
    public void add_ShouldAddObject_WhenAddedObjectIsValid() {
        FuelType objectToAdd = new FuelType();
        objectToAdd.setName("gasoline");

        FuelType expected = new FuelType(1L, "gasoline");

        when(fuelTypeDAO.add(any())).thenReturn(fuelType);

        FuelType actual = fuelTypeServiceImpl.add(objectToAdd);
        assertEquals(expected, actual);

        verify(fuelTypeDAO).add(any());
    }

    @Test
    public void updateById_ShouldThrowException_WhenUpdatedObjectIsNull() {
        FuelType objectToUpdate = null;

        assertThrows(ObjectValidationException.class, () -> fuelTypeServiceImpl.updateById(objectToUpdate));

        verify(fuelTypeDAO, never()).updateById(any());
    }

    @Test
    public void updateById_ShouldThrowException_WhenUpdatedObjectIsInvalid() {
        FuelType objectToUpdate = new FuelType();
        objectToUpdate.setId(1L);
        objectToUpdate.setName("");

        assertThrows(ObjectValidationException.class, () -> fuelTypeServiceImpl.updateById(objectToUpdate));

        verify(fuelTypeDAO, never()).updateById(any());
    }

    @Test
    public void updateById_ShouldUpdateObject_WhenUpdatedObjectIsValid() {
        FuelType objectToUpdate = new FuelType(1L, "gasoline");

        boolean expected = true;

        when(fuelTypeDAO.updateById(any())).thenReturn(true);

        boolean actual = fuelTypeServiceImpl.updateById(objectToUpdate);
        assertEquals(expected, actual);

        verify(fuelTypeDAO).updateById(any());
    }

    @Test
    public void deleteById_ShouldThrowException_WhenMethodParameterIsInvalid() {
        long id = -1L;

        assertThrows(QueryParametersMismatchException.class, () -> fuelTypeServiceImpl.deleteById(id));

        verify(fuelTypeDAO, never()).deleteById(anyLong());
    }

    @Test
    public void deleteById_DeleteRecord_WhenMethodParameterIsValid() {
        long id = 1L;
        boolean expected = true;

        when(fuelTypeDAO.deleteById(anyLong())).thenReturn(true);

        boolean actual = fuelTypeServiceImpl.deleteById(id);
        assertEquals(expected, actual);

        verify(fuelTypeDAO).deleteById(anyLong());
    }

}