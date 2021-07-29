package dev.andrylat.carsharing.controllers;

import dev.andrylat.carsharing.dao.BodyTypeDAO;
import dev.andrylat.carsharing.models.BodyType;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class BodyTypesControllerTest {
    @Mock
    private BodyTypeDAO bodyTypeDAO;

    @InjectMocks
    private BodyTypesController bodyTypesController;

    @Captor
    private ArgumentCaptor<Integer> pageNumberCaptor;

    @Captor
    private ArgumentCaptor<Integer> pageSizeCaptor;

    private MockMvc mockMvc;
    private long recordsNumber;
    private int bodyTypesListSize;
    private int pageNumber;
    private List<BodyType> bodyTypes;

    @BeforeEach
    public void setUp() {
        bodyTypes = new ArrayList<>();
        bodyTypes.add(new BodyType(1, "sedan"));
        bodyTypes.add(new BodyType(2, "SUV"));

        recordsNumber = 2;
        bodyTypesListSize = bodyTypes.size();
        pageNumber = 0;

        mockMvc = MockMvcBuilders
                .standaloneSetup(bodyTypesController)
                .build();
    }

    @Test
    public void getAll_ShouldReturn404Response_WhenUrlIsInvalid() throws Exception {
        mockMvc.perform(get("/invalidUrl"))
                .andExpect(status().isNotFound());

        verify(bodyTypeDAO, never()).getAll(anyInt(), anyInt());
    }

    @Test
    public void getAll_ShouldThrownException_WhenQueryParametersAreInvalid() {
        when(bodyTypeDAO.getAll(anyInt(), anyInt())).thenThrow(MockitoException.class);

        assertThrows(NestedServletException.class,
                () -> mockMvc.perform(get("/bodytypes?pageNumber=-1&pageSize=-1")));

        verify(bodyTypeDAO).getAll(anyInt(), anyInt());
    }

    @Test
    public void getAll_ShouldReturnBodyTypes_WhenQueryParametersAreAbsent() throws Exception {
        int pageSize = 10;

        when(bodyTypeDAO.getAll(anyInt(), anyInt())).thenReturn(bodyTypes);
        when(bodyTypeDAO.getRecordsNumber()).thenReturn(recordsNumber);

        mockMvc.perform(get("/bodytypes"))
                .andExpect(status().isOk())
                .andExpect(view().name("bodytypes/getAll"))
                .andExpect(model().attribute("bodyTypes", hasSize(bodyTypesListSize)))
                .andExpect(model().attribute("recordsNumber", recordsNumber))
                .andExpect(model().attribute("pageNumber", pageNumber))
                .andExpect(model().attribute("pageSize", pageSize));

        verify(bodyTypeDAO).getAll(pageNumberCaptor.capture(), pageSizeCaptor.capture());

        assertThat(pageNumberCaptor.getValue(), is(pageNumber));
        assertThat(pageSizeCaptor.getValue(), is(pageSize));

        verify(bodyTypeDAO).getRecordsNumber();
    }

    @Test
    public void getAll_ShouldReturnBodyTypes_WhenQueryParametersArePresent() throws Exception {
        int pageSize = 5;

        when(bodyTypeDAO.getAll(anyInt(), anyInt())).thenReturn(bodyTypes);
        when(bodyTypeDAO.getRecordsNumber()).thenReturn(recordsNumber);

        mockMvc.perform(get("/bodytypes?pageNumber=0&pageSize=5"))
                .andExpect(status().isOk())
                .andExpect(view().name("bodytypes/getAll"))
                .andExpect(model().attribute("bodyTypes", hasSize(bodyTypesListSize)))
                .andExpect(model().attribute("recordsNumber", recordsNumber))
                .andExpect(model().attribute("pageNumber", pageNumber))
                .andExpect(model().attribute("pageSize", pageSize));

        verify(bodyTypeDAO).getAll(pageNumberCaptor.capture(), pageSizeCaptor.capture());

        assertThat(pageNumberCaptor.getValue(), is(pageNumber));
        assertThat(pageSizeCaptor.getValue(), is(pageSize));

        verify(bodyTypeDAO).getRecordsNumber();
    }

}