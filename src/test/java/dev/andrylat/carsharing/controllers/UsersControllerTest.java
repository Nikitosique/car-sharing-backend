package dev.andrylat.carsharing.controllers;

import dev.andrylat.carsharing.models.User;
import dev.andrylat.carsharing.services.UserService;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UsersControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UsersController usersController;

    private MockMvc mockMvc;
    private long recordsNumber;
    private long customersIdNumber;
    private long managerId;
    private int pageNumber;
    private int usersListSize;
    private int customersIdListSize;
    private User user;
    private List<User> users;
    private List<Long> customersId;

    @BeforeEach
    public void setUp() {
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

        customersId = new ArrayList<>();
        customersId.add(1L);

        pageNumber = 0;

        recordsNumber = 2L;
        customersIdNumber = 1L;

        managerId = 2L;

        usersListSize = users.size();
        customersIdListSize = customersId.size();

        user = users.get(0);

        mockMvc = MockMvcBuilders
                .standaloneSetup(usersController)
                .build();
    }

    @Test
    public void getAll_ShouldReturn404Response_WhenUrlIsInvalid() throws Exception {
        mockMvc.perform(get("/invalidUrl"))
                .andExpect(status().isNotFound());

        verify(userService, never()).getAll(anyInt(), anyInt());
    }

    @Test
    public void getAll_ShouldThrownException_WhenQueryParametersAreInvalid() {
        when(userService.getAll(anyInt(), anyInt())).thenThrow(MockitoException.class);

        assertThrows(NestedServletException.class,
                () -> mockMvc.perform(get("/users?pageNumber=-1&pageSize=-1")));

        verify(userService).getAll(anyInt(), anyInt());
    }

    @Test
    public void getAll_ShouldReturnRecords_WhenQueryParametersAreAbsent() throws Exception {
        int pageSize = 10;

        when(userService.getRecordsNumber()).thenReturn(recordsNumber);
        when(userService.getAll(anyInt(), anyInt())).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/getAll"))
                .andExpect(model().attribute("users", hasSize(usersListSize)))
                .andExpect(model().attribute("recordsNumber", recordsNumber))
                .andExpect(model().attribute("pageNumber", pageNumber))
                .andExpect(model().attribute("pageSize", pageSize));

        verify(userService).getRecordsNumber();
        verify(userService).getAll(anyInt(), anyInt());
    }

    @Test
    public void getAll_ShouldReturnRecords_WhenQueryParametersArePresent() throws Exception {
        int pageSize = 5;

        when(userService.getAll(anyInt(), anyInt())).thenReturn(users);
        when(userService.getRecordsNumber()).thenReturn(recordsNumber);

        mockMvc.perform(get("/users?pageNumber=0&pageSize=5"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/getAll"))
                .andExpect(model().attribute("users", hasSize(usersListSize)))
                .andExpect(model().attribute("recordsNumber", recordsNumber))
                .andExpect(model().attribute("pageNumber", pageNumber))
                .andExpect(model().attribute("pageSize", pageSize));

        verify(userService).getRecordsNumber();
        verify(userService).getAll(anyInt(), anyInt());
    }

    @Test
    public void getById_ShouldThrowException_WhenPathParameterIsInvalid() {
        when(userService.getById(anyLong())).thenThrow(MockitoException.class);

        assertThrows(NestedServletException.class,
                () -> mockMvc.perform(get("/users/-1")));

        verify(userService).getById(anyLong());
    }

    @Test
    public void getById_ShouldReturnRecord_WhenPathParameterIsValid() throws Exception {
        User expected = new User();
        expected.setId(1L);
        expected.setEmail("user1@gmail.com");
        expected.setPassword("3NreW8R");
        expected.setDiscountCardId(1L);
        expected.setType("customer");

        when(userService.getById(anyLong())).thenReturn(user);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/getById"))
                .andExpect(model().attribute("user", expected));

        verify(userService).getById(anyLong());
    }

    @Test
    public void showAdditionPage_ShouldShowAdditionPage_WhenUrlIsValid() throws Exception {
        mockMvc.perform(get("/users/add"))
                .andExpect(view().name("users/add"));
    }

    @Test
    public void showCustomerAdditionForm_ShouldShowForm_WhenUrlIsValid() throws Exception {
        User expected = new User();

        mockMvc.perform(get("/users/add/customer"))
                .andExpect(view().name("users/addCustomer"))
                .andExpect(model().attribute("user", expected));
    }

    @Test
    public void showManagerAdditionForm_ShouldShowForm_WhenUrlIsValid() throws Exception {
        User expected = new User();

        mockMvc.perform(get("/users/add/manager"))
                .andExpect(view().name("users/addManager"))
                .andExpect(model().attribute("user", expected));
    }

    @Test
    public void add_ShouldReturnAdditionForm_WhenAddedObjectIsInvalid() throws Exception {
        User expected = new User();
        expected.setEmail("");
        expected.setPassword("");
        expected.setDiscountCardId(0L);
        expected.setType("customer");

        mockMvc.perform(post("/users/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                                new BasicNameValuePair("email", ""),
                                new BasicNameValuePair("password", ""),
                                new BasicNameValuePair("discountCardId", "0"),
                                new BasicNameValuePair("type", "customer")
                        )))))
                .andExpect(status().isOk())
                .andExpect(view().name("users/addCustomer"))
                .andExpect(model().attribute("user", expected));

        verify(userService, never()).add(any());
    }

    @Test
    public void add_ShouldAddObject_WhenAddedObjectIsValid() throws Exception {
        User expected = new User();
        expected.setEmail("user1@gmail.com");
        expected.setPassword("3NreW8R");
        expected.setDiscountCardId(1L);
        expected.setType("customer");

        when(userService.add(any())).thenReturn(user);

        mockMvc.perform(post("/users/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                                new BasicNameValuePair("email", "user1@gmail.com"),
                                new BasicNameValuePair("password", "3NreW8R"),
                                new BasicNameValuePair("discountCardId", "1"),
                                new BasicNameValuePair("type", "customer")
                        )))))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users/1"))
                .andExpect(model().attribute("user", expected));

        verify(userService).add(any());
    }

    @Test
    public void showUpdatingForm_ShouldThrowException_WhenPathParameterIsInvalid() {
        when(userService.getById(anyLong())).thenThrow(MockitoException.class);

        assertThrows(NestedServletException.class,
                () -> mockMvc.perform(get("/users/-1/edit")));

        verify(userService).getById(anyLong());
    }

    @Test
    public void showUpdatingForm_ShouldShowUpdatingForm_WhenPathParameterIsValid() throws Exception {
        when(userService.getById(anyLong())).thenReturn(user);

        User expected = new User();
        expected.setId(1L);
        expected.setEmail("user1@gmail.com");
        expected.setPassword("3NreW8R");
        expected.setDiscountCardId(1L);
        expected.setType("customer");

        mockMvc.perform(get("/users/1/edit"))
                .andExpect(view().name("users/edit"))
                .andExpect(model().attribute("user", expected));

        verify(userService).getById(anyLong());
    }

    @Test
    public void updateById_ShouldReturnUpdatingForm_WhenUpdatedObjectIsInvalid() throws Exception {
        User expected = new User();
        expected.setEmail("");
        expected.setPassword("");
        expected.setDiscountCardId(0L);
        expected.setType("customer");

        mockMvc.perform(post("/users/edit")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                                new BasicNameValuePair("email", ""),
                                new BasicNameValuePair("password", ""),
                                new BasicNameValuePair("discountCardId", "0"),
                                new BasicNameValuePair("type", "customer")
                        )))))
                .andExpect(status().isOk())
                .andExpect(view().name("users/edit"))
                .andExpect(model().attribute("user", expected));

        verify(userService, never()).updateById(any());
    }

    @Test
    public void updateById_ShouldUpdateObject_WhenUpdatedObjectIsValid() throws Exception {
        when(userService.updateById(any())).thenReturn(true);

        User expected = new User();
        expected.setId(1L);
        expected.setEmail("user1@gmail.com");
        expected.setPassword("3NreW8R");
        expected.setDiscountCardId(1L);
        expected.setType("customer");

        mockMvc.perform(post("/users/edit")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(EntityUtils.toString(new UrlEncodedFormEntity(List.of(
                                new BasicNameValuePair("id", "1"),
                                new BasicNameValuePair("email", "user1@gmail.com"),
                                new BasicNameValuePair("password", "3NreW8R"),
                                new BasicNameValuePair("discountCardId", "1"),
                                new BasicNameValuePair("type", "customer")
                        )))))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users/1"))
                .andExpect(model().attribute("user", expected));

        verify(userService).updateById(any());
    }

    @Test
    public void deleteById_ShouldThrowException_WhenPathParameterIsInvalid() {
        when(userService.deleteById(anyLong())).thenThrow(MockitoException.class);

        assertThrows(NestedServletException.class,
                () -> mockMvc.perform(post("/users/-1/delete")));

        verify(userService).deleteById(anyLong());
    }

    @Test
    public void deleteById_ShouldDeleteRecord_WhenPathParameterIsValid() throws Exception {
        when(userService.deleteById(anyLong())).thenReturn(true);

        mockMvc.perform(post("/users/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users"));

        verify(userService).deleteById(anyLong());
    }

    @Test
    public void getCustomersIdByManagerId_ShouldThrowException_WhenPathParameterAndQueryParametersAreInvalid() {
        when(userService.getCustomersIdByManagerId(anyLong(), anyInt(), anyInt())).thenThrow(MockitoException.class);

        assertThrows(NestedServletException.class,
                () -> mockMvc.perform(get("/users/managers/-1?pageNumber=-1&pageSize=-1")));

        verify(userService).getCustomersIdByManagerId(anyLong(), anyInt(), anyInt());
    }

    @Test
    public void getCustomersIdByManagerId_ShouldReturnCustomersId_WhenPathParameterIsValidAndQueryParametersAreAbsent() throws Exception {
        int pageSize = 10;

        when(userService.getCustomersNumberByManagerId(anyLong())).thenReturn(customersIdNumber);
        when(userService.getCustomersIdByManagerId(anyLong(), anyInt(), anyInt())).thenReturn(customersId);

        mockMvc.perform(get("/users/managers/2"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/getCustomersIdByManagerId"))
                .andExpect(model().attribute("customersId", hasSize(customersIdListSize)))
                .andExpect(model().attribute("customersIdNumber", customersIdNumber))
                .andExpect(model().attribute("managerId", managerId))
                .andExpect(model().attribute("pageNumber", pageNumber))
                .andExpect(model().attribute("pageSize", pageSize));

        verify(userService).getCustomersNumberByManagerId(anyLong());
        verify(userService).getCustomersIdByManagerId(anyLong(), anyInt(), anyInt());
    }

    @Test
    public void getCustomersIdByManagerId_ShouldReturnCustomersId_WhenPathParameterIsValidAndQueryParametersArePresent() throws Exception {
        int pageSize = 5;

        when(userService.getCustomersNumberByManagerId(anyLong())).thenReturn(customersIdNumber);
        when(userService.getCustomersIdByManagerId(anyLong(), anyInt(), anyInt())).thenReturn(customersId);

        mockMvc.perform(get("/users/managers/2?pageNumber=0&pageSize=5"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/getCustomersIdByManagerId"))
                .andExpect(model().attribute("customersId", hasSize(customersIdListSize)))
                .andExpect(model().attribute("customersIdNumber", customersIdNumber))
                .andExpect(model().attribute("managerId", managerId))
                .andExpect(model().attribute("pageNumber", pageNumber))
                .andExpect(model().attribute("pageSize", pageSize));

        verify(userService).getCustomersNumberByManagerId(anyLong());
        verify(userService).getCustomersIdByManagerId(anyLong(), anyInt(), anyInt());
    }

    @Test
    public void showAssigmentForm_ShouldShowForm_WhenUrlIsValid() throws Exception {
        mockMvc.perform(get("/users/managers/assign"))
                .andExpect(view().name("users/assignCustomerToManager"));
    }

    @Test
    public void assignCustomerToManager_ShouldThrowException_WhenCustomerAndManagerWithSuchIdNotExist() {
        when(userService.assignCustomerToManager(anyLong(), anyLong())).thenThrow(MockitoException.class);

        assertThrows(NestedServletException.class,
                () -> mockMvc.perform(post("/users/managers/assign")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                                new BasicNameValuePair("customerId", "0"),
                                new BasicNameValuePair("managerId", "-1")
                        ))))));

        verify(userService).assignCustomerToManager(anyLong(), anyLong());
    }

    @Test
    public void assignCustomerToManager_ShouldThrowException_WhenCustomerAndManagerAreAlreadyAssigned() {
        when(userService.assignCustomerToManager(anyLong(), anyLong())).thenThrow(MockitoException.class);

        assertThrows(NestedServletException.class,
                () -> mockMvc.perform(post("/users/managers/assign")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                                new BasicNameValuePair("customerId", "1"),
                                new BasicNameValuePair("managerId", "2")
                        ))))));

        verify(userService).assignCustomerToManager(anyLong(), anyLong());
    }

    @Test
    public void assignCustomerToManager_ShouldAssignCustomerToManager_WhenCustomerAndManagerAreNotAssigned() throws Exception {
        when(userService.assignCustomerToManager(anyLong(), anyLong())).thenReturn(true);

        mockMvc.perform(post("/users/managers/assign")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(EntityUtils.toString(new UrlEncodedFormEntity(List.of(
                                new BasicNameValuePair("customerId", "1"),
                                new BasicNameValuePair("managerId", "3")
                        )))))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users/managers/3"));

        verify(userService).assignCustomerToManager(anyLong(), anyLong());
    }

    @Test
    public void showUnassignmentForm_ShouldShowForm_WhenUrlIsValid() throws Exception {
        mockMvc.perform(get("/users/managers/unassign"))
                .andExpect(view().name("users/unassignCustomerFromManager"));
    }

    @Test
    public void unassignCustomerFromManager_ShouldThrowException_WhenCustomerAndManagerWithSuchIdsAreNotExist() {
        when(userService.unassignCustomerFromManager(anyLong(), anyLong())).thenThrow(MockitoException.class);

        assertThrows(NestedServletException.class,
                () -> mockMvc.perform(post("/users/managers/unassign")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                                new BasicNameValuePair("customerId", "0"),
                                new BasicNameValuePair("managerId", "-1")
                        ))))));

        verify(userService).unassignCustomerFromManager(anyLong(), anyLong());
    }

    @Test
    public void unassignCustomerFromManager_ShouldThrowException_WhenCustomerAndManagerAreAlreadyUnassigned() throws Exception {
        when(userService.unassignCustomerFromManager(anyLong(), anyLong())).thenThrow(MockitoException.class);

        assertThrows(NestedServletException.class,
                () -> mockMvc.perform(post("/users/managers/unassign")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                                new BasicNameValuePair("customerId", "1"),
                                new BasicNameValuePair("managerId", "2")
                        ))))));

        verify(userService).unassignCustomerFromManager(anyLong(), anyLong());
    }

    @Test
    public void unassignCustomerFromManager_ShouldUnassignCustomerToManager_WhenCustomerAndManagerAreAssigned() throws Exception {
        when(userService.unassignCustomerFromManager(anyLong(), anyLong())).thenReturn(true);

        mockMvc.perform(post("/users/managers/unassign")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(EntityUtils.toString(new UrlEncodedFormEntity(List.of(
                                new BasicNameValuePair("customerId", "1"),
                                new BasicNameValuePair("managerId", "3")
                        )))))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users/managers/3"));

        verify(userService).unassignCustomerFromManager(anyLong(), anyLong());
    }

}
