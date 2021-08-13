package dev.andrylat.carsharing.controllers;

import dev.andrylat.carsharing.models.CarBrand;
import dev.andrylat.carsharing.services.CarBrandService;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CarBrandsControllerTest {

    @Mock
    private CarBrandService carBrandService;

    @InjectMocks
    private CarBrandsController carBrandsController;

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

        when(carBrandService.getRecordsNumber()).thenReturn(recordsNumber);
        when(carBrandService.getAll(anyInt(), anyInt())).thenReturn(carBrands);

        mockMvc.perform(get("/carbrands"))
                .andExpect(status().isOk())
                .andExpect(view().name("carbrands/getAll"))
                .andExpect(model().attribute("carBrands", hasSize(carBrandsListSize)))
                .andExpect(model().attribute("recordsNumber", recordsNumber))
                .andExpect(model().attribute("pageNumber", pageNumber))
                .andExpect(model().attribute("pageSize", pageSize));

        verify(carBrandService).getRecordsNumber();
        verify(carBrandService).getAll(anyInt(), anyInt());
    }

    @Test
    public void getAll_ShouldReturnRecords_WhenQueryParametersArePresent() throws Exception {
        int pageSize = 5;

        when(carBrandService.getRecordsNumber()).thenReturn(recordsNumber);
        when(carBrandService.getAll(anyInt(), anyInt())).thenReturn(carBrands);

        mockMvc.perform(get("/carbrands?pageNumber=0&pageSize=5"))
                .andExpect(status().isOk())
                .andExpect(view().name("carbrands/getAll"))
                .andExpect(model().attribute("carBrands", hasSize(carBrandsListSize)))
                .andExpect(model().attribute("recordsNumber", recordsNumber))
                .andExpect(model().attribute("pageNumber", pageNumber))
                .andExpect(model().attribute("pageSize", pageSize));

        verify(carBrandService).getRecordsNumber();
        verify(carBrandService).getAll(anyInt(), anyInt());
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

        verify(carBrandService).getById(anyLong());
    }

    @Test
    public void showAdditionForm_ShouldShowAdditionForm_WhenUrlIsValid() throws Exception {
        CarBrand expected = new CarBrand();

        mockMvc.perform(get("/carbrands/add"))
                .andExpect(view().name("carbrands/add"))
                .andExpect(model().attribute("carBrand", expected));
    }

    @Test
    public void add_ShouldReturnAdditionForm_WhenAddedObjectIsInvalid() throws Exception {
        CarBrand expected = new CarBrand(1L, "");

        mockMvc.perform(post("/carbrands/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                                new BasicNameValuePair("id", "1"),
                                new BasicNameValuePair("name", "")
                        )))))
                .andExpect(status().isOk())
                .andExpect(view().name("carbrands/add"))
                .andExpect(model().attribute("carBrand", expected));

        verify(carBrandService, never()).add(any());
    }

    @Test
    public void add_ShouldAddObject_WhenAddedObjectIsValid() throws Exception {
        CarBrand expected = new CarBrand();
        expected.setName("nissan");

        when(carBrandService.add(any())).thenReturn(carBrand);

        mockMvc.perform(post("/carbrands/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(EntityUtils.toString(new UrlEncodedFormEntity(List.of(
                                new BasicNameValuePair("name", "nissan")
                        )))))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/carbrands/1"))
                .andExpect(model().attribute("carBrand", expected));

        verify(carBrandService).add(any());
    }

    @Test
    public void showUpdatingForm_ShouldThrowException_WhenPathParameterIsInvalid() {
        when(carBrandService.getById(anyLong())).thenThrow(MockitoException.class);

        assertThrows(NestedServletException.class,
                () -> mockMvc.perform(get("/carbrands/-1/edit")));

        verify(carBrandService).getById(anyLong());
    }

    @Test
    public void showUpdatingForm_ShouldShowUpdatingForm_WhenPathParameterIsValid() throws Exception {
        CarBrand expected = new CarBrand(1L, "nissan");

        when(carBrandService.getById(anyLong())).thenReturn(carBrand);

        mockMvc.perform(get("/carbrands/1/edit"))
                .andExpect(view().name("carbrands/edit"))
                .andExpect(model().attribute("carBrand", expected));

        verify(carBrandService).getById(anyLong());
    }

    @Test
    public void updateById_ShouldReturnUpdatingForm_WhenUpdatedObjectIsInvalid() throws Exception {
        CarBrand expected = new CarBrand(1L, "");

        mockMvc.perform(post("/carbrands/edit")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                                new BasicNameValuePair("id", "1"),
                                new BasicNameValuePair("name", "")
                        )))))
                .andExpect(status().isOk())
                .andExpect(view().name("carbrands/edit"))
                .andExpect(model().attribute("carBrand", expected));

        verify(carBrandService, never()).add(any());
    }

    @Test
    public void updateById_ShouldUpdateObject_WhenUpdatedObjectIsValid() throws Exception {
        CarBrand expected = new CarBrand(1L, "nissan");

        when(carBrandService.updateById(any())).thenReturn(true);

        mockMvc.perform(post("/carbrands/edit")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(EntityUtils.toString(new UrlEncodedFormEntity(List.of(
                                new BasicNameValuePair("id", "1"),
                                new BasicNameValuePair("name", "nissan")
                        )))))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/carbrands/1"))
                .andExpect(model().attribute("carBrand", expected));

        verify(carBrandService).updateById(any());
    }

    @Test
    public void deleteById_ShouldThrowException_WhenPathParameterIsInvalid() {
        when(carBrandService.deleteById(anyLong())).thenThrow(MockitoException.class);

        assertThrows(NestedServletException.class,
                () -> mockMvc.perform(post("/carbrands/-1/delete")));

        verify(carBrandService).deleteById(anyLong());
    }

    @Test
    public void deleteById_ShouldDeleteRecord_WhenPathParameterIsValid() throws Exception {
        when(carBrandService.deleteById(anyLong())).thenReturn(true);

        mockMvc.perform(post("/carbrands/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/carbrands"));

        verify(carBrandService).deleteById(anyLong());
    }

}