package dev.andrylat.carsharing.controllers;

import dev.andrylat.carsharing.models.CarModel;
import dev.andrylat.carsharing.services.CarModelService;
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
class CarModelsControllerTest {

    @Mock
    private CarModelService carModelService;

    @InjectMocks
    private CarModelsController carModelsController;

    @Captor
    private ArgumentCaptor<Integer> pageNumberCaptor;

    @Captor
    private ArgumentCaptor<Integer> pageSizeCaptor;

    @Captor
    private ArgumentCaptor<Long> idCaptor;

    private MockMvc mockMvc;
    private long recordsNumber;
    private int carModelsListSize;
    private int pageNumber;
    private CarModel carModel;
    private List<CarModel> carModels;

    @BeforeEach
    public void setUp() {
        carModels = new ArrayList<>();

        CarModel temporal = new CarModel();
        temporal.setId(1);
        temporal.setBodyId(1);
        temporal.setBrandId(5);
        temporal.setName("Type-345");
        temporal.setEngineDisplacement(1.8);
        temporal.setFuelId(2);
        temporal.setGearboxType("manual");
        temporal.setProductionYear(2017);
        carModels.add(temporal);

        temporal = new CarModel();
        temporal.setId(2);
        temporal.setBodyId(2);
        temporal.setBrandId(3);
        temporal.setName("Model-CE1");
        temporal.setEngineDisplacement(2.0);
        temporal.setFuelId(1);
        temporal.setGearboxType("automatic");
        temporal.setProductionYear(2020);
        carModels.add(carModel);

        recordsNumber = 2;
        carModelsListSize = carModels.size();
        pageNumber = 0;
        carModel = carModels.get(0);

        mockMvc = MockMvcBuilders
                .standaloneSetup(carModelsController)
                .build();
    }

    @Test
    public void getAll_ShouldReturn404Response_WhenUrlIsInvalid() throws Exception {
        mockMvc.perform(get("/invalidUrl"))
                .andExpect(status().isNotFound());

        verify(carModelService, never()).getAll(anyInt(), anyInt());
    }

    @Test
    public void getAll_ShouldThrownException_WhenQueryParametersAreInvalid() {
        when(carModelService.getAll(anyInt(), anyInt())).thenThrow(MockitoException.class);

        assertThrows(NestedServletException.class,
                () -> mockMvc.perform(get("/carmodels?pageNumber=-1&pageSize=-1")));

        verify(carModelService).getAll(anyInt(), anyInt());
    }

    @Test
    public void getAll_ShouldReturnRecords_WhenQueryParametersAreAbsent() throws Exception {
        int pageSize = 10;

        when(carModelService.getAll(anyInt(), anyInt())).thenReturn(carModels);
        when(carModelService.getRecordsNumber()).thenReturn(recordsNumber);

        mockMvc.perform(get("/carmodels"))
                .andExpect(status().isOk())
                .andExpect(view().name("carmodels/getAll"))
                .andExpect(model().attribute("carModels", hasSize(carModelsListSize)))
                .andExpect(model().attribute("recordsNumber", recordsNumber))
                .andExpect(model().attribute("pageNumber", pageNumber))
                .andExpect(model().attribute("pageSize", pageSize));

        verify(carModelService).getAll(pageNumberCaptor.capture(), pageSizeCaptor.capture());

        assertThat(pageNumberCaptor.getValue(), is(pageNumber));
        assertThat(pageSizeCaptor.getValue(), is(pageSize));

        verify(carModelService).getRecordsNumber();
    }

    @Test
    public void getAll_ShouldReturnRecords_WhenQueryParametersArePresent() throws Exception {
        int pageSize = 5;

        when(carModelService.getAll(anyInt(), anyInt())).thenReturn(carModels);
        when(carModelService.getRecordsNumber()).thenReturn(recordsNumber);

        mockMvc.perform(get("/carmodels?pageNumber=0&pageSize=5"))
                .andExpect(status().isOk())
                .andExpect(view().name("carmodels/getAll"))
                .andExpect(model().attribute("carModels", hasSize(carModelsListSize)))
                .andExpect(model().attribute("recordsNumber", recordsNumber))
                .andExpect(model().attribute("pageNumber", pageNumber))
                .andExpect(model().attribute("pageSize", pageSize));

        verify(carModelService).getAll(pageNumberCaptor.capture(), pageSizeCaptor.capture());

        assertThat(pageNumberCaptor.getValue(), is(pageNumber));
        assertThat(pageSizeCaptor.getValue(), is(pageSize));

        verify(carModelService).getRecordsNumber();
    }

    @Test
    public void getById_ShouldThrowException_WhenPathParameterIsInvalid() {
        when(carModelService.getById(anyLong())).thenThrow(MockitoException.class);

        assertThrows(NestedServletException.class,
                () -> mockMvc.perform(get("/carmodels/-1")));

        verify(carModelService).getById(anyLong());
    }

    @Test
    public void getById_ShouldReturnRecord_WhenPathParameterIsValid() throws Exception {
        CarModel expected = new CarModel();
        expected.setId(1);
        expected.setBodyId(1);
        expected.setBrandId(5);
        expected.setName("Type-345");
        expected.setEngineDisplacement(1.8);
        expected.setFuelId(2);
        expected.setGearboxType("manual");
        expected.setProductionYear(2017);

        when(carModelService.getById(anyLong())).thenReturn(carModel);

        mockMvc.perform(get("/carmodels/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("carmodels/getById"))
                .andExpect(model().attribute("carModel", expected));

        verify(carModelService).getById(idCaptor.capture());

        assertThat(idCaptor.getValue(), is(1L));
    }

}