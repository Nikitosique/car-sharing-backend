package dev.andrylat.carsharing.services.implementations;

import dev.andrylat.carsharing.dao.BodyTypeDAO;
import dev.andrylat.carsharing.exceptions.QueryParametersMismatchException;
import dev.andrylat.carsharing.models.BodyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BodyTypeServiceImplTest {

    @Mock
    private BodyTypeDAO bodyTypeDAO;

    @InjectMocks
    private BodyTypeServiceImpl bodyTypeServiceImpl;

    private BodyType bodyType;
    private List<BodyType> bodyTypes;

    @BeforeEach
    void setUp() {
        bodyTypes = new ArrayList<>();
        bodyTypes.add(new BodyType(1, "sedan"));
        bodyTypes.add(new BodyType(2, "SUV"));

        bodyType = bodyTypes.get(0);
    }

    @Test
    public void getRecordsNumber_ShouldReturnZero_WhenTableIsEmpty() {
        long expected = 0L;

        when(bodyTypeDAO.getRecordsNumber()).thenReturn(0L);

        long actual = bodyTypeServiceImpl.getRecordsNumber();
        assertEquals(expected, actual);

        verify(bodyTypeDAO).getRecordsNumber();
    }

    @Test
    public void getRecordsNumber_ShouldReturnRecordsNumber_WhenTableIsNotEmpty() {
        long recordsNumber = bodyTypes.size();
        long expected = 2L;

        when(bodyTypeDAO.getRecordsNumber()).thenReturn(recordsNumber);

        long actual = bodyTypeServiceImpl.getRecordsNumber();
        assertEquals(expected, actual);

        verify(bodyTypeDAO).getRecordsNumber();
    }

    @Test
    public void getAll_ShouldThrownException_WhenMethodParametersAreInvalid() {
        int pageNumber = -1;
        int pageSize = -1;

        assertThrows(QueryParametersMismatchException.class, () -> bodyTypeServiceImpl.getAll(pageNumber, pageSize));

        verify(bodyTypeDAO, never()).getAll(anyInt(), anyInt());
    }

    @Test
    void getAll_ShouldReturnRecords_WhenMethodParametersAreValid() {
        int pageNumber = 0;
        int pageSize = 10;

        List<BodyType> expected = new ArrayList<>();
        expected.add(new BodyType(1, "sedan"));
        expected.add(new BodyType(2, "SUV"));

        when(bodyTypeDAO.getAll(anyInt(), anyInt())).thenReturn(bodyTypes);

        List<BodyType> actual = bodyTypeServiceImpl.getAll(pageNumber, pageSize);
        assertEquals(expected, actual);

        verify(bodyTypeDAO).getAll(anyInt(), anyInt());
    }

    @Test
    void getById_ShouldThrownException_WhenMethodParameterIsInvalid() {
        long id = -1L;

        assertThrows(QueryParametersMismatchException.class, () -> bodyTypeServiceImpl.getById(id));

        verify(bodyTypeDAO, never()).getById(anyLong());
    }

    @Test
    void getById_ShouldReturnRecord_WhenMethodParameterIsValid() {
        long id = 1L;
        BodyType expected = new BodyType(1, "sedan");

        when(bodyTypeDAO.getById(anyLong())).thenReturn(bodyType);

        BodyType actual = bodyTypeServiceImpl.getById(id);
        assertEquals(expected, actual);

        verify(bodyTypeDAO).getById(anyLong());
    }

}