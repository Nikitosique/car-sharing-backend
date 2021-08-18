package dev.andrylat.carsharing.controllers;

import dev.andrylat.carsharing.models.BodyType;
import dev.andrylat.carsharing.services.BodyTypeService;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class BodyTypesControllerTest {

    @Mock
    private BodyTypeService bodyTypeService;

    @InjectMocks
    private BodyTypesController bodyTypesController;

    private MockMvc mockMvc;
    private long recordsNumber;
    private int bodyTypesListSize;
    private int pageNumber;
    private BodyType bodyType;
    private List<BodyType> bodyTypes;

    @BeforeEach
    public void setUp() {
        bodyTypes = new ArrayList<>();
        bodyTypes.add(new BodyType(1, "sedan"));
        bodyTypes.add(new BodyType(2, "SUV"));

        recordsNumber = 2L;
        bodyTypesListSize = bodyTypes.size();
        pageNumber = 0;
        bodyType = bodyTypes.get(0);

        mockMvc = MockMvcBuilders
                .standaloneSetup(bodyTypesController)
                .build();
    }

    @Test
    public void getAll_ShouldReturn404Response_WhenUrlIsInvalid() throws Exception {
        mockMvc.perform(get("/invalidUrl"))
                .andExpect(status().isNotFound());

        verify(bodyTypeService, never()).getAll(anyInt(), anyInt());
    }

    @Test
    public void getAll_ShouldThrownException_WhenQueryParametersAreInvalid() {
        when(bodyTypeService.getAll(anyInt(), anyInt())).thenThrow(MockitoException.class);

        assertThrows(NestedServletException.class,
                () -> mockMvc.perform(get("/bodytypes?pageNumber=-1&pageSize=-1")));

        verify(bodyTypeService).getAll(anyInt(), anyInt());
    }

    @Test
    public void getAll_ShouldReturnRecords_WhenQueryParametersAreAbsent() throws Exception {
        int pageSize = 10;

        when(bodyTypeService.getRecordsNumber()).thenReturn(recordsNumber);
        when(bodyTypeService.getAll(anyInt(), anyInt())).thenReturn(bodyTypes);

        mockMvc.perform(get("/bodytypes"))
                .andExpect(status().isOk())
                .andExpect(view().name("bodytypes/getAll"))
                .andExpect(model().attribute("bodyTypes", hasSize(bodyTypesListSize)))
                .andExpect(model().attribute("recordsNumber", recordsNumber))
                .andExpect(model().attribute("pageNumber", pageNumber))
                .andExpect(model().attribute("pageSize", pageSize));

        verify(bodyTypeService).getRecordsNumber();
        verify(bodyTypeService).getAll(anyInt(), anyInt());
    }

    @Test
    public void getAll_ShouldReturnRecords_WhenQueryParametersArePresent() throws Exception {
        int pageSize = 5;

        when(bodyTypeService.getRecordsNumber()).thenReturn(recordsNumber);
        when(bodyTypeService.getAll(anyInt(), anyInt())).thenReturn(bodyTypes);

        mockMvc.perform(get("/bodytypes?pageNumber=0&pageSize=5"))
                .andExpect(status().isOk())
                .andExpect(view().name("bodytypes/getAll"))
                .andExpect(model().attribute("bodyTypes", hasSize(bodyTypesListSize)))
                .andExpect(model().attribute("recordsNumber", recordsNumber))
                .andExpect(model().attribute("pageNumber", pageNumber))
                .andExpect(model().attribute("pageSize", pageSize));

        verify(bodyTypeService).getRecordsNumber();
        verify(bodyTypeService).getAll(anyInt(), anyInt());
    }

    @Test
    public void getById_ShouldThrowException_WhenPathParameterIsInvalid() {
        when(bodyTypeService.getById(anyLong())).thenThrow(MockitoException.class);

        assertThrows(NestedServletException.class,
                () -> mockMvc.perform(get("/bodytypes/-1")));

        verify(bodyTypeService).getById(anyLong());
    }

    @Test
    public void getById_ShouldReturnRecord_WhenPathParameterIsValid() throws Exception {
        BodyType expected = new BodyType(1L, "sedan");

        when(bodyTypeService.getById(anyLong())).thenReturn(bodyType);

        mockMvc.perform(get("/bodytypes/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("bodytypes/getById"))
                .andExpect(model().attribute("bodyType", expected));

        verify(bodyTypeService).getById(anyLong());
    }

    @Test
    public void showAdditionForm_ShouldShowAdditionForm_WhenUrlIsValid() throws Exception {
        BodyType expected = new BodyType();

        mockMvc.perform(get("/bodytypes/add"))
                .andExpect(view().name("bodytypes/add"))
                .andExpect(model().attribute("bodyType", expected));
    }

    @Test
    public void add_ShouldReturnAdditionForm_WhenAddedObjectIsInvalid() throws Exception {
        BodyType expected = new BodyType(1L, "");

        mockMvc.perform(post("/bodytypes/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                                new BasicNameValuePair("id", "1"),
                                new BasicNameValuePair("name", "")
                        )))))
                .andExpect(status().isOk())
                .andExpect(view().name("bodytypes/add"))
                .andExpect(model().attribute("bodyType", expected));

        verify(bodyTypeService, never()).add(any());
    }

    @Test
    public void add_ShouldAddObject_WhenAddedObjectIsValid() throws Exception {
        BodyType expected = new BodyType();
        expected.setName("sedan");

        when(bodyTypeService.add(any())).thenReturn(bodyType);

        mockMvc.perform(post("/bodytypes/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(EntityUtils.toString(new UrlEncodedFormEntity(List.of(
                                new BasicNameValuePair("name", "sedan")
                        )))))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bodytypes/1"))
                .andExpect(model().attribute("bodyType", expected));

        verify(bodyTypeService).add(any());
    }

    @Test
    public void showUpdatingForm_ShouldThrowException_WhenPathParameterIsInvalid() {
        when(bodyTypeService.getById(anyLong())).thenThrow(MockitoException.class);

        assertThrows(NestedServletException.class,
                () -> mockMvc.perform(get("/bodytypes/-1/edit")));

        verify(bodyTypeService).getById(anyLong());
    }

    @Test
    public void showUpdatingForm_ShouldShowUpdatingForm_WhenPathParameterIsValid() throws Exception {
        BodyType expected = new BodyType(1L, "sedan");

        when(bodyTypeService.getById(anyLong())).thenReturn(bodyType);

        mockMvc.perform(get("/bodytypes/1/edit"))
                .andExpect(view().name("bodytypes/edit"))
                .andExpect(model().attribute("bodyType", expected));

        verify(bodyTypeService).getById(anyLong());
    }

    @Test
    public void updateById_ShouldReturnUpdatingForm_WhenUpdatedObjectIsInvalid() throws Exception {
        BodyType expected = new BodyType(1L, "");

        mockMvc.perform(post("/bodytypes/edit")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                                new BasicNameValuePair("id", "1"),
                                new BasicNameValuePair("name", "")
                        )))))
                .andExpect(status().isOk())
                .andExpect(view().name("bodytypes/edit"))
                .andExpect(model().attribute("bodyType", expected));

        verify(bodyTypeService, never()).add(any());
    }

    @Test
    public void updateById_ShouldUpdateObject_WhenUpdatedObjectIsValid() throws Exception {
        BodyType expected = new BodyType(1L, "sedan");

        when(bodyTypeService.updateById(any())).thenReturn(true);

        mockMvc.perform(post("/bodytypes/edit")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(EntityUtils.toString(new UrlEncodedFormEntity(List.of(
                                new BasicNameValuePair("id", "1"),
                                new BasicNameValuePair("name", "sedan")
                        )))))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bodytypes/1"))
                .andExpect(model().attribute("bodyType", expected));

        verify(bodyTypeService).updateById(any());
    }

    @Test
    public void deleteById_ShouldThrowException_WhenPathParameterIsInvalid() {
        when(bodyTypeService.deleteById(anyLong())).thenThrow(MockitoException.class);

        assertThrows(NestedServletException.class,
                () -> mockMvc.perform(post("/bodytypes/-1/delete")));

        verify(bodyTypeService).deleteById(anyLong());
    }

    @Test
    public void deleteById_ShouldDeleteRecord_WhenPathParameterIsValid() throws Exception {
        when(bodyTypeService.deleteById(anyLong())).thenReturn(true);

        mockMvc.perform(post("/bodytypes/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bodytypes"));

        verify(bodyTypeService).deleteById(anyLong());
    }

}