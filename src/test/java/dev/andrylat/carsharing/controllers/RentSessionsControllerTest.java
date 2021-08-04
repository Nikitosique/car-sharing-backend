package dev.andrylat.carsharing.controllers;

import dev.andrylat.carsharing.models.RentSession;
import dev.andrylat.carsharing.services.RentSessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.jupiter.MockitoExtension;
import org.postgresql.util.PGInterval;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import java.sql.SQLException;
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
class RentSessionsControllerTest {

    @Mock
    private RentSessionService rentSessionService;

    @InjectMocks
    private RentSessionsController rentSessionsController;

    @Captor
    private ArgumentCaptor<Integer> pageNumberCaptor;

    @Captor
    private ArgumentCaptor<Integer> pageSizeCaptor;

    @Captor
    private ArgumentCaptor<Long> idCaptor;

    private MockMvc mockMvc;
    private long recordsNumber;
    private int rentSessionsListSize;
    private int pageNumber;
    private RentSession rentSession;
    private List<RentSession> rentSessions;

    @BeforeEach
    public void setUp() throws SQLException {
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

        recordsNumber = 2L;
        rentSessionsListSize = rentSessions.size();
        pageNumber = 0;
        rentSession = rentSessions.get(0);

        mockMvc = MockMvcBuilders
                .standaloneSetup(rentSessionsController)
                .build();
    }

    @Test
    public void getAll_ShouldReturn404Response_WhenUrlIsInvalid() throws Exception {
        mockMvc.perform(get("/invalidUrl"))
                .andExpect(status().isNotFound());

        verify(rentSessionService, never()).getAll(anyInt(), anyInt());
    }

    @Test
    public void getAll_ShouldThrownException_WhenQueryParametersAreInvalid() {
        when(rentSessionService.getAll(anyInt(), anyInt())).thenThrow(MockitoException.class);

        assertThrows(NestedServletException.class,
                () -> mockMvc.perform(get("/rentsessions?pageNumber=-1&pageSize=-1")));

        verify(rentSessionService).getAll(anyInt(), anyInt());
    }

    @Test
    public void getAll_ShouldReturnRecords_WhenQueryParametersAreAbsent() throws Exception {
        int pageSize = 10;

        when(rentSessionService.getAll(anyInt(), anyInt())).thenReturn(rentSessions);
        when(rentSessionService.getRecordsNumber()).thenReturn(recordsNumber);

        mockMvc.perform(get("/rentsessions"))
                .andExpect(status().isOk())
                .andExpect(view().name("rentsessions/getAll"))
                .andExpect(model().attribute("rentSessions", hasSize(rentSessionsListSize)))
                .andExpect(model().attribute("recordsNumber", recordsNumber))
                .andExpect(model().attribute("pageNumber", pageNumber))
                .andExpect(model().attribute("pageSize", pageSize));

        verify(rentSessionService).getAll(pageNumberCaptor.capture(), pageSizeCaptor.capture());

        assertThat(pageNumberCaptor.getValue(), is(pageNumber));
        assertThat(pageSizeCaptor.getValue(), is(pageSize));

        verify(rentSessionService).getRecordsNumber();
    }

    @Test
    public void getAll_ShouldReturnRecords_WhenQueryParametersArePresent() throws Exception {
        int pageSize = 5;

        when(rentSessionService.getAll(anyInt(), anyInt())).thenReturn(rentSessions);
        when(rentSessionService.getRecordsNumber()).thenReturn(recordsNumber);

        mockMvc.perform(get("/rentsessions?pageNumber=0&pageSize=5"))
                .andExpect(status().isOk())
                .andExpect(view().name("rentsessions/getAll"))
                .andExpect(model().attribute("rentSessions", hasSize(rentSessionsListSize)))
                .andExpect(model().attribute("recordsNumber", recordsNumber))
                .andExpect(model().attribute("pageNumber", pageNumber))
                .andExpect(model().attribute("pageSize", pageSize));

        verify(rentSessionService).getAll(pageNumberCaptor.capture(), pageSizeCaptor.capture());

        assertThat(pageNumberCaptor.getValue(), is(pageNumber));
        assertThat(pageSizeCaptor.getValue(), is(pageSize));

        verify(rentSessionService).getRecordsNumber();
    }

    @Test
    public void getById_ShouldThrowException_WhenPathParameterIsInvalid() {
        when(rentSessionService.getById(anyLong())).thenThrow(MockitoException.class);

        assertThrows(NestedServletException.class,
                () -> mockMvc.perform(get("/rentsessions/-1")));

        verify(rentSessionService).getById(anyLong());
    }

    @Test
    public void getById_ShouldReturnRecord_WhenPathParameterIsValid() throws Exception {
        RentSession expected = new RentSession();
        expected.setId(1L);
        expected.setCustomerId(1L);
        expected.setCarId(14L);
        expected.setRentTimeInterval(new PGInterval("30 day 23 hour 59 minutes 59 second"));
        expected.setRentSessionCost(100000);

        when(rentSessionService.getById(anyLong())).thenReturn(rentSession);

        mockMvc.perform(get("/rentsessions/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("rentsessions/getById"))
                .andExpect(model().attribute("rentSession", expected));

        verify(rentSessionService).getById(idCaptor.capture());

        assertThat(idCaptor.getValue(), is(1L));
    }

}