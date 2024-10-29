package com.rajat.product_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rajat.product_service.dto.*;
import com.rajat.product_service.service.IAddProductsService;
import com.rajat.product_service.service.IDeleteProductService;
import com.rajat.product_service.service.IUpdateProductsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ProductCommandControllerImpl.class)
class ProductCommandControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IAddProductsService addProductsService;

    @MockBean
    private IUpdateProductsService updateProductsService;

    @MockBean
    private IDeleteProductService deleteProductService;

    @Autowired
    private ObjectMapper objectMapper;

    private AddProductRequest validProductRequest;
    private Set<AddProductRequest> productRequests;

    @BeforeEach
    void setUp() {
        validProductRequest = new AddProductRequest();
        validProductRequest.setName("Product1");
        validProductRequest.setBrand("BrandA");
        validProductRequest.setCategory("CategoryA");
        validProductRequest.setDescription("Sample description");
        AddAttributeRequest attribute = new AddAttributeRequest();
        attribute.setName("Color");
        attribute.setValue("Red");
        validProductRequest.setAttributes(Set.of(attribute));
        productRequests = Set.of(validProductRequest);
    }

    @Test
    void testAddProducts_InvalidRequest_EmptyName() throws Exception {
        // Set an invalid name to trigger validation
        validProductRequest.setName("");

        mockMvc.perform(post("/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequests)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Product name must not be blank"));

        verify(addProductsService, never()).addProducts(anySet());
    }

    @Test
    void testUpdateProducts_InvalidRequest_NullId() throws Exception {
        UpdateProductRequest updateRequest = new UpdateProductRequest();
        updateRequest.setName("UpdatedName");

        mockMvc.perform(put("/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Set.of(updateRequest))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.id").value("Product ID must not be null"));

        verify(updateProductsService, never()).updateProducts(anySet());
    }

    @Test
    void testDeleteProducts_InvalidRequest_NullProductIds() throws Exception {
        mockMvc.perform(delete("/v1/products/ids")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Required request parameter 'productIds' for method parameter type Set is not present"));

        verify(deleteProductService, never()).deleteProduct(anySet());
    }


    @Test
    void testAddProducts_ValidRequest() throws Exception {
        // Mock response from service
        when(addProductsService.addProducts(anySet())).thenReturn(Set.of(new AddProductResponse()));

        // Perform request and validate response
        mockMvc.perform(post("/v1/products").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(productRequests))).andExpect(status().isOk());

        // Verify service call
        verify(addProductsService, times(1)).addProducts(anySet());
    }

    @Test
    void testUpdateProducts_ValidRequest() throws Exception {
        // Mock response from service
        when(updateProductsService.updateProducts(anySet())).thenReturn(Set.of(new UpdateProductResponse()));

        // Create a valid update request
        UpdateProductRequest updateRequest = new UpdateProductRequest();
        updateRequest.setId(UUID.randomUUID());
        updateRequest.setName("UpdatedName");

        // Perform request and validate response
        mockMvc.perform(put("/v1/products").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(Set.of(updateRequest)))).andExpect(status().isOk());

        // Verify service call
        verify(updateProductsService, times(1)).updateProducts(anySet());
    }

    @Test
    void testDeleteProducts_ValidRequest() throws Exception {
        Set<UUID> productIds = Set.of(UUID.randomUUID());

        // Mock response from service
        when(deleteProductService.deleteProduct(anySet())).thenReturn(ResponseEntity.ok().build());

        // Perform request and validate response
        mockMvc.perform(delete("/v1/products/ids").param("productIds", productIds.iterator().next().toString()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        // Verify service call
        verify(deleteProductService, times(1)).deleteProduct(anySet());
    }

    @Test
    void testDeleteProductAttributes_ValidRequest() throws Exception {
        DeleteProductRequest deleteRequest = new DeleteProductRequest();
        deleteRequest.setProductIdToAttributeNamesMap(Map.of(UUID.randomUUID(), Set.of("Attribute1")));

        // Perform request and validate response
        mockMvc.perform(delete("/v1/products/attributes").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(deleteRequest))).andExpect(status().isOk());

        // Verify service call
        verify(deleteProductService, times(1)).deleteProductAttributes(any(DeleteProductRequest.class));
    }
}
