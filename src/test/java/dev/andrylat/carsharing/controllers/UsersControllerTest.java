package dev.andrylat.carsharing.controllers;

import dev.andrylat.carsharing.models.User;
import dev.andrylat.carsharing.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import java.util.ArrayList;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UsersControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UsersController usersController;

    @Captor
    private ArgumentCaptor<Integer> pageNumberCaptor;

    @Captor
    private ArgumentCaptor<Integer> pageSizeCaptor;

    @Captor
    private ArgumentCaptor<Long> idCaptor;

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

        when(userService.getAll(anyInt(), anyInt())).thenReturn(users);
        when(userService.getRecordsNumber()).thenReturn(recordsNumber);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/getAll"))
                .andExpect(model().attribute("users", hasSize(usersListSize)))
                .andExpect(model().attribute("recordsNumber", recordsNumber))
                .andExpect(model().attribute("pageNumber", pageNumber))
                .andExpect(model().attribute("pageSize", pageSize));

        verify(userService).getAll(pageNumberCaptor.capture(), pageSizeCaptor.capture());

        assertThat(pageNumberCaptor.getValue(), is(pageNumber));
        assertThat(pageSizeCaptor.getValue(), is(pageSize));

        verify(userService).getRecordsNumber();
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

        verify(userService).getAll(pageNumberCaptor.capture(), pageSizeCaptor.capture());

        assertThat(pageNumberCaptor.getValue(), is(pageNumber));
        assertThat(pageSizeCaptor.getValue(), is(pageSize));

        verify(userService).getRecordsNumber();
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

        verify(userService).getById(idCaptor.capture());

        assertThat(idCaptor.getValue(), is(1L));
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

        when(userService.getCustomersIdByManagerId(anyLong(), anyInt(), anyInt())).thenReturn(customersId);
        when(userService.getCustomersNumberByManagerId(anyLong())).thenReturn(customersIdNumber);

        mockMvc.perform(get("/users/managers/2"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/getCustomersIdByManagerId"))
                .andExpect(model().attribute("customersId", hasSize(customersIdListSize)))
                .andExpect(model().attribute("customersIdNumber", customersIdNumber))
                .andExpect(model().attribute("managerId", managerId))
                .andExpect(model().attribute("pageNumber", pageNumber))
                .andExpect(model().attribute("pageSize", pageSize));

        verify(userService).getCustomersIdByManagerId(idCaptor.capture(), pageNumberCaptor.capture(), pageSizeCaptor.capture());

        assertThat(idCaptor.getValue(), is(managerId));
        assertThat(pageNumberCaptor.getValue(), is(pageNumber));
        assertThat(pageSizeCaptor.getValue(), is(pageSize));

        verify(userService).getCustomersNumberByManagerId(anyLong());
    }

    @Test
    public void getCustomersIdByManagerId_ShouldReturnCustomersId_WhenPathParameterIsValidAndQueryParametersArePresent() throws Exception {
        int pageSize = 5;

        when(userService.getCustomersIdByManagerId(anyLong(), anyInt(), anyInt())).thenReturn(customersId);
        when(userService.getCustomersNumberByManagerId(anyLong())).thenReturn(customersIdNumber);

        mockMvc.perform(get("/users/managers/2?pageNumber=0&pageSize=5"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/getCustomersIdByManagerId"))
                .andExpect(model().attribute("customersId", hasSize(customersIdListSize)))
                .andExpect(model().attribute("customersIdNumber", customersIdNumber))
                .andExpect(model().attribute("managerId", managerId))
                .andExpect(model().attribute("pageNumber", pageNumber))
                .andExpect(model().attribute("pageSize", pageSize));

        verify(userService).getCustomersIdByManagerId(idCaptor.capture(), pageNumberCaptor.capture(), pageSizeCaptor.capture());

        assertThat(idCaptor.getValue(), is(managerId));
        assertThat(pageNumberCaptor.getValue(), is(pageNumber));
        assertThat(pageSizeCaptor.getValue(), is(pageSize));

        verify(userService).getCustomersNumberByManagerId(anyLong());
    }

}