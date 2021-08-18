package dev.andrylat.carsharing.services.implementations;

import dev.andrylat.carsharing.dao.CarBrandDAO;
import dev.andrylat.carsharing.exceptions.ObjectValidationException;
import dev.andrylat.carsharing.exceptions.QueryParametersMismatchException;
import dev.andrylat.carsharing.models.CarBrand;
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
class CarBrandServiceImplTest {
    @Mock
    private CarBrandDAO carBrandDAO;

    @InjectMocks
    private CarBrandServiceImpl carBrandServiceImpl;

    private CarBrand carBrand;
    private List<CarBrand> carBrands;

    @BeforeEach
    void setUp() {
        carBrands = new ArrayList<>();
        carBrands.add(new CarBrand(1, "nissan"));
        carBrands.add(new CarBrand(2, "audi"));

        carBrand = carBrands.get(0);
    }

    @Test
    public void getRecordsNumber_ShouldReturnZero_WhenTableIsEmpty() {
        long expected = 0L;

        when(carBrandDAO.getRecordsNumber()).thenReturn(0L);

        long actual = carBrandServiceImpl.getRecordsNumber();
        assertEquals(expected, actual);

        verify(carBrandDAO).getRecordsNumber();
    }

    @Test
    public void getRecordsNumber_ShouldReturnRecordsNumber_WhenTableIsNotEmpty() {
        long recordsNumber = carBrands.size();
        long expected = 2L;

        when(carBrandDAO.getRecordsNumber()).thenReturn(recordsNumber);

        long actual = carBrandServiceImpl.getRecordsNumber();
        assertEquals(expected, actual);

        verify(carBrandDAO).getRecordsNumber();
    }

    @Test
    public void getAll_ShouldThrownException_WhenMethodParametersAreInvalid() {
        int pageNumber = -1;
        int pageSize = -1;

        assertThrows(QueryParametersMismatchException.class, () -> carBrandServiceImpl.getAll(pageNumber, pageSize));

        verify(carBrandDAO, never()).getAll(anyInt(), anyInt());
    }

    @Test
    void getAll_ShouldReturnRecords_WhenMethodParametersAreValid() {
        int pageNumber = 0;
        int pageSize = 10;

        List<CarBrand> expected = new ArrayList<>();
        expected.add(new CarBrand(1, "nissan"));
        expected.add(new CarBrand(2, "audi"));

        when(carBrandDAO.getAll(anyInt(), anyInt())).thenReturn(carBrands);

        List<CarBrand> actual = carBrandServiceImpl.getAll(pageNumber, pageSize);
        assertEquals(expected, actual);

        verify(carBrandDAO).getAll(anyInt(), anyInt());
    }

    @Test
    void getById_ShouldThrownException_WhenMethodParameterIsInvalid() {
        long id = -1L;

        assertThrows(QueryParametersMismatchException.class, () -> carBrandServiceImpl.getById(id));

        verify(carBrandDAO, never()).getById(anyLong());
    }

    @Test
    void getById_ShouldReturnRecord_WhenMethodParameterIsValid() {
        CarBrand expected = new CarBrand(1, "nissan");
        long id = 1L;

        when(carBrandDAO.getById(anyLong())).thenReturn(carBrand);

        CarBrand actual = carBrandServiceImpl.getById(id);
        assertEquals(expected, actual);

        verify(carBrandDAO).getById(anyLong());
    }

    @Test
    public void add_ShouldThrowException_WhenAddedObjectIsNull() {
        CarBrand objectToAdd = null;

        assertThrows(ObjectValidationException.class, () -> carBrandServiceImpl.add(objectToAdd));

        verify(carBrandDAO, never()).add(any());
    }

    @Test
    public void add_ShouldThrowException_WhenAddedObjectIsInvalid() {
        CarBrand objectToAdd = new CarBrand(1L, "");

        assertThrows(ObjectValidationException.class, () -> carBrandServiceImpl.add(objectToAdd));

        verify(carBrandDAO, never()).add(any());
    }

    @Test
    public void add_ShouldAddObject_WhenAddedObjectIsValid() {
        CarBrand objectToAdd = new CarBrand();
        objectToAdd.setName("nissan");

        CarBrand expected = new CarBrand(1L, "nissan");

        when(carBrandDAO.add(any())).thenReturn(carBrand);

        CarBrand actual = carBrandServiceImpl.add(objectToAdd);
        assertEquals(expected, actual);

        verify(carBrandDAO).add(any());
    }

    @Test
    public void updateById_ShouldThrowException_WhenUpdatedObjectIsNull() {
        CarBrand objectToUpdate = null;

        assertThrows(ObjectValidationException.class, () -> carBrandServiceImpl.updateById(objectToUpdate));

        verify(carBrandDAO, never()).updateById(any());
    }

    @Test
    public void updateById_ShouldThrowException_WhenUpdatedObjectIsInvalid() {
        CarBrand objectToUpdate = new CarBrand(1L, "");

        assertThrows(ObjectValidationException.class, () -> carBrandServiceImpl.updateById(objectToUpdate));

        verify(carBrandDAO, never()).updateById(any());
    }

    @Test
    public void updateById_ShouldUpdateObject_WhenUpdatedObjectIsValid() {
        CarBrand objectToUpdate = new CarBrand(1L, "nissan");
        boolean expected = true;

        when(carBrandDAO.updateById(any())).thenReturn(true);

        boolean actual = carBrandServiceImpl.updateById(objectToUpdate);
        assertEquals(expected, actual);

        verify(carBrandDAO).updateById(any());
    }

    @Test
    public void deleteById_ShouldThrowException_WhenMethodParameterIsInvalid() {
        long id = -1L;

        assertThrows(QueryParametersMismatchException.class, () -> carBrandServiceImpl.deleteById(id));

        verify(carBrandDAO, never()).deleteById(anyLong());
    }

    @Test
    public void deleteById_DeleteRecord_WhenMethodParameterIsValid() {
        long id = 1L;
        boolean expected = true;

        when(carBrandDAO.deleteById(anyLong())).thenReturn(true);

        boolean actual = carBrandServiceImpl.deleteById(id);
        assertEquals(expected, actual);

        verify(carBrandDAO).deleteById(anyLong());
    }

}