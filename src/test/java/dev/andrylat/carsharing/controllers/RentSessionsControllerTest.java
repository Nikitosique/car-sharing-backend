package dev.andrylat.carsharing.controllers;

import dev.andrylat.carsharing.models.BodyType;
import dev.andrylat.carsharing.models.RentSession;
import dev.andrylat.carsharing.services.RentSessionService;
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
import org.postgresql.util.PGInterval;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import java.sql.SQLException;
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
class RentSessionsControllerTest {

    @Mock
    private RentSessionService rentSessionService;

    @InjectMocks
    private RentSessionsController rentSessionsController;

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

        when(rentSessionService.getRecordsNumber()).thenReturn(recordsNumber);
        when(rentSessionService.getAll(anyInt(), anyInt())).thenReturn(rentSessions);

        mockMvc.perform(get("/rentsessions"))
                .andExpect(status().isOk())
                .andExpect(view().name("rentsessions/getAll"))
                .andExpect(model().attribute("rentSessions", hasSize(rentSessionsListSize)))
                .andExpect(model().attribute("recordsNumber", recordsNumber))
                .andExpect(model().attribute("pageNumber", pageNumber))
                .andExpect(model().attribute("pageSize", pageSize));

        verify(rentSessionService).getRecordsNumber();
        verify(rentSessionService).getAll(anyInt(), anyInt());
    }

    @Test
    public void getAll_ShouldReturnRecords_WhenQueryParametersArePresent() throws Exception {
        int pageSize = 5;

        when(rentSessionService.getRecordsNumber()).thenReturn(recordsNumber);
        when(rentSessionService.getAll(anyInt(), anyInt())).thenReturn(rentSessions);

        mockMvc.perform(get("/rentsessions?pageNumber=0&pageSize=5"))
                .andExpect(status().isOk())
                .andExpect(view().name("rentsessions/getAll"))
                .andExpect(model().attribute("rentSessions", hasSize(rentSessionsListSize)))
                .andExpect(model().attribute("recordsNumber", recordsNumber))
                .andExpect(model().attribute("pageNumber", pageNumber))
                .andExpect(model().attribute("pageSize", pageSize));

        verify(rentSessionService).getRecordsNumber();
        verify(rentSessionService).getAll(anyInt(), anyInt());
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

        verify(rentSessionService).getById(anyLong());
    }

    @Test
    public void showAdditionForm_ShouldShowAdditionForm_WhenUrlIsValid() throws Exception {
        RentSession expected = new RentSession();

        mockMvc.perform(get("/rentsessions/add"))
                .andExpect(view().name("rentsessions/add"))
                .andExpect(model().attribute("rentSession", expected));
    }

    @Test
    public void add_ShouldReturnAdditionForm_WhenAddedObjectIsInvalid() throws Exception {
        RentSession expected = new RentSession();
        expected.setCustomerId(-1L);
        expected.setCarId(-1L);
        expected.setRentTimeInterval(null);
        expected.setRentSessionCost(-1);

        mockMvc.perform(post("/rentsessions/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                                new BasicNameValuePair("customerId", "-1"),
                                new BasicNameValuePair("carId", "-1"),
                                new BasicNameValuePair("rentTimeInterval", null),
                                new BasicNameValuePair("rentSessionCost", "-1")
                        )))))
                .andExpect(status().isOk())
                .andExpect(view().name("rentsessions/add"))
                .andExpect(model().attribute("rentSession", expected));

        verify(rentSessionService, never()).add(any());
    }

    @Test
    public void add_ShouldAddObject_WhenAddedObjectIsValid() throws Exception {
        RentSession expected = new RentSession();
        expected.setCustomerId(1L);
        expected.setCarId(14L);
        expected.setRentTimeInterval(new PGInterval("30 day 23 hour 59 minutes 59 second"));
        expected.setRentSessionCost(100000);

        when(rentSessionService.add(any())).thenReturn(rentSession);

        mockMvc.perform(post("/rentsessions/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(EntityUtils.toString(new UrlEncodedFormEntity(List.of(
                                new BasicNameValuePair("customerId", "1"),
                                new BasicNameValuePair("carId", "14"),
                                new BasicNameValuePair("rentTimeInterval", "30 day 23 hour 59 minutes 59 second"),
                                new BasicNameValuePair("rentSessionCost", "100000")
                        )))))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/rentsessions/1"))
                .andExpect(model().attribute("rentSession", expected));

        verify(rentSessionService).add(any());
    }

    @Test
    public void showUpdatingForm_ShouldThrowException_WhenPathParameterIsInvalid() {
        when(rentSessionService.getById(anyLong())).thenThrow(MockitoException.class);

        assertThrows(NestedServletException.class,
                () -> mockMvc.perform(get("/rentsessions/-1/edit")));

        verify(rentSessionService).getById(anyLong());
    }

    @Test
    public void showUpdatingForm_ShouldShowUpdatingForm_WhenPathParameterIsValid() throws Exception {
        RentSession expected = new RentSession();
        expected.setId(1L);
        expected.setCustomerId(1L);
        expected.setCarId(14L);
        expected.setRentTimeInterval(new PGInterval("30 day 23 hour 59 minutes 59 second"));
        expected.setRentSessionCost(100000);

        when(rentSessionService.getById(anyLong())).thenReturn(rentSession);

        mockMvc.perform(get("/rentsessions/1/edit"))
                .andExpect(view().name("rentsessions/edit"))
                .andExpect(model().attribute("rentSession", expected));

        verify(rentSessionService).getById(anyLong());
    }

    @Test
    public void updateById_ShouldReturnUpdatingForm_WhenUpdatedObjectIsInvalid() throws Exception {
        RentSession expected = new RentSession();
        expected.setId(-1L);
        expected.setCustomerId(-1L);
        expected.setCarId(-1L);
        expected.setRentTimeInterval(null);
        expected.setRentSessionCost(-1);

        mockMvc.perform(post("/rentsessions/edit")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                                new BasicNameValuePair("id", "-1"),
                                new BasicNameValuePair("customerId", "-1"),
                                new BasicNameValuePair("carId", "-1"),
                                new BasicNameValuePair("rentTimeInterval", null),
                                new BasicNameValuePair("rentSessionCost", "-1")
                        )))))
                .andExpect(status().isOk())
                .andExpect(view().name("rentsessions/edit"))
                .andExpect(model().attribute("rentSession", expected));

        verify(rentSessionService, never()).add(any());
    }

    @Test
    public void updateById_ShouldUpdateObject_WhenUpdatedObjectIsValid() throws Exception {
        RentSession expected = new RentSession();
        expected.setId(1L);
        expected.setCustomerId(1L);
        expected.setCarId(14L);
        expected.setRentTimeInterval(new PGInterval("30 day 23 hour 59 minutes 59 second"));
        expected.setRentSessionCost(100000);

        when(rentSessionService.updateById(any())).thenReturn(true);

        mockMvc.perform(post("/rentsessions/edit")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(EntityUtils.toString(new UrlEncodedFormEntity(List.of(new BasicNameValuePair("customerId", "1"),
                                new BasicNameValuePair("id", "1"),
                                new BasicNameValuePair("customerId", "1"),
                                new BasicNameValuePair("carId", "14"),
                                new BasicNameValuePair("rentTimeInterval", "30 day 23 hour 59 minutes 59 second"),
                                new BasicNameValuePair("rentSessionCost", "100000")
                        )))))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/rentsessions/1"))
                .andExpect(model().attribute("rentSession", expected));

        verify(rentSessionService).updateById(any());
    }

    @Test
    public void deleteById_ShouldThrowException_WhenPathParameterIsInvalid() {
        when(rentSessionService.deleteById(anyLong())).thenThrow(MockitoException.class);

        assertThrows(NestedServletException.class,
                () -> mockMvc.perform(post("/rentsessions/-1/delete")));

        verify(rentSessionService).deleteById(anyLong());
    }

    @Test
    public void deleteById_ShouldDeleteRecord_WhenPathParameterIsValid() throws Exception {
        when(rentSessionService.deleteById(anyLong())).thenReturn(true);

        mockMvc.perform(post("/rentsessions/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/rentsessions"));

        verify(rentSessionService).deleteById(anyLong());
    }

}