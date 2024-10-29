package com.rajat.price_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rajat.price_service.dto.AddProductPriceRequest;
import com.rajat.price_service.dto.AddProductPriceResponse;
import com.rajat.price_service.dto.UpdateProductPriceRequest;
import com.rajat.price_service.dto.UpdateProductPriceResponse;
import com.rajat.price_service.service.AddProductPriceService;
import com.rajat.price_service.service.DeleteProductPriceService;
import com.rajat.price_service.service.UpdateProductPriceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PriceCommandControllerImpl.class)
public class PriceCommandControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddProductPriceService addProductPriceService;

    @MockBean
    private UpdateProductPriceService updateProductPriceService;

    @MockBean
    private DeleteProductPriceService deleteProductPriceService;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID validProductId;
    private AddProductPriceRequest validAddRequest;
    private UpdateProductPriceRequest validUpdateRequest;

    @BeforeEach
    void setUp() {
        validProductId = UUID.randomUUID();
        validAddRequest = new AddProductPriceRequest();
        validAddRequest.setProductId(validProductId);
        validAddRequest.setCurrency("USD");
        validAddRequest.setAmount(99.99f);

        validUpdateRequest = new UpdateProductPriceRequest();
        validUpdateRequest.setProductId(validProductId);
        validUpdateRequest.setCurrency("USD");
        validUpdateRequest.setAmount(49.99f);
    }

    @Test
    void testAddProductPrice_ValidRequest() throws Exception {
        // Arrange: Mock response
        AddProductPriceResponse response = new AddProductPriceResponse();
        response.setId(1);
        response.setCurrency("USD");
        response.setAmount(99.99f);
        response.setProductId(validProductId);

        when(addProductPriceService.addProductPrice(anySet())).thenReturn(List.of(response));

        // Act & Assert
        mockMvc.perform(post("/v1/prices/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Set.of(validAddRequest))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].currency", is("USD")))
                .andExpect(jsonPath("$[0].amount", is(99.99)))
                .andExpect(jsonPath("$[0].productId", is(validProductId.toString())));

        verify(addProductPriceService, times(1)).addProductPrice(anySet());
    }

    @Test
    void testAddProductPrice_InvalidCurrency() throws Exception {
        // Arrange: Set an invalid currency format for validation testing
        validAddRequest.setCurrency("US");  // Invalid format
        String invalidRequestJson = objectMapper.writeValueAsString(Set.of(validAddRequest));

        // Act & Assert: Expect 400 Bad Request due to validation error
        mockMvc.perform(post("/v1/prices/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.['addProductPrice.addProductPriceRequests[].currency']").value("Currency must be a valid ISO 4217 currency code (3 uppercase letters)"));
    }


    @Test
    void testUpdateProductPrice_ValidRequest() throws Exception {
        // Arrange: Mock response
        UpdateProductPriceResponse response = new UpdateProductPriceResponse();
        response.setId(1);
        response.setCurrency("USD");
        response.setAmount(49.99f);
        response.setProductId(validProductId);

        when(updateProductPriceService.updateProductPrice(anyList())).thenReturn(List.of(response));

        // Act & Assert
        mockMvc.perform(put("/v1/prices/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(validUpdateRequest))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].currency", is("USD")))
                .andExpect(jsonPath("$[0].amount", is(49.99)))
                .andExpect(jsonPath("$[0].productId", is(validProductId.toString())));

        verify(updateProductPriceService, times(1)).updateProductPrice(anyList());
    }

    @Test
    void testUpdateProductPrice_InvalidProductId() throws Exception {
        // Arrange: Create a request with null productId
        validUpdateRequest.setProductId(null);  // Null value to trigger validation
        String invalidRequestJson = objectMapper.writeValueAsString(List.of(validUpdateRequest));

        // Act & Assert: Expect 400 Bad Request due to validation error
        mockMvc.perform(put("/v1/prices/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.['updateProductPrice.updateProductPriceRequests[0].productId']").value("Product Id cannot be null."));
    }

    @Test
    void testDeleteProductPrice_ValidRequest() throws Exception {
        // Arrange: Mock product IDs as query parameters
        List<String> productIds = List.of(validProductId.toString());

        // Act & Assert
        mockMvc.perform(delete("/v1/prices/ids")
                        .param("productIds", productIds.toArray(new String[0])))
                .andExpect(status().isOk());

        verify(deleteProductPriceService, times(1)).deleteProductPrice(productIds);
    }

    @Test
    void testDeleteProductPrice_InvalidRequest() throws Exception {
        // Act & Assert: Omit the 'productIds' parameter to simulate a missing required parameter
        mockMvc.perform(delete("/v1/prices/ids"))
                .andExpect(status().isBadRequest());
    }
}
