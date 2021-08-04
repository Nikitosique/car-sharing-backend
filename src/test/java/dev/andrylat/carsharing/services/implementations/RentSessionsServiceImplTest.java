package dev.andrylat.carsharing.services.implementations;

import dev.andrylat.carsharing.dao.RentSessionDAO;
import dev.andrylat.carsharing.exceptions.QueryParametersMismatchException;
import dev.andrylat.carsharing.models.RentSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.postgresql.util.PGInterval;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RentSessionsServiceImplTest {

    @Mock
    private RentSessionDAO rentSessionDAO;

    @InjectMocks
    private RentSessionsServiceImpl rentSessionsServiceImpl;

    private RentSession rentSession;
    private List<RentSession> rentSessions;

    @BeforeEach
    void setUp() throws SQLException {
        rentSessions = new ArrayList<>();

        RentSession temporal = new RentSession();
        temporal.setId(1L);
        temporal.setCustomerId(1L);
        temporal.setCarId(14L);
        temporal.setRentTimeInterval(new PGInterval("30 day 23 hour 59 minutes 59 second"));
        temporal.setRentSessionCost(100000);
        rentSessions.add(temporal);

        temporal = new RentSession();
        temporal.setId(2L);
        temporal.setCustomerId(2L);
        temporal.setCarId(8L);
        temporal.setRentTimeInterval(new PGInterval("15 hour 36 minutes 47 second"));
        temporal.setRentSessionCost(1500);
        rentSessions.add(temporal);

        rentSession = rentSessions.get(0);
    }

    @Test
    public void getRecordsNumber_ShouldReturnZero_WhenTableIsEmpty() {
        long expected = 0L;

        when(rentSessionDAO.getRecordsNumber()).thenReturn(0L);

        long actual = rentSessionsServiceImpl.getRecordsNumber();
        assertEquals(expected, actual);

        verify(rentSessionDAO).getRecordsNumber();
    }

    @Test
    public void getRecordsNumber_ShouldReturnRecordsNumber_WhenTableIsNotEmpty() {
        long recordsNumber = rentSessions.size();
        long expected = 2L;

        when(rentSessionDAO.getRecordsNumber()).thenReturn(recordsNumber);

        long actual = rentSessionsServiceImpl.getRecordsNumber();
        assertEquals(expected, actual);

        verify(rentSessionDAO).getRecordsNumber();
    }

    @Test
    public void getAll_ShouldThrownException_WhenMethodParametersAreInvalid() {
        int pageNumber = -1;
        int pageSize = -1;

        assertThrows(QueryParametersMismatchException.class, () -> rentSessionsServiceImpl.getAll(pageNumber, pageSize));

        verify(rentSessionDAO, never()).getAll(anyInt(), anyInt());
    }

    @Test
    void getAll_ShouldReturnRecords_WhenMethodParametersAreValid() throws SQLException {
        int pageNumber = 0;
        int pageSize = 10;

        List<RentSession> expected = new ArrayList<>();
        RentSession temporal = new RentSession();
        temporal.setId(1L);
        temporal.setCustomerId(1L);
        temporal.setCarId(14L);
        temporal.setRentTimeInterval(new PGInterval("30 day 23 hour 59 minutes 59 second"));
        temporal.setRentSessionCost(100000);
        expected.add(temporal);

        temporal = new RentSession();
        temporal.setId(2L);
        temporal.setCustomerId(2L);
        temporal.setCarId(8L);
        temporal.setRentTimeInterval(new PGInterval("15 hour 36 minutes 47 second"));
        temporal.setRentSessionCost(1500);
        expected.add(temporal);

        when(rentSessionDAO.getAll(anyInt(), anyInt())).thenReturn(rentSessions);

        List<RentSession> actual = rentSessionsServiceImpl.getAll(pageNumber, pageSize);
        assertEquals(expected, actual);

        verify(rentSessionDAO).getAll(anyInt(), anyInt());
    }

    @Test
    void getById_ShouldThrownException_WhenMethodParameterIsInvalid() {
        long id = -1L;

        assertThrows(QueryParametersMismatchException.class, () -> rentSessionsServiceImpl.getById(id));

        verify(rentSessionDAO, never()).getById(anyLong());
    }

    @Test
    void getById_ShouldReturnRecord_WhenMethodParameterIsValid() throws SQLException {
        long id = 1L;

        RentSession expected = new RentSession();
        expected.setId(1L);
        expected.setCustomerId(1L);
        expected.setCarId(14L);
        expected.setRentTimeInterval(new PGInterval("30 day 23 hour 59 minutes 59 second"));
        expected.setRentSessionCost(100000);

        when(rentSessionDAO.getById(anyLong())).thenReturn(rentSession);

        RentSession actual = rentSessionsServiceImpl.getById(id);
        assertEquals(expected, actual);

        verify(rentSessionDAO).getById(anyLong());
    }

}