package dev.andrylat.carsharing.services.implementations;

import dev.andrylat.carsharing.dao.CarBrandDAO;
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
        long expected = 0;

        when(carBrandDAO.getRecordsNumber()).thenReturn(0L);

        long actual = carBrandServiceImpl.getRecordsNumber();
        assertEquals(expected, actual);

        verify(carBrandDAO).getRecordsNumber();
    }

    @Test
    public void getRecordsNumber_ShouldReturnRecordsNumber_WhenTableIsNotEmpty() {
        long recordsNumber = carBrands.size();
        long expected = 2;

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
        long id = -1;

        assertThrows(QueryParametersMismatchException.class, () -> carBrandServiceImpl.getById(id));

        verify(carBrandDAO, never()).getById(anyLong());
    }

    @Test
    void getById_ShouldReturnRecord_WhenMethodParameterIsValid() {
        long id = 1L;
        CarBrand expected = new CarBrand(1, "nissan");

        when(carBrandDAO.getById(anyLong())).thenReturn(carBrand);

        CarBrand actual = carBrandServiceImpl.getById(id);
        assertEquals(expected, actual);

        verify(carBrandDAO).getById(anyLong());
    }

}