package com.rajat.product_service.controller;

import com.rajat.product_service.dto.AttributeResponse;
import com.rajat.product_service.dto.CategoryResponse;
import com.rajat.product_service.dto.ProductResponse;
import com.rajat.product_service.service.IViewProductsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ProductQueryControllerImpl.class)
public class ProductQueryControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IViewProductsService viewProductsService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void viewProductsByCategory_ShouldReturnProductResponses() throws Exception {
        // Arrange
        Set<String> categories = new HashSet<>(Collections.singletonList("Electronics"));
        ProductResponse productResponse = createSampleProductResponse();

        when(viewProductsService.viewProductsByCategory(categories))
                .thenReturn(Collections.singleton(productResponse));

        // Act & Assert
        mockMvc.perform(get("/v1/products/categories")
                        .param("categories", "Electronics")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(productResponse.getId().toString()))
                .andExpect(jsonPath("$[0].name").value(productResponse.getName()))
                .andExpect(jsonPath("$[0].brand").value(productResponse.getBrand()))
                .andExpect(jsonPath("$[0].category.id").value(productResponse.getCategory().getId().intValue()))
                .andExpect(jsonPath("$[0].category.name").value(productResponse.getCategory().getName()))
                .andExpect(jsonPath("$[0].category.description").value(productResponse.getCategory().getDescription()))
                .andExpect(jsonPath("$[0].attributes[0].name").value(productResponse.getAttributes().get(0).getName()))
                .andExpect(jsonPath("$[0].attributes[0].value").value(productResponse.getAttributes().get(0).getValue()));
    }

    private ProductResponse createSampleProductResponse() {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(UUID.randomUUID());
        productResponse.setName("Smartphone");
        productResponse.setBrand("BrandX");

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(1L);
        categoryResponse.setName("Electronics");
        categoryResponse.setDescription("Electronic items");
        productResponse.setCategory(categoryResponse);

        AttributeResponse attributeResponse = new AttributeResponse();
        attributeResponse.setName("Color");
        attributeResponse.setValue("Black");

        productResponse.setAttributes(List.of(attributeResponse));
        return productResponse;
    }
}
