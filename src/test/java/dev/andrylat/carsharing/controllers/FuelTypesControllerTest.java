package dev.andrylat.carsharing.controllers;

import dev.andrylat.carsharing.models.FuelType;
import dev.andrylat.carsharing.services.FuelTypeService;
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
class FuelTypesControllerTest {

    @Mock
    private FuelTypeService fuelTypeService;

    @InjectMocks
    private FuelTypesController fuelTypesController;

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

        when(fuelTypeService.getRecordsNumber()).thenReturn(recordsNumber);
        when(fuelTypeService.getAll(anyInt(), anyInt())).thenReturn(fuelTypes);

        mockMvc.perform(get("/fueltypes"))
                .andExpect(status().isOk())
                .andExpect(view().name("fueltypes/getAll"))
                .andExpect(model().attribute("fuelTypes", hasSize(fuelTypesListSize)))
                .andExpect(model().attribute("recordsNumber", recordsNumber))
                .andExpect(model().attribute("pageNumber", pageNumber))
                .andExpect(model().attribute("pageSize", pageSize));

        verify(fuelTypeService).getRecordsNumber();
        verify(fuelTypeService).getAll(anyInt(), anyInt());
    }

    @Test
    public void getAll_ShouldReturnRecords_WhenQueryParametersArePresent() throws Exception {
        int pageSize = 5;

        when(fuelTypeService.getRecordsNumber()).thenReturn(recordsNumber);
        when(fuelTypeService.getAll(anyInt(), anyInt())).thenReturn(fuelTypes);

        mockMvc.perform(get("/fueltypes?pageNumber=0&pageSize=5"))
                .andExpect(status().isOk())
                .andExpect(view().name("fueltypes/getAll"))
                .andExpect(model().attribute("fuelTypes", hasSize(fuelTypesListSize)))
                .andExpect(model().attribute("recordsNumber", recordsNumber))
                .andExpect(model().attribute("pageNumber", pageNumber))
                .andExpect(model().attribute("pageSize", pageSize));

        verify(fuelTypeService).getRecordsNumber();
        verify(fuelTypeService).getAll(anyInt(), anyInt());
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

        verify(fuelTypeService).getById(anyLong());
    }

    @Test
    public void showAdditionForm_ShouldShowAdditionForm_WhenUrlIsValid() throws Exception {
        FuelType expected = new FuelType();

        mockMvc.perform(get("/fueltypes/add"))
                .andExpect(view().name("fueltypes/add"))
                .andExpect(model().attribute("fuelType", expected));
    }

    @Test
    public void add_ShouldReturnAdditionForm_WhenAddedObjectIsInvalid() throws Exception {
        FuelType expected = new FuelType(1L, "");

        mockMvc.perform(post("/fueltypes/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                                new BasicNameValuePair("id", "1"),
                                new BasicNameValuePair("name", "")
                        )))))
                .andExpect(status().isOk())
                .andExpect(view().name("fueltypes/add"))
                .andExpect(model().attribute("fuelType", expected));

        verify(fuelTypeService, never()).add(any());
    }

    @Test
    public void add_ShouldAddObject_WhenAddedObjectIsValid() throws Exception {
        FuelType expected = new FuelType();
        expected.setName("gasoline");

        when(fuelTypeService.add(any())).thenReturn(fuelType);

        mockMvc.perform(post("/fueltypes/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(EntityUtils.toString(new UrlEncodedFormEntity(List.of(
                                new BasicNameValuePair("name", "gasoline")
                        )))))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/fueltypes/1"))
                .andExpect(model().attribute("fuelType", expected));

        verify(fuelTypeService).add(any());
    }

    @Test
    public void showUpdatingForm_ShouldThrowException_WhenPathParameterIsInvalid() {
        when(fuelTypeService.getById(anyLong())).thenThrow(MockitoException.class);

        assertThrows(NestedServletException.class,
                () -> mockMvc.perform(get("/fueltypes/-1/edit")));

        verify(fuelTypeService).getById(anyLong());
    }

    @Test
    public void showUpdatingForm_ShouldShowUpdatingForm_WhenPathParameterIsValid() throws Exception {
        when(fuelTypeService.getById(anyLong())).thenReturn(fuelType);

        FuelType expected = new FuelType(1L, "gasoline");

        mockMvc.perform(get("/fueltypes/1/edit"))
                .andExpect(view().name("fueltypes/edit"))
                .andExpect(model().attribute("fuelType", expected));

        verify(fuelTypeService).getById(anyLong());
    }

    @Test
    public void updateById_ShouldReturnUpdatingForm_WhenUpdatedObjectIsInvalid() throws Exception {
        FuelType expected = new FuelType(1L, "");

        mockMvc.perform(post("/fueltypes/edit")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                                new BasicNameValuePair("id", "1"),
                                new BasicNameValuePair("name", "")
                        )))))
                .andExpect(status().isOk())
                .andExpect(view().name("fueltypes/edit"))
                .andExpect(model().attribute("fuelType", expected));

        verify(fuelTypeService, never()).add(any());
    }

    @Test
    public void updateById_ShouldUpdateObject_WhenUpdatedObjectIsValid() throws Exception {
        FuelType expected = new FuelType(1L, "gasoline");

        when(fuelTypeService.updateById(any())).thenReturn(true);

        mockMvc.perform(post("/fueltypes/edit")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(EntityUtils.toString(new UrlEncodedFormEntity(List.of(
                                new BasicNameValuePair("id", "1"),
                                new BasicNameValuePair("name", "gasoline")
                        )))))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/fueltypes/1"))
                .andExpect(model().attribute("fuelType", expected));

        verify(fuelTypeService).updateById(any());
    }

    @Test
    public void deleteById_ShouldThrowException_WhenPathParameterIsInvalid() {
        when(fuelTypeService.deleteById(anyLong())).thenThrow(MockitoException.class);

        assertThrows(NestedServletException.class,
                () -> mockMvc.perform(post("/fueltypes/-1/delete")));

        verify(fuelTypeService).deleteById(anyLong());
    }

    @Test
    public void deleteById_ShouldDeleteRecord_WhenPathParameterIsValid() throws Exception {
        when(fuelTypeService.deleteById(anyLong())).thenReturn(true);

        mockMvc.perform(post("/fueltypes/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/fueltypes"));

        verify(fuelTypeService).deleteById(anyLong());
    }

}