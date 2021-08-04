package dev.andrylat.carsharing.controllers;

import dev.andrylat.carsharing.models.DiscountCard;
import dev.andrylat.carsharing.services.DiscountCardService;
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
class DiscountCardsControllerTest {

    @Mock
    private DiscountCardService discountCardService;

    @InjectMocks
    private DiscountCardsController discountCardsController;

    @Captor
    private ArgumentCaptor<Integer> pageNumberCaptor;

    @Captor
    private ArgumentCaptor<Integer> pageSizeCaptor;

    @Captor
    private ArgumentCaptor<Long> idCaptor;

    private MockMvc mockMvc;
    private long recordsNumber;
    private int discountCardsListSize;
    private int pageNumber;
    private DiscountCard discountCard;
    private List<DiscountCard> discountCards;

    @BeforeEach
    public void setUp() {
        discountCards = new ArrayList<>();

        DiscountCard temporal = new DiscountCard();
        temporal.setId(1L);
        temporal.setCardNumber("8267bacb06d2");
        temporal.setDiscountValue(6);
        discountCards.add(temporal);

        temporal = new DiscountCard();
        temporal.setId(2L);
        temporal.setCardNumber("96853b88d930");
        temporal.setDiscountValue(4);
        discountCards.add(temporal);

        recordsNumber = 2L;
        discountCardsListSize = discountCards.size();
        pageNumber = 0;
        discountCard = discountCards.get(0);

        mockMvc = MockMvcBuilders
                .standaloneSetup(discountCardsController)
                .build();
    }

    @Test
    public void getAll_ShouldReturn404Response_WhenUrlIsInvalid() throws Exception {
        mockMvc.perform(get("/invalidUrl"))
                .andExpect(status().isNotFound());

        verify(discountCardService, never()).getAll(anyInt(), anyInt());
    }

    @Test
    public void getAll_ShouldThrownException_WhenQueryParametersAreInvalid() {
        when(discountCardService.getAll(anyInt(), anyInt())).thenThrow(MockitoException.class);

        assertThrows(NestedServletException.class,
                () -> mockMvc.perform(get("/discountcards?pageNumber=-1&pageSize=-1")));

        verify(discountCardService).getAll(anyInt(), anyInt());
    }

    @Test
    public void getAll_ShouldReturnRecords_WhenQueryParametersAreAbsent() throws Exception {
        int pageSize = 10;

        when(discountCardService.getAll(anyInt(), anyInt())).thenReturn(discountCards);
        when(discountCardService.getRecordsNumber()).thenReturn(recordsNumber);

        mockMvc.perform(get("/discountcards"))
                .andExpect(status().isOk())
                .andExpect(view().name("discountcards/getAll"))
                .andExpect(model().attribute("discountCards", hasSize(discountCardsListSize)))
                .andExpect(model().attribute("recordsNumber", recordsNumber))
                .andExpect(model().attribute("pageNumber", pageNumber))
                .andExpect(model().attribute("pageSize", pageSize));

        verify(discountCardService).getAll(pageNumberCaptor.capture(), pageSizeCaptor.capture());

        assertThat(pageNumberCaptor.getValue(), is(pageNumber));
        assertThat(pageSizeCaptor.getValue(), is(pageSize));

        verify(discountCardService).getRecordsNumber();
    }

    @Test
    public void getAll_ShouldReturnRecords_WhenQueryParametersArePresent() throws Exception {
        int pageSize = 5;

        when(discountCardService.getAll(anyInt(), anyInt())).thenReturn(discountCards);
        when(discountCardService.getRecordsNumber()).thenReturn(recordsNumber);

        mockMvc.perform(get("/discountcards?pageNumber=0&pageSize=5"))
                .andExpect(status().isOk())
                .andExpect(view().name("discountcards/getAll"))
                .andExpect(model().attribute("discountCards", hasSize(discountCardsListSize)))
                .andExpect(model().attribute("recordsNumber", recordsNumber))
                .andExpect(model().attribute("pageNumber", pageNumber))
                .andExpect(model().attribute("pageSize", pageSize));

        verify(discountCardService).getAll(pageNumberCaptor.capture(), pageSizeCaptor.capture());

        assertThat(pageNumberCaptor.getValue(), is(pageNumber));
        assertThat(pageSizeCaptor.getValue(), is(pageSize));

        verify(discountCardService).getRecordsNumber();
    }

    @Test
    public void getById_ShouldThrowException_WhenPathParameterIsInvalid() {
        when(discountCardService.getById(anyLong())).thenThrow(MockitoException.class);

        assertThrows(NestedServletException.class,
                () -> mockMvc.perform(get("/discountcards/-1")));

        verify(discountCardService).getById(anyLong());
    }

    @Test
    public void getById_ShouldReturnRecord_WhenPathParameterIsValid() throws Exception {
        DiscountCard expected = new DiscountCard();
        expected.setId(1L);
        expected.setCardNumber("8267bacb06d2");
        expected.setDiscountValue(6);

        when(discountCardService.getById(anyLong())).thenReturn(discountCard);

        mockMvc.perform(get("/discountcards/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("discountcards/getById"))
                .andExpect(model().attribute("discountCard", expected));

        verify(discountCardService).getById(idCaptor.capture());

        assertThat(idCaptor.getValue(), is(1L));
    }

}