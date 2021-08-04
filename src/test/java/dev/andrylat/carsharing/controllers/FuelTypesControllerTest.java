package dev.andrylat.carsharing.controllers;

import dev.andrylat.carsharing.models.FuelType;
import dev.andrylat.carsharing.services.FuelTypeService;
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
class FuelTypesControllerTest {

    @Mock
    private FuelTypeService fuelTypeService;

    @InjectMocks
    private FuelTypesController fuelTypesController;

    @Captor
    private ArgumentCaptor<Integer> pageNumberCaptor;

    @Captor
    private ArgumentCaptor<Integer> pageSizeCaptor;

    @Captor
    private ArgumentCaptor<Long> idCaptor;

    private MockMvc mockMvc;
    private long recordsNumber;
    private int fuelTypesListSize;
    private int pageNumber;
    private FuelType fuelType;
    private List<FuelType> fuelTypes;

    @BeforeEach
    public void setUp() {
        fuelTypes = new ArrayList<>();
        fuelTypes.add(new FuelType(1L, "gasoline"));
        fuelTypes.add(new FuelType(2L, "electricity"));

        recordsNumber = 2L;
        fuelTypesListSize = fuelTypes.size();
        pageNumber = 0;
        fuelType = fuelTypes.get(0);

        mockMvc = MockMvcBuilders
                .standaloneSetup(fuelTypesController)
                .build();
    }

    @Test
    public void getAll_ShouldReturn404Response_WhenUrlIsInvalid() throws Exception {
        mockMvc.perform(get("/invalidUrl"))
                .andExpect(status().isNotFound());

        verify(fuelTypeService, never()).getAll(anyInt(), anyInt());
    }

    @Test
    public void getAll_ShouldThrownException_WhenQueryParametersAreInvalid() {
        when(fuelTypeService.getAll(anyInt(), anyInt())).thenThrow(MockitoException.class);

        assertThrows(NestedServletException.class,
                () -> mockMvc.perform(get("/fueltypes?pageNumber=-1&pageSize=-1")));

        verify(fuelTypeService).getAll(anyInt(), anyInt());
    }

    @Test
    public void getAll_ShouldReturnRecords_WhenQueryParametersAreAbsent() throws Exception {
        int pageSize = 10;

        when(fuelTypeService.getAll(anyInt(), anyInt())).thenReturn(fuelTypes);
        when(fuelTypeService.getRecordsNumber()).thenReturn(recordsNumber);

        mockMvc.perform(get("/fueltypes"))
                .andExpect(status().isOk())
                .andExpect(view().name("fueltypes/getAll"))
                .andExpect(model().attribute("fuelTypes", hasSize(fuelTypesListSize)))
                .andExpect(model().attribute("recordsNumber", recordsNumber))
                .andExpect(model().attribute("pageNumber", pageNumber))
                .andExpect(model().attribute("pageSize", pageSize));

        verify(fuelTypeService).getAll(pageNumberCaptor.capture(), pageSizeCaptor.capture());

        assertThat(pageNumberCaptor.getValue(), is(pageNumber));
        assertThat(pageSizeCaptor.getValue(), is(pageSize));

        verify(fuelTypeService).getRecordsNumber();
    }

    @Test
    public void getAll_ShouldReturnRecords_WhenQueryParametersArePresent() throws Exception {
        int pageSize = 5;

        when(fuelTypeService.getAll(anyInt(), anyInt())).thenReturn(fuelTypes);
        when(fuelTypeService.getRecordsNumber()).thenReturn(recordsNumber);

        mockMvc.perform(get("/fueltypes?pageNumber=0&pageSize=5"))
                .andExpect(status().isOk())
                .andExpect(view().name("fueltypes/getAll"))
                .andExpect(model().attribute("fuelTypes", hasSize(fuelTypesListSize)))
                .andExpect(model().attribute("recordsNumber", recordsNumber))
                .andExpect(model().attribute("pageNumber", pageNumber))
                .andExpect(model().attribute("pageSize", pageSize));

        verify(fuelTypeService).getAll(pageNumberCaptor.capture(), pageSizeCaptor.capture());

        assertThat(pageNumberCaptor.getValue(), is(pageNumber));
        assertThat(pageSizeCaptor.getValue(), is(pageSize));

        verify(fuelTypeService).getRecordsNumber();
    }

    @Test
    public void getById_ShouldThrowException_WhenPathParameterIsInvalid() {
        when(fuelTypeService.getById(anyLong())).thenThrow(MockitoException.class);

        assertThrows(NestedServletException.class,
                () -> mockMvc.perform(get("/fueltypes/-1")));

        verify(fuelTypeService).getById(anyLong());
    }

    @Test
    public void getById_ShouldReturnRecord_WhenPathParameterIsValid() throws Exception {
        FuelType expected = new FuelType(1L, "gasoline");

        when(fuelTypeService.getById(anyLong())).thenReturn(fuelType);

        mockMvc.perform(get("/fueltypes/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("fueltypes/getById"))
                .andExpect(model().attribute("fuelType", expected));

        verify(fuelTypeService).getById(idCaptor.capture());

        assertThat(idCaptor.getValue(), is(1L));
    }

}