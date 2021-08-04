package dev.andrylat.carsharing.services.implementations;

import dev.andrylat.carsharing.dao.UserDAO;
import dev.andrylat.carsharing.exceptions.QueryParametersMismatchException;
import dev.andrylat.carsharing.models.User;
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
class UserServiceImplTest {

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    private User user;
    private List<User> users;
    private List<Long> customersId;

    @BeforeEach
    void setUp() {
        users = new ArrayList<>();
        User temporal = new User();
        temporal.setId(1);
        temporal.setEmail("user1@gmail.com");
        temporal.setPassword("3NreW8R");
        temporal.setDiscountCardId(1);
        temporal.setType("customer");
        users.add(temporal);

        temporal = new User();
        temporal.setId(2);
        temporal.setEmail("manager1@awesomecars.com");
        temporal.setPassword("2345qwerty");
        temporal.setType("manager");
        users.add(temporal);

        user = users.get(0);

        customersId = new ArrayList<>();
        customersId.add(1L);
    }

    @Test
    public void getRecordsNumber_ShouldReturnZero_WhenTableIsEmpty() {
        long expected = 0;

        when(userDAO.getRecordsNumber()).thenReturn(0L);

        long actual = userServiceImpl.getRecordsNumber();
        assertEquals(expected, actual);

        verify(userDAO).getRecordsNumber();
    }

    @Test
    public void getRecordsNumber_ShouldReturnRecordsNumber_WhenTableIsNotEmpty() {
        long recordsNumber = users.size();
        long expected = 2;

        when(userDAO.getRecordsNumber()).thenReturn(recordsNumber);

        long actual = userServiceImpl.getRecordsNumber();
        assertEquals(expected, actual);

        verify(userDAO).getRecordsNumber();
    }

    @Test
    public void getAll_ShouldThrownException_WhenMethodParametersAreInvalid() {
        int pageNumber = -1;
        int pageSize = -1;

        assertThrows(QueryParametersMismatchException.class, () -> userServiceImpl.getAll(pageNumber, pageSize));

        verify(userDAO, never()).getAll(anyInt(), anyInt());
    }

    @Test
    void getAll_ShouldReturnRecords_WhenMethodParametersAreValid() {
        int pageNumber = 0;
        int pageSize = 10;

        List<User> expected = new ArrayList<>();
        User temporal = new User();
        temporal.setId(1);
        temporal.setEmail("user1@gmail.com");
        temporal.setPassword("3NreW8R");
        temporal.setDiscountCardId(1);
        temporal.setType("customer");
        expected.add(temporal);

        temporal = new User();
        temporal.setId(2);
        temporal.setEmail("manager1@awesomecars.com");
        temporal.setPassword("2345qwerty");
        temporal.setType("manager");
        expected.add(temporal);

        when(userDAO.getAll(anyInt(), anyInt())).thenReturn(users);

        List<User> actual = userServiceImpl.getAll(pageNumber, pageSize);
        assertEquals(expected, actual);

        verify(userDAO).getAll(anyInt(), anyInt());
    }

    @Test
    void getById_ShouldThrownException_WhenMethodParameterIsInvalid() {
        long id = -1;

        assertThrows(QueryParametersMismatchException.class, () -> userServiceImpl.getById(id));

        verify(userDAO, never()).getById(anyLong());
    }

    @Test
    void getById_ShouldReturnRecord_WhenMethodParameterIsValid() {
        long id = 1L;

        User expected = new User();
        expected.setId(1);
        expected.setEmail("user1@gmail.com");
        expected.setPassword("3NreW8R");
        expected.setDiscountCardId(1);
        expected.setType("customer");

        when(userDAO.getById(anyLong())).thenReturn(user);

        User actual = userServiceImpl.getById(id);
        assertEquals(expected, actual);

        verify(userDAO).getById(anyLong());
    }

    @Test
    void getCustomersNumberByManagerId_ShouldThrownException_WhenMethodParameterIsInvalid() {
        long managerId = -1;

        assertThrows(QueryParametersMismatchException.class,
                () -> userServiceImpl.getCustomersNumberByManagerId(managerId));

        verify(userDAO, never()).getCustomersNumberByManagerId(anyLong());
    }

    @Test
    void getCustomersNumberByManagerId_ShouldReturnZero_WhenTableIsEmpty() {
        long managerId = 1;
        long expected = 0;

        when(userDAO.getCustomersNumberByManagerId(anyLong())).thenReturn(0L);

        long actual = userServiceImpl.getCustomersNumberByManagerId(managerId);
        assertEquals(expected, actual);

        verify(userDAO).getCustomersNumberByManagerId(anyLong());
    }

    @Test
    void getCustomersNumberByManagerId_ShouldReturnRecordsNumber_WhenMethodParameterIsValid() {
        long managerId = 1;
        long expected = 1;

        when(userDAO.getCustomersNumberByManagerId(anyLong())).thenReturn(1L);

        long actual = userServiceImpl.getCustomersNumberByManagerId(managerId);
        assertEquals(expected, actual);

        verify(userDAO).getCustomersNumberByManagerId(anyLong());
    }

    @Test
    void getCustomersIdByManagerId_ShouldThrowException_WhenMethodParametersAreInvalid() {
        long managerId = -1;
        int pageNumber = -1;
        int pageSize = -1;

        assertThrows(QueryParametersMismatchException.class,
                () -> userServiceImpl.getCustomersIdByManagerId(managerId, pageNumber, pageSize));

        verify(userDAO, never()).getCustomersIdByManagerId(anyLong(), anyInt(), anyInt());
    }

    @Test
    void getCustomersIdByManagerId_ShouldReturnRecordsId_WhenMethodParametersAreValid() {
        long managerId = 1;
        int pageNumber = 1;
        int pageSize = 1;

        List<Long> expected = new ArrayList<>();
        expected.add(1L);

        when(userDAO.getCustomersIdByManagerId(anyLong(), anyInt(), anyInt())).thenReturn(customersId);

        List<Long> actual = userServiceImpl.getCustomersIdByManagerId(managerId, pageNumber, pageSize);

        assertEquals(expected, actual);

        verify(userDAO).getCustomersIdByManagerId(anyLong(), anyInt(), anyInt());
    }

}