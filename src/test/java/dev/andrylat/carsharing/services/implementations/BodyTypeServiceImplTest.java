package dev.andrylat.carsharing.services.implementations;

import dev.andrylat.carsharing.dao.BodyTypeDAO;
import dev.andrylat.carsharing.exceptions.ObjectValidationException;
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

    @Test
    public void add_ShouldThrowException_WhenAddedObjectIsNull() {
        BodyType objectToAdd = null;

        assertThrows(ObjectValidationException.class, () -> bodyTypeServiceImpl.add(objectToAdd));

        verify(bodyTypeDAO, never()).add(any());
    }

    @Test
    public void add_ShouldThrowException_WhenAddedObjectIsInvalid() {
        BodyType objectToAdd = new BodyType();
        objectToAdd.setId(1L);
        objectToAdd.setName("");

        assertThrows(ObjectValidationException.class, () -> bodyTypeServiceImpl.add(objectToAdd));

        verify(bodyTypeDAO, never()).add(any());
    }

    @Test
    public void add_ShouldAddObject_WhenAddedObjectIsValid() {
        BodyType objectToAdd = new BodyType();
        objectToAdd.setName("sedan");

        BodyType expected = new BodyType(1L, "sedan");

        when(bodyTypeDAO.add(any())).thenReturn(bodyType);

        BodyType actual = bodyTypeServiceImpl.add(objectToAdd);
        assertEquals(expected, actual);

        verify(bodyTypeDAO).add(any());
    }

    @Test
    public void updateById_ShouldThrowException_WhenUpdatedObjectIsNull() {
        BodyType objectToUpdate = null;

        assertThrows(ObjectValidationException.class, () -> bodyTypeServiceImpl.updateById(objectToUpdate));

        verify(bodyTypeDAO, never()).updateById(any());
    }

    @Test
    public void updateById_ShouldThrowException_WhenUpdatedObjectIsInvalid() {
        BodyType objectToUpdate = new BodyType();
        objectToUpdate.setId(1L);
        objectToUpdate.setName("");

        assertThrows(ObjectValidationException.class, () -> bodyTypeServiceImpl.updateById(objectToUpdate));

        verify(bodyTypeDAO, never()).updateById(any());
    }

    @Test
    public void updateById_ShouldUpdateObject_WhenUpdatedObjectIsValid() {
        BodyType objectToUpdate = new BodyType(1L, "sedan");
        boolean expected = true;

        when(bodyTypeDAO.updateById(any())).thenReturn(true);

        boolean actual = bodyTypeServiceImpl.updateById(objectToUpdate);
        assertEquals(expected, actual);

        verify(bodyTypeDAO).updateById(any());
    }

    @Test
    public void deleteById_ShouldThrowException_WhenMethodParameterIsInvalid() {
        long id = -1L;

        assertThrows(QueryParametersMismatchException.class, () -> bodyTypeServiceImpl.deleteById(id));

        verify(bodyTypeDAO, never()).deleteById(anyLong());
    }

    @Test
    public void deleteById_DeleteRecord_WhenMethodParameterIsValid() {
        long id = 1L;
        boolean expected = true;

        when(bodyTypeDAO.deleteById(anyLong())).thenReturn(true);

        boolean actual = bodyTypeServiceImpl.deleteById(id);
        assertEquals(expected, actual);

        verify(bodyTypeDAO).deleteById(anyLong());
    }

}