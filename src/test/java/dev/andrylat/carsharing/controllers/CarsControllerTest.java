package dev.andrylat.carsharing.controllers;

import dev.andrylat.carsharing.models.Car;
import dev.andrylat.carsharing.services.CarService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@ExtendWith(MockitoExtension.class)
class CarsControllerTest {
    @Mock
    private CarService carService;

    @InjectMocks
    private CarsController carsController;

    private MockMvc mockMvc;
    private long recordsNumber;
    private int carsListSize;
    private int pageNumber;
    private Car car;
    private List<Car> cars;

    @BeforeEach
    void setUp() {
        cars = new ArrayList<>();

        Car temporal = new Car();
        temporal.setId(1);
        temporal.setModelId(7);
        temporal.setRegistrationPlate("AA1234BB");
        temporal.setRentCostPerMin(30);
        temporal.setColor("red");
        temporal.setPhoto("car_1.png");
        cars.add(temporal);

        temporal = new Car();
        temporal.setId(2);
        temporal.setModelId(32);
        temporal.setRegistrationPlate("QZ1WD403");
        temporal.setRentCostPerMin(20);
        temporal.setColor("white");
        temporal.setPhoto("car_2.png");
        cars.add(temporal);

        car = cars.get(0);

        recordsNumber = 2L;
        carsListSize = cars.size();
        pageNumber = 0;

        mockMvc = MockMvcBuilders
                .standaloneSetup(carsController)
                .build();
    }

    @Test
    public void getAll_ShouldReturn404Response_WhenUrlIsInvalid() throws Exception {
        mockMvc.perform(get("/invalidUrl"))
                .andExpect(status().isNotFound());

        verify(carService, never()).getAll(anyInt(), anyInt());
    }

    @Test
    public void getAll_ShouldThrownException_WhenQueryParametersAreInvalid() {
        when(carService.getAll(anyInt(), anyInt())).thenThrow(MockitoException.class);

        assertThrows(NestedServletException.class,
                () -> mockMvc.perform(get("/cars?pageNumber=-1&pageSize=-1")));

        verify(carService).getAll(anyInt(), anyInt());
    }

    @Test
    public void getAll_ShouldReturnRecords_WhenQueryParametersAreAbsent() throws Exception {
        int pageSize = 10;

        when(carService.getRecordsNumber()).thenReturn(recordsNumber);
        when(carService.getAll(anyInt(), anyInt())).thenReturn(cars);

        mockMvc.perform(get("/cars"))
                .andExpect(status().isOk())
                .andExpect(view().name("cars/getAll"))
                .andExpect(model().attribute("cars", hasSize(carsListSize)))
                .andExpect(model().attribute("recordsNumber", recordsNumber))
                .andExpect(model().attribute("pageNumber", pageNumber))
                .andExpect(model().attribute("pageSize", pageSize));

        verify(carService).getRecordsNumber();
        verify(carService).getAll(anyInt(), anyInt());
    }

    @Test
    public void getAll_ShouldReturnRecords_WhenQueryParametersArePresent() throws Exception {
        int pageSize = 5;

        when(carService.getAll(anyInt(), anyInt())).thenReturn(cars);
        when(carService.getRecordsNumber()).thenReturn(recordsNumber);

        mockMvc.perform(get("/cars?pageNumber=0&pageSize=5"))
                .andExpect(status().isOk())
                .andExpect(view().name("cars/getAll"))
                .andExpect(model().attribute("cars", hasSize(carsListSize)))
                .andExpect(model().attribute("recordsNumber", recordsNumber))
                .andExpect(model().attribute("pageNumber", pageNumber))
                .andExpect(model().attribute("pageSize", pageSize));

        verify(carService).getRecordsNumber();
        verify(carService).getAll(anyInt(), anyInt());
    }

    @Test
    public void getById_ShouldThrowException_WhenPathParameterIsInvalid() {
        when(carService.getById(anyLong())).thenThrow(MockitoException.class);

        assertThrows(NestedServletException.class,
                () -> mockMvc.perform(get("/cars/-1")));

        verify(carService).getById(anyLong());
    }

    @Test
    public void getById_ShouldReturnRecord_WhenPathParameterIsValid() throws Exception {
        Car expected = new Car();
        expected.setId(1L);
        expected.setModelId(7L);
        expected.setRegistrationPlate("AA1234BB");
        expected.setRentCostPerMin(30);
        expected.setColor("red");
        expected.setPhoto("car_1.png");

        when(carService.getById(anyLong())).thenReturn(car);

        mockMvc.perform(get("/cars/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("cars/getById"))
                .andExpect(model().attribute("car", expected));

        verify(carService).getById(anyLong());
    }

    @Test
    public void showAdditionForm_ShouldShowAdditionForm_WhenUrlIsValid() throws Exception {
        Car expected = new Car();

        mockMvc.perform(get("/cars/add"))
                .andExpect(view().name("cars/add"))
                .andExpect(model().attribute("car", expected));
    }

    @Test
    public void add_ShouldReturnAdditionForm_WhenAddedObjectIsInvalid() throws Exception {
        Car expected = new Car();
        expected.setModelId(-1L);
        expected.setRegistrationPlate("");
        expected.setRentCostPerMin(-1);
        expected.setColor("");
        expected.setPhoto("");

        mockMvc.perform(post("/cars/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                                new BasicNameValuePair("modelId", "-1"),
                                new BasicNameValuePair("registrationPlate", ""),
                                new BasicNameValuePair("rentCostPerMin", "-1"),
                                new BasicNameValuePair("color", ""),
                                new BasicNameValuePair("photo", "")
                        )))))
                .andExpect(status().isOk())
                .andExpect(view().name("cars/add"))
                .andExpect(model().attribute("car", expected));

        verify(carService, never()).add(any());
    }

    @Test
    public void add_ShouldAddObject_WhenAddedObjectIsValid() throws Exception {
        Car expected = new Car();
        expected.setModelId(7L);
        expected.setRegistrationPlate("AA1234BB");
        expected.setRentCostPerMin(30);
        expected.setColor("red");
        expected.setPhoto("car_1.png");

        when(carService.add(any())).thenReturn(car);

        mockMvc.perform(post("/cars/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(EntityUtils.toString(new UrlEncodedFormEntity(List.of(
                                new BasicNameValuePair("modelId", "7"),
                                new BasicNameValuePair("registrationPlate", "AA1234BB"),
                                new BasicNameValuePair("rentCostPerMin", "30"),
                                new BasicNameValuePair("color", "red"),
                                new BasicNameValuePair("photo", "car_1.png")
                        )))))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/cars/1"))
                .andExpect(model().attribute("car", expected));

        verify(carService).add(any());
    }

    @Test
    public void showUpdatingForm_ShouldThrowException_WhenPathParameterIsInvalid() {
        when(carService.getById(anyLong())).thenThrow(MockitoException.class);

        assertThrows(NestedServletException.class,
                () -> mockMvc.perform(get("/cars/-1/edit")));

        verify(carService).getById(anyLong());
    }

    @Test
    public void showUpdatingForm_ShouldShowUpdatingForm_WhenPathParameterIsValid() throws Exception {
        Car expected = new Car();
        expected.setId(1L);
        expected.setModelId(7L);
        expected.setRegistrationPlate("AA1234BB");
        expected.setRentCostPerMin(30);
        expected.setColor("red");
        expected.setPhoto("car_1.png");

        when(carService.getById(anyLong())).thenReturn(car);

        mockMvc.perform(get("/cars/1/edit"))
                .andExpect(view().name("cars/edit"))
                .andExpect(model().attribute("car", expected));

        verify(carService).getById(anyLong());
    }

    @Test
    public void updateById_ShouldReturnUpdatingForm_WhenUpdatedObjectIsInvalid() throws Exception {
        Car expected = new Car();
        expected.setModelId(-1L);
        expected.setRegistrationPlate("");
        expected.setRentCostPerMin(-1);
        expected.setColor("");
        expected.setPhoto("");

        mockMvc.perform(post("/cars/edit")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                                new BasicNameValuePair("modelId", "-1"),
                                new BasicNameValuePair("registrationPlate", ""),
                                new BasicNameValuePair("rentCostPerMin", "-1"),
                                new BasicNameValuePair("color", ""),
                                new BasicNameValuePair("photo", "")
                        )))))
                .andExpect(status().isOk())
                .andExpect(view().name("cars/edit"))
                .andExpect(model().attribute("car", expected));

        verify(carService, never()).add(any());
    }

    @Test
    public void updateById_ShouldUpdateObject_WhenUpdatedObjectIsValid() throws Exception {
        when(carService.updateById(any())).thenReturn(true);

        Car expected = new Car();
        expected.setId(1L);
        expected.setModelId(7L);
        expected.setRegistrationPlate("AA1234BB");
        expected.setRentCostPerMin(30);
        expected.setColor("red");
        expected.setPhoto("car_1.png");

        mockMvc.perform(post("/cars/edit")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(EntityUtils.toString(new UrlEncodedFormEntity(List.of(
                                new BasicNameValuePair("id", "1"),
                                new BasicNameValuePair("modelId", "7"),
                                new BasicNameValuePair("registrationPlate", "AA1234BB"),
                                new BasicNameValuePair("rentCostPerMin", "30"),
                                new BasicNameValuePair("color", "red"),
                                new BasicNameValuePair("photo", "car_1.png")
                        )))))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/cars/1"))
                .andExpect(model().attribute("car", expected));

        verify(carService).updateById(any());
    }

    @Test
    public void deleteById_ShouldThrowException_WhenPathParameterIsInvalid() {
        when(carService.deleteById(anyLong())).thenThrow(MockitoException.class);

        assertThrows(NestedServletException.class,
                () -> mockMvc.perform(post("/cars/-1/delete")));

        verify(carService).deleteById(anyLong());
    }

    @Test
    public void deleteById_ShouldDeleteRecord_WhenPathParameterIsValid() throws Exception {
        when(carService.deleteById(anyLong())).thenReturn(true);

        mockMvc.perform(post("/cars/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/cars"));

        verify(carService).deleteById(anyLong());
    }

}