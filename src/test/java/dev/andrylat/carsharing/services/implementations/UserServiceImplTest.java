package dev.andrylat.carsharing.services.implementations;

import dev.andrylat.carsharing.dao.UserDAO;
import dev.andrylat.carsharing.exceptions.AssignmentException;
import dev.andrylat.carsharing.exceptions.ObjectValidationException;
import dev.andrylat.carsharing.exceptions.QueryParametersMismatchException;
import dev.andrylat.carsharing.exceptions.RecordNotFoundException;
import dev.andrylat.carsharing.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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
    private User manager;
    private User customer;
    private List<User> users;
    private List<Long> customersId;

    @BeforeEach
    void setUp() {
        users = new ArrayList<>();
        User temporal = new User();
        temporal.setId(1L);
        temporal.setEmail("user1@gmail.com");
        temporal.setPassword("3NreW8R");
        temporal.setDiscountCardId(1L);
        temporal.setType("customer");
        users.add(temporal);

        temporal = new User();
        temporal.setId(2L);
        temporal.setEmail("manager1@awesomecars.com");
        temporal.setPassword("2345qwerty");
        temporal.setType("manager");
        users.add(temporal);

        user = users.get(0);
        manager = users.get(1);
        customer = users.get(0);

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
        long expected = 2L;

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
        temporal.setId(1L);
        temporal.setEmail("user1@gmail.com");
        temporal.setPassword("3NreW8R");
        temporal.setDiscountCardId(1L);
        temporal.setType("customer");
        expected.add(temporal);

        temporal = new User();
        temporal.setId(2L);
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
        long id = -1L;

        assertThrows(QueryParametersMismatchException.class, () -> userServiceImpl.getById(id));

        verify(userDAO, never()).getById(anyLong());
    }

    @Test
    void getById_ShouldReturnRecord_WhenMethodParameterIsValid() {
        long id = 1L;

        User expected = new User();
        expected.setId(1L);
        expected.setEmail("user1@gmail.com");
        expected.setPassword("3NreW8R");
        expected.setDiscountCardId(1L);
        expected.setType("customer");

        when(userDAO.getById(anyLong())).thenReturn(user);

        User actual = userServiceImpl.getById(id);
        assertEquals(expected, actual);

        verify(userDAO).getById(anyLong());
    }

    @Test
    public void add_ShouldThrowException_WhenAddedObjectIsNull() {
        User objectToAdd = null;

        assertThrows(ObjectValidationException.class, () -> userServiceImpl.add(objectToAdd));

        verify(userDAO, never()).add(any());
    }

    @Test
    public void add_ShouldThrowException_WhenAddedObjectIsInvalid() {
        User objectToAdd = new User();
        objectToAdd.setEmail("");
        objectToAdd.setPassword("");
        objectToAdd.setDiscountCardId(-1L);
        objectToAdd.setType("");

        assertThrows(ObjectValidationException.class, () -> userServiceImpl.add(objectToAdd));

        verify(userDAO, never()).add(any());
    }

    @Test
    public void add_ShouldAddObject_WhenAddedObjectIsValid() {
        User objectToAdd = new User();
        objectToAdd.setEmail("user1@gmail.com");
        objectToAdd.setPassword("3NreW8R");
        objectToAdd.setDiscountCardId(1L);
        objectToAdd.setType("customer");

        User expected = new User();
        expected.setId(1L);
        expected.setEmail("user1@gmail.com");
        expected.setPassword("3NreW8R");
        expected.setDiscountCardId(1L);
        expected.setType("customer");

        when(userDAO.add(any())).thenReturn(user);

        User actual = userServiceImpl.add(objectToAdd);
        assertEquals(expected, actual);

        verify(userDAO).add(any());
    }

    @Test
    public void updateById_ShouldThrowException_WhenUpdatedObjectIsNull() {
        User objectToUpdate = null;

        assertThrows(ObjectValidationException.class, () -> userServiceImpl.updateById(objectToUpdate));

        verify(userDAO, never()).updateById(any());
    }

    @Test
    public void updateById_ShouldThrowException_WhenUpdatedObjectIsInvalid() {
        User objectToUpdate = new User();
        objectToUpdate.setId(-1L);
        objectToUpdate.setEmail("");
        objectToUpdate.setPassword("");
        objectToUpdate.setDiscountCardId(-1L);
        objectToUpdate.setType("");

        assertThrows(ObjectValidationException.class, () -> userServiceImpl.updateById(objectToUpdate));

        verify(userDAO, never()).updateById(any());
    }

    @Test
    public void updateById_ShouldUpdateObject_WhenUpdatedObjectIsValid() {
        User objectToUpdate = new User();
        objectToUpdate.setId(1L);
        objectToUpdate.setEmail("user1@gmail.com");
        objectToUpdate.setPassword("3NreW8R");
        objectToUpdate.setDiscountCardId(1L);
        objectToUpdate.setType("customer");

        boolean expected = true;

        when(userDAO.updateById(any())).thenReturn(true);

        boolean actual = userServiceImpl.updateById(objectToUpdate);
        assertEquals(expected, actual);

        verify(userDAO).updateById(any());
    }

    @Test
    public void deleteById_ShouldThrowException_WhenMethodParameterIsInvalid() {
        long id = -1L;

        assertThrows(QueryParametersMismatchException.class, () -> userServiceImpl.deleteById(id));

        verify(userDAO, never()).deleteById(anyLong());
    }

    @Test
    public void deleteById_DeleteRecord_WhenMethodParameterIsValid() {
        long id = 1L;
        boolean expected = true;

        when(userDAO.deleteById(anyLong())).thenReturn(true);

        boolean actual = userServiceImpl.deleteById(id);
        assertEquals(expected, actual);

        verify(userDAO).deleteById(anyLong());
    }

    @Test
    void getCustomersNumberByManagerId_ShouldThrownException_WhenMethodParameterIsInvalid() {
        long managerId = -1L;

        assertThrows(QueryParametersMismatchException.class,
                () -> userServiceImpl.getCustomersNumberByManagerId(managerId));

        verify(userDAO, never()).getCustomersNumberByManagerId(anyLong());
    }

    @Test
    void getCustomersNumberByManagerId_ShouldThrownException_WhenManagerWithSuchIdNotExists() {
        long managerId = 1L;

        when(userDAO.getById(anyLong())).thenThrow(AssignmentException.class);

        assertThrows(AssignmentException.class,
                () -> userServiceImpl.getCustomersNumberByManagerId(managerId));

        verify(userDAO, never()).getCustomersNumberByManagerId(anyLong());
        verify(userDAO).getById(anyLong());
    }

    @Test
    void getCustomersNumberByManagerId_ShouldReturnZero_WhenTableIsEmpty() {
        long expected = 0;
        long managerId = 1;

        when(userDAO.getById(anyLong())).thenReturn(manager);
        when(userDAO.getCustomersNumberByManagerId(anyLong())).thenReturn(0L);

        long actual = userServiceImpl.getCustomersNumberByManagerId(managerId);
        assertEquals(expected, actual);

        verify(userDAO).getCustomersNumberByManagerId(anyLong());
        verify(userDAO).getById(anyLong());
    }

    @Test
    void getCustomersNumberByManagerId_ShouldReturnRecordsNumber_WhenMethodParameterIsValid() {
        long expected = 1;
        long managerId = 1;

        when(userDAO.getById(anyLong())).thenReturn(manager);
        when(userDAO.getCustomersNumberByManagerId(anyLong())).thenReturn(1L);

        long actual = userServiceImpl.getCustomersNumberByManagerId(managerId);
        assertEquals(expected, actual);

        verify(userDAO).getCustomersNumberByManagerId(anyLong());
        verify(userDAO).getById(anyLong());
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
    void getCustomersIdByManagerId_ShouldThrownException_WhenManagerWithSuchIdNotExists() {
        long managerId = 1L;
        int pageNumber = 1;
        int pageSize = 1;

        when(userDAO.getById(anyLong())).thenThrow(AssignmentException.class);

        assertThrows(AssignmentException.class,
                () -> userServiceImpl.getCustomersIdByManagerId(managerId, pageNumber, pageSize));

        verify(userDAO, never()).getCustomersIdByManagerId(anyLong(), anyInt(), anyInt());

        verify(userDAO).getById(anyLong());
    }

    @Test
    void getCustomersIdByManagerId_ShouldReturnRecordsId_WhenMethodParametersAreValid() {
        long managerId = 1;
        int pageNumber = 1;
        int pageSize = 1;

        List<Long> expected = new ArrayList<>();
        expected.add(1L);

        when(userDAO.getById(anyLong())).thenReturn(manager);
        when(userDAO.getCustomersIdByManagerId(anyLong(), anyInt(), anyInt())).thenReturn(customersId);

        List<Long> actual = userServiceImpl.getCustomersIdByManagerId(managerId, pageNumber, pageSize);

        assertEquals(expected, actual);

        verify(userDAO).getById(anyLong());
        verify(userDAO).getCustomersIdByManagerId(anyLong(), anyInt(), anyInt());
    }

    @Test
    public void checkUserAssignedToManager_ShouldThrowException_WhenMethodParametersAreInvalid() {
        long managerId = -1L;
        long customerId = -2L;

        assertThrows(QueryParametersMismatchException.class,
                () -> userServiceImpl.checkUserAssignedToManager(customerId, managerId));

        verify(userDAO, never()).getAssignmentsNumber(anyLong(), anyLong());
    }

    @Test
    public void checkUserAssignedToManager_ShouldReturnTrue_WhenMethodParametersAreValid() {
        long managerId = 1L;
        long customerId = 2L;
        boolean expected = true;

        when(userDAO.getById(managerId)).thenReturn(manager);
        when(userDAO.getById(customerId)).thenReturn(customer);
        when(userDAO.getAssignmentsNumber(anyLong(), anyLong())).thenReturn(1L);

        boolean actual = userServiceImpl.checkUserAssignedToManager(customerId, managerId);
        assertEquals(expected, actual);

        verify(userDAO, times(2)).getById(anyLong());
        verify(userDAO).getAssignmentsNumber(anyLong(), anyLong());
    }

    @Test
    public void assignCustomerToManager_ShouldThrowException_WhenMethodParametersAreInvalid() {
        long managerId = -1L;
        long customerId = -2L;

        assertThrows(QueryParametersMismatchException.class, () -> userServiceImpl.assignCustomerToManager(customerId, managerId));

        verify(userDAO, never()).assignCustomerToManager(anyLong(), anyLong());
    }

    @Test
    public void assignCustomerToManager_ShouldThrowException_WhenCustomerAndManagerWithSuchIdNotPresentInTable() {
        long managerId = 1L;
        long customerId = 2L;

        when(userDAO.getById(anyLong())).thenThrow(RecordNotFoundException.class);

        assertThrows(RecordNotFoundException.class,
                () -> userServiceImpl.assignCustomerToManager(customerId, managerId));

        verify(userDAO).getById(anyLong());
        verify(userDAO, never()).assignCustomerToManager(anyLong(), anyLong());
    }

    @Test
    public void assignCustomerToManager_ShouldThrowException_WhenCustomerAndManagerIdNotBelongToActualCustomerAndManager() {
        long managerId = 1L;
        long customerId = 2L;

        when(userDAO.getById(customerId)).thenReturn(manager);

        assertThrows(AssignmentException.class,
                () -> userServiceImpl.assignCustomerToManager(customerId, managerId));

        verify(userDAO).getById(anyLong());
        verify(userDAO, never()).assignCustomerToManager(anyLong(), anyLong());
    }

    @Test
    public void assignCustomerToManager_ShouldThrowException_WhenCustomerAndManagerAreAlreadyAssigned() {
        long managerId = 1L;
        long customerId = 2L;

        when(userDAO.getById(customerId)).thenReturn(customer);
        when(userDAO.getById(managerId)).thenReturn(manager);
        when(userDAO.getAssignmentsNumber(anyLong(), anyLong())).thenReturn(1L);

        assertThrows(AssignmentException.class,
                () -> userServiceImpl.assignCustomerToManager(customerId, managerId));

        verify(userDAO, times(4)).getById(anyLong());
        verify(userDAO).getAssignmentsNumber(anyLong(), anyLong());
        verify(userDAO, never()).assignCustomerToManager(anyLong(), anyLong());
    }

    @Test
    public void assignCustomerToManager_ShouldAssignCustomerToManager_WhenMethodParametersAreValid() {
        long managerId = 1L;
        long customerId = 2L;

        boolean expected = true;

        when(userDAO.getById(managerId)).thenReturn(manager);
        when(userDAO.getById(customerId)).thenReturn(customer);
        when(userDAO.getAssignmentsNumber(anyLong(), anyLong())).thenReturn(0L);
        when(userDAO.assignCustomerToManager(anyLong(), anyLong())).thenReturn(true);

        boolean actual = userServiceImpl.assignCustomerToManager(customerId, managerId);
        assertEquals(expected, actual);

        verify(userDAO, times(4)).getById(anyLong());
        verify(userDAO).getAssignmentsNumber(anyLong(), anyLong());
        verify(userDAO).assignCustomerToManager(anyLong(), anyLong());
    }

    @Test
    public void unassignCustomerFromManager_ShouldThrowException_WhenMethodParametersAreInvalid() {
        long managerId = -1L;
        long customerId = -1L;

        assertThrows(QueryParametersMismatchException.class,
                () -> userServiceImpl.unassignCustomerFromManager(customerId, managerId));

        verify(userDAO, never()).unassignCustomerFromManager(anyLong(), anyLong());
    }

    @Test
    public void unassignCustomerFromManager_ShouldThrowException_WhenCustomerAndManagerWithSuchIdNotPresentInTable() {
        long managerId = 1L;
        long customerId = 2L;

        when(userDAO.getById(anyLong())).thenThrow(RecordNotFoundException.class);

        assertThrows(RecordNotFoundException.class,
                () -> userServiceImpl.assignCustomerToManager(customerId, managerId));

        verify(userDAO, never()).assignCustomerToManager(anyLong(), anyLong());
    }

    @Test
    public void unassignCustomerFromManager_ShouldThrowException_WhenCustomerAndManagerIdNotBelongToActualCustomerAndManager() {
        long managerId = 1L;
        long customerId = 2L;

        when(userDAO.getById(customerId)).thenReturn(manager);

        assertThrows(AssignmentException.class,
                () -> userServiceImpl.unassignCustomerFromManager(customerId, managerId));

        verify(userDAO).getById(anyLong());
        verify(userDAO, never()).unassignCustomerFromManager(anyLong(), anyLong());
    }

    @Test
    public void unassignCustomerFromManager_ShouldThrowException_WhenCustomerAndManagerNotAssigned() {
        long managerId = 1L;
        long customerId = 2L;

        when(userDAO.getById(customerId)).thenReturn(customer);
        when(userDAO.getById(managerId)).thenReturn(manager);
        when(userDAO.getAssignmentsNumber(anyLong(), anyLong())).thenReturn(0L);

        assertThrows(AssignmentException.class,
                () -> userServiceImpl.unassignCustomerFromManager(customerId, managerId));

        verify(userDAO, times(4)).getById(anyLong());
        verify(userDAO).getAssignmentsNumber(anyLong(), anyLong());
        verify(userDAO, never()).unassignCustomerFromManager(anyLong(), anyLong());
    }

    @Test
    public void unassignCustomerFromManager_ShouldDeleteRecord_WhenMethodParameterIsValid() {
        long managerId = 1L;
        long customerId = 2L;
        boolean expected = true;

        when(userDAO.getById(customerId)).thenReturn(customer);
        when(userDAO.getById(managerId)).thenReturn(manager);
        when(userDAO.getAssignmentsNumber(anyLong(), anyLong())).thenReturn(1L);
        when(userDAO.unassignCustomerFromManager(anyLong(), anyLong())).thenReturn(true);

        boolean actual = userServiceImpl.unassignCustomerFromManager(customerId, managerId);
        assertEquals(expected, actual);

        verify(userDAO, times(4)).getById(anyLong());
        verify(userDAO).getAssignmentsNumber(anyLong(), anyLong());
        verify(userDAO).unassignCustomerFromManager(anyLong(), anyLong());
    }

}