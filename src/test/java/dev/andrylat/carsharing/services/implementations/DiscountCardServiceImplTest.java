package dev.andrylat.carsharing.services.implementations;

import dev.andrylat.carsharing.dao.DiscountCardDAO;
import dev.andrylat.carsharing.exceptions.ObjectValidationException;
import dev.andrylat.carsharing.exceptions.QueryParametersMismatchException;
import dev.andrylat.carsharing.models.DiscountCard;
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
class DiscountCardServiceImplTest {

    @Mock
    private DiscountCardDAO discountCardDAO;

    @InjectMocks
    private DiscountCardServiceImpl discountCardServiceImpl;

    private DiscountCard discountCard;
    private List<DiscountCard> discountCards;

    @BeforeEach
    void setUp() {
        discountCards = new ArrayList<>();

        DiscountCard temporal = new DiscountCard();
        temporal.setId(1L);
        temporal.setCardNumber("8267bacb06d2");
        temporal.setDiscountValue(6);
        discountCards.add(temporal);

        temporal = new DiscountCard();
        temporal.setId(2L);
        temporal.setCardNumber("96853b88d930");
        temporal.setDiscountValue(4);
        discountCards.add(temporal);

        discountCard = discountCards.get(0);
    }

    @Test
    public void getRecordsNumber_ShouldReturnZero_WhenTableIsEmpty() {
        long expected = 0L;

        when(discountCardDAO.getRecordsNumber()).thenReturn(0L);

        long actual = discountCardServiceImpl.getRecordsNumber();
        assertEquals(expected, actual);

        verify(discountCardDAO).getRecordsNumber();
    }

    @Test
    public void getRecordsNumber_ShouldReturnRecordsNumber_WhenTableIsNotEmpty() {
        long recordsNumber = discountCards.size();
        long expected = 2L;

        when(discountCardDAO.getRecordsNumber()).thenReturn(recordsNumber);

        long actual = discountCardServiceImpl.getRecordsNumber();
        assertEquals(expected, actual);

        verify(discountCardDAO).getRecordsNumber();
    }

    @Test
    public void getAll_ShouldThrownException_WhenMethodParametersAreInvalid() {
        int pageNumber = -1;
        int pageSize = -1;

        assertThrows(QueryParametersMismatchException.class, () -> discountCardServiceImpl.getAll(pageNumber, pageSize));

        verify(discountCardDAO, never()).getAll(anyInt(), anyInt());
    }

    @Test
    void getAll_ShouldReturnRecords_WhenMethodParametersAreValid() {
        int pageNumber = 0;
        int pageSize = 10;

        List<DiscountCard> expected = new ArrayList<>();
        DiscountCard temporal = new DiscountCard();
        temporal.setId(1L);
        temporal.setCardNumber("8267bacb06d2");
        temporal.setDiscountValue(6);
        expected.add(temporal);

        temporal = new DiscountCard();
        temporal.setId(2L);
        temporal.setCardNumber("96853b88d930");
        temporal.setDiscountValue(4);
        expected.add(temporal);

        when(discountCardDAO.getAll(anyInt(), anyInt())).thenReturn(discountCards);

        List<DiscountCard> actual = discountCardServiceImpl.getAll(pageNumber, pageSize);
        assertEquals(expected, actual);

        verify(discountCardDAO).getAll(anyInt(), anyInt());
    }

    @Test
    void getById_ShouldThrownException_WhenMethodParameterIsInvalid() {
        long id = -1L;

        assertThrows(QueryParametersMismatchException.class, () -> discountCardServiceImpl.getById(id));

        verify(discountCardDAO, never()).getById(anyLong());
    }

    @Test
    void getById_ShouldReturnRecord_WhenMethodParameterIsValid() {
        long id = 1L;

        DiscountCard expected = new DiscountCard();
        expected.setId(1L);
        expected.setCardNumber("8267bacb06d2");
        expected.setDiscountValue(6);

        when(discountCardDAO.getById(anyLong())).thenReturn(discountCard);

        DiscountCard actual = discountCardServiceImpl.getById(id);
        assertEquals(expected, actual);

        verify(discountCardDAO).getById(anyLong());
    }

    @Test
    public void add_ShouldThrowException_WhenAddedObjectIsNull() {
        DiscountCard objectToAdd = null;

        assertThrows(ObjectValidationException.class, () -> discountCardServiceImpl.add(objectToAdd));

        verify(discountCardDAO, never()).add(any());
    }

    @Test
    public void add_ShouldThrowException_WhenAddedObjectIsInvalid() {
        DiscountCard objectToAdd = new DiscountCard();
        objectToAdd.setCardNumber("");
        objectToAdd.setDiscountValue(-1);

        assertThrows(ObjectValidationException.class, () -> discountCardServiceImpl.add(objectToAdd));

        verify(discountCardDAO, never()).add(any());
    }

    @Test
    public void add_ShouldAddObject_WhenAddedObjectIsValid() {
        DiscountCard objectToAdd = new DiscountCard();
        objectToAdd.setId(1L);
        objectToAdd.setCardNumber("8267bacb06d2");
        objectToAdd.setDiscountValue(6);

        DiscountCard expected = new DiscountCard();
        expected.setId(1L);
        expected.setCardNumber("8267bacb06d2");
        expected.setDiscountValue(6);

        when(discountCardDAO.add(any())).thenReturn(discountCard);

        DiscountCard actual = discountCardServiceImpl.add(objectToAdd);
        assertEquals(expected, actual);

        verify(discountCardDAO).add(any());
    }

    @Test
    public void updateById_ShouldThrowException_WhenUpdatedObjectIsNull() {
        DiscountCard objectToUpdate = null;

        assertThrows(ObjectValidationException.class, () -> discountCardServiceImpl.updateById(objectToUpdate));

        verify(discountCardDAO, never()).updateById(any());
    }

    @Test
    public void updateById_ShouldThrowException_WhenUpdatedObjectIsInvalid() {
        DiscountCard objectToUpdate = new DiscountCard();
        objectToUpdate.setId(-1L);
        objectToUpdate.setCardNumber("");
        objectToUpdate.setDiscountValue(-1);

        assertThrows(ObjectValidationException.class, () -> discountCardServiceImpl.updateById(objectToUpdate));

        verify(discountCardDAO, never()).updateById(any());
    }

    @Test
    public void updateById_ShouldUpdateObject_WhenUpdatedObjectIsValid() {
        DiscountCard objectToUpdate = new DiscountCard();
        objectToUpdate.setId(1L);
        objectToUpdate.setCardNumber("8267bacb06d2");
        objectToUpdate.setDiscountValue(6);

        boolean expected = true;

        when(discountCardDAO.updateById(any())).thenReturn(true);

        boolean actual = discountCardServiceImpl.updateById(objectToUpdate);
        assertEquals(expected, actual);

        verify(discountCardDAO).updateById(any());
    }

    @Test
    public void deleteById_ShouldThrowException_WhenMethodParameterIsInvalid() {
        long id = -1L;

        assertThrows(QueryParametersMismatchException.class, () -> discountCardServiceImpl.deleteById(id));

        verify(discountCardDAO, never()).deleteById(anyLong());
    }

    @Test
    public void deleteById_DeleteRecord_WhenMethodParameterIsValid() {
        long id = 1L;
        boolean expected = true;

        when(discountCardDAO.deleteById(anyLong())).thenReturn(true);

        boolean actual = discountCardServiceImpl.deleteById(id);
        assertEquals(expected, actual);

        verify(discountCardDAO).deleteById(anyLong());
    }

}