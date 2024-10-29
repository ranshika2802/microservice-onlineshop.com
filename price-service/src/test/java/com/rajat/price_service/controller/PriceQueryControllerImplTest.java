package com.rajat.price_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rajat.price_service.dto.ViewProductPriceResponse;
import com.rajat.price_service.service.ViewProductPriceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PriceQueryControllerImpl.class)
@ExtendWith(MockitoExtension.class)
public class PriceQueryControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ViewProductPriceService priceViewService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testViewPriceByProductId_ValidRequest() throws Exception {
        UUID productId = UUID.randomUUID();
        ViewProductPriceResponse response = new ViewProductPriceResponse();
        response.setId(1);
        response.setCurrency("USD");
        response.setAmount(99.99f);
        response.setProductId(productId);

        when(priceViewService.viewPriceByProductId(anyList())).thenReturn(List.of(response));

        mockMvc.perform(get("/v1/prices/productId").param("productIds", productId.toString()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$[0].id", is(response.getId()))).andExpect(jsonPath("$[0].currency", is(response.getCurrency()))).andExpect(jsonPath("$[0].amount", closeTo(99.99, 0.01))) // Adjusted precision check
                .andExpect(jsonPath("$[0].productId", is(response.getProductId().toString())));

        verify(priceViewService, times(1)).viewPriceByProductId(anyList());
    }

    @Test
    void testViewPriceByProductId_InvalidRequest_NullProductIds() throws Exception {
        mockMvc.perform(get("/v1/prices/productId") // Missing productIds param
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andExpect(content().json("{\"error\":\"Missing required request parameter\",\"message\":\"productIds parameter is missing\"}"));

        verify(priceViewService, never()).viewPriceByProductId(anyList());
    }

    @Test
    void testViewPriceByProductId_InvalidRequest_EmptyProductIds() throws Exception {
        mockMvc.perform(get("/v1/prices/productId").param("productIds", "") // Empty productIds param
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andExpect(content().json("{\"error\":\"Invalid argument\",\"message\":\"ProductIds cannot be empty\"}"));

        verify(priceViewService, never()).viewPriceByProductId(anyList());
    }
}
