package dev.andrylat.carsharing.controllers;

import dev.andrylat.carsharing.models.CarBrand;
import dev.andrylat.carsharing.services.CarBrandService;
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
class CarBrandsControllerTest {

    @Mock
    private CarBrandService carBrandService;

    @InjectMocks
    private CarBrandsController carBrandsController;

    @Captor
    private ArgumentCaptor<Integer> pageNumberCaptor;

    @Captor
    private ArgumentCaptor<Integer> pageSizeCaptor;

    @Captor
    private ArgumentCaptor<Long> idCaptor;

    private MockMvc mockMvc;
    private long recordsNumber;
    private int carBrandsListSize;
    private int pageNumber;
    private CarBrand carBrand;
    private List<CarBrand> carBrands;

    @BeforeEach
    public void setUp() {
        carBrands = new ArrayList<>();
        carBrands.add(new CarBrand(1, "nissan"));
        carBrands.add(new CarBrand(2, "audi"));

        recordsNumber = 2L;
        carBrandsListSize = carBrands.size();
        pageNumber = 0;
        carBrand = carBrands.get(0);

        mockMvc = MockMvcBuilders
                .standaloneSetup(carBrandsController)
                .build();
    }

    @Test
    public void getAll_ShouldReturn404Response_WhenUrlIsInvalid() throws Exception {
        mockMvc.perform(get("/invalidUrl"))
                .andExpect(status().isNotFound());

        verify(carBrandService, never()).getAll(anyInt(), anyInt());
    }

    @Test
    public void getAll_ShouldThrownException_WhenQueryParametersAreInvalid() {
        when(carBrandService.getAll(anyInt(), anyInt())).thenThrow(MockitoException.class);

        assertThrows(NestedServletException.class,
                () -> mockMvc.perform(get("/carbrands?pageNumber=-1&pageSize=-1")));

        verify(carBrandService).getAll(anyInt(), anyInt());
    }

    @Test
    public void getAll_ShouldReturnRecords_WhenQueryParametersAreAbsent() throws Exception {
        int pageSize = 10;

        when(carBrandService.getAll(anyInt(), anyInt())).thenReturn(carBrands);
        when(carBrandService.getRecordsNumber()).thenReturn(recordsNumber);

        mockMvc.perform(get("/carbrands"))
                .andExpect(status().isOk())
                .andExpect(view().name("carbrands/getAll"))
                .andExpect(model().attribute("carBrands", hasSize(carBrandsListSize)))
                .andExpect(model().attribute("recordsNumber", recordsNumber))
                .andExpect(model().attribute("pageNumber", pageNumber))
                .andExpect(model().attribute("pageSize", pageSize));

        verify(carBrandService).getAll(pageNumberCaptor.capture(), pageSizeCaptor.capture());

        assertThat(pageNumberCaptor.getValue(), is(pageNumber));
        assertThat(pageSizeCaptor.getValue(), is(pageSize));

        verify(carBrandService).getRecordsNumber();
    }

    @Test
    public void getAll_ShouldReturnRecords_WhenQueryParametersArePresent() throws Exception {
        int pageSize = 5;

        when(carBrandService.getAll(anyInt(), anyInt())).thenReturn(carBrands);
        when(carBrandService.getRecordsNumber()).thenReturn(recordsNumber);

        mockMvc.perform(get("/carbrands?pageNumber=0&pageSize=5"))
                .andExpect(status().isOk())
                .andExpect(view().name("carbrands/getAll"))
                .andExpect(model().attribute("carBrands", hasSize(carBrandsListSize)))
                .andExpect(model().attribute("recordsNumber", recordsNumber))
                .andExpect(model().attribute("pageNumber", pageNumber))
                .andExpect(model().attribute("pageSize", pageSize));

        verify(carBrandService).getAll(pageNumberCaptor.capture(), pageSizeCaptor.capture());

        assertThat(pageNumberCaptor.getValue(), is(pageNumber));
        assertThat(pageSizeCaptor.getValue(), is(pageSize));

        verify(carBrandService).getRecordsNumber();
    }

    @Test
    public void getById_ShouldThrowException_WhenPathParameterIsInvalid() {
        when(carBrandService.getById(anyLong())).thenThrow(MockitoException.class);

        assertThrows(NestedServletException.class,
                () -> mockMvc.perform(get("/carbrands/-1")));

        verify(carBrandService).getById(anyLong());
    }

    @Test
    public void getById_ShouldReturnRecord_WhenPathParameterIsValid() throws Exception {
        CarBrand expected = new CarBrand(1, "nissan");

        when(carBrandService.getById(anyLong())).thenReturn(carBrand);

        mockMvc.perform(get("/carbrands/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("carbrands/getById"))
                .andExpect(model().attribute("carBrand", expected));

        verify(carBrandService).getById(idCaptor.capture());

        assertThat(idCaptor.getValue(), is(1L));
    }

}