package dev.andrylat.carsharing.controllers;

import dev.andrylat.carsharing.models.CarModel;
import dev.andrylat.carsharing.services.CarModelService;
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
class CarModelsControllerTest {

    @Mock
    private CarModelService carModelService;

    @InjectMocks
    private CarModelsController carModelsController;

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

        recordsNumber = 2L;
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

        when(carModelService.getRecordsNumber()).thenReturn(recordsNumber);
        when(carModelService.getAll(anyInt(), anyInt())).thenReturn(carModels);

        mockMvc.perform(get("/carmodels"))
                .andExpect(status().isOk())
                .andExpect(view().name("carmodels/getAll"))
                .andExpect(model().attribute("carModels", hasSize(carModelsListSize)))
                .andExpect(model().attribute("recordsNumber", recordsNumber))
                .andExpect(model().attribute("pageNumber", pageNumber))
                .andExpect(model().attribute("pageSize", pageSize));

        verify(carModelService).getRecordsNumber();
        verify(carModelService).getAll(anyInt(), anyInt());
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

        verify(carModelService).getRecordsNumber();
        verify(carModelService).getAll(anyInt(), anyInt());

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
        expected.setId(1L);
        expected.setBodyId(1L);
        expected.setBrandId(5L);
        expected.setName("Type-345");
        expected.setEngineDisplacement(1.8);
        expected.setFuelId(2L);
        expected.setGearboxType("manual");
        expected.setProductionYear(2017);

        when(carModelService.getById(anyLong())).thenReturn(carModel);

        mockMvc.perform(get("/carmodels/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("carmodels/getById"))
                .andExpect(model().attribute("carModel", expected));

        verify(carModelService).getById(anyLong());
    }

    @Test
    public void showAdditionForm_ShouldShowAdditionForm_WhenUrlIsValid() throws Exception {
        CarModel expected = new CarModel();

        mockMvc.perform(get("/carmodels/add"))
                .andExpect(view().name("carmodels/add"))
                .andExpect(model().attribute("carModel", expected));
    }

    @Test
    public void add_ShouldReturnAdditionForm_WhenAddedObjectIsInvalid() throws Exception {
        CarModel expected = new CarModel();
        expected.setBodyId(-1L);
        expected.setBrandId(-1L);
        expected.setFuelId(-1L);
        expected.setEngineDisplacement(-2.3);
        expected.setProductionYear(-1);
        expected.setName("");
        expected.setGearboxType("");

        mockMvc.perform(post("/carmodels/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                                new BasicNameValuePair("bodyId", "-1"),
                                new BasicNameValuePair("brandId", "-1"),
                                new BasicNameValuePair("fuelId", "-1"),
                                new BasicNameValuePair("engineDisplacement", "-2.3"),
                                new BasicNameValuePair("productionYear", "-1"),
                                new BasicNameValuePair("name", ""),
                                new BasicNameValuePair("gearboxType", "")
                        )))))
                .andExpect(status().isOk())
                .andExpect(view().name("carmodels/add"))
                .andExpect(model().attribute("carModel", expected));

        verify(carModelService, never()).add(any());
    }


    @Test
    public void add_ShouldAddObject_WhenAddedObjectIsValid() throws Exception {
        CarModel expected = new CarModel();
        expected.setBodyId(1);
        expected.setBrandId(5);
        expected.setName("Type-345");
        expected.setEngineDisplacement(1.8);
        expected.setFuelId(2);
        expected.setGearboxType("manual");
        expected.setProductionYear(2017);

        when(carModelService.add(any())).thenReturn(carModel);

        mockMvc.perform(post("/carmodels/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(EntityUtils.toString(new UrlEncodedFormEntity(List.of(
                                new BasicNameValuePair("bodyId", "1"),
                                new BasicNameValuePair("brandId", "5"),
                                new BasicNameValuePair("fuelId", "2"),
                                new BasicNameValuePair("engineDisplacement", "1.8"),
                                new BasicNameValuePair("productionYear", "2017"),
                                new BasicNameValuePair("name", "Type-345"),
                                new BasicNameValuePair("gearboxType", "manual")
                        )))))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/carmodels/1"))
                .andExpect(model().attribute("carModel", expected));

        verify(carModelService).add(any());
    }

    @Test
    public void showUpdatingForm_ShouldThrowException_WhenPathParameterIsInvalid() {
        when(carModelService.getById(anyLong())).thenThrow(MockitoException.class);

        assertThrows(NestedServletException.class,
                () -> mockMvc.perform(get("/carmodels/-1/edit")));

        verify(carModelService).getById(anyLong());
    }

    @Test
    public void showUpdatingForm_ShouldShowUpdatingForm_WhenPathParameterIsValid() throws Exception {
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

        mockMvc.perform(get("/carmodels/1/edit"))
                .andExpect(view().name("carmodels/edit"))
                .andExpect(model().attribute("carModel", expected));

        verify(carModelService).getById(anyLong());
    }

    @Test
    public void updateById_ShouldReturnUpdatingForm_WhenUpdatedObjectIsInvalid() throws Exception {
        CarModel expected = new CarModel();
        expected.setId(-1L);
        expected.setBodyId(-1L);
        expected.setBrandId(-1L);
        expected.setFuelId(-1L);
        expected.setEngineDisplacement(-2.3);
        expected.setProductionYear(-1);
        expected.setName("");
        expected.setGearboxType("");

        mockMvc.perform(post("/carmodels/edit")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                                new BasicNameValuePair("id", "-1"),
                                new BasicNameValuePair("bodyId", "-1"),
                                new BasicNameValuePair("brandId", "-1"),
                                new BasicNameValuePair("fuelId", "-1"),
                                new BasicNameValuePair("engineDisplacement", "-2.3"),
                                new BasicNameValuePair("productionYear", "-1"),
                                new BasicNameValuePair("name", ""),
                                new BasicNameValuePair("gearboxType", "")
                        )))))
                .andExpect(status().isOk())
                .andExpect(view().name("carmodels/edit"))
                .andExpect(model().attribute("carModel", expected));

        verify(carModelService, never()).add(any());
    }

    @Test
    public void updateById_ShouldUpdateObject_WhenUpdatedObjectIsValid() throws Exception {
        CarModel expected = new CarModel();
        expected.setId(1L);
        expected.setBodyId(1);
        expected.setBrandId(5);
        expected.setName("Type-345");
        expected.setEngineDisplacement(1.8);
        expected.setFuelId(2);
        expected.setGearboxType("manual");
        expected.setProductionYear(2017);

        when(carModelService.updateById(any())).thenReturn(true);

        mockMvc.perform(post("/carmodels/edit")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(EntityUtils.toString(new UrlEncodedFormEntity(List.of(
                                new BasicNameValuePair("id", "1"),
                                new BasicNameValuePair("bodyId", "1"),
                                new BasicNameValuePair("brandId", "5"),
                                new BasicNameValuePair("fuelId", "2"),
                                new BasicNameValuePair("engineDisplacement", "1.8"),
                                new BasicNameValuePair("productionYear", "2017"),
                                new BasicNameValuePair("name", "Type-345"),
                                new BasicNameValuePair("gearboxType", "manual")
                        )))))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/carmodels/1"))
                .andExpect(model().attribute("carModel", expected));

        verify(carModelService).updateById(any());
    }

    @Test
    public void deleteById_ShouldThrowException_WhenPathParameterIsInvalid() {
        when(carModelService.deleteById(anyLong())).thenThrow(MockitoException.class);

        assertThrows(NestedServletException.class,
                () -> mockMvc.perform(post("/carmodels/-1/delete")));

        verify(carModelService).deleteById(anyLong());
    }

    @Test
    public void deleteById_ShouldDeleteRecord_WhenPathParameterIsValid() throws Exception {
        when(carModelService.deleteById(anyLong())).thenReturn(true);

        mockMvc.perform(post("/carmodels/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/carmodels"));

        verify(carModelService).deleteById(anyLong());
    }

}