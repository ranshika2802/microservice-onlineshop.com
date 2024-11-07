package com.rajat.customer_service.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.rajat.customer_service.dto.ApiResponse;
import com.rajat.customer_service.dto.ViewCategoryResponse;
import com.rajat.customer_service.dto.ViewInventoryResponse;
import com.rajat.customer_service.dto.ViewPriceResponse;
import com.rajat.customer_service.dto.ViewProductAttributeResponse;
import com.rajat.customer_service.dto.ViewProductDetailsResponse;
import com.rajat.customer_service.service.CustomerQueryService;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CustomerQueryControllerImpl.class)
public class CustomerQueryControllerImplTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private CustomerQueryService customerQueryService;

  private ApiResponse apiResponse;

  @BeforeEach
  void setUp() {
    ViewCategoryResponse categoryResponse = new ViewCategoryResponse();
    categoryResponse.setId(1L);
    categoryResponse.setName("Electronics");
    categoryResponse.setDescription("Electronic devices");

    ViewInventoryResponse inventoryResponse = new ViewInventoryResponse();
    inventoryResponse.setTotal(100);
    inventoryResponse.setAvailable(80);
    inventoryResponse.setReserved(20);

    ViewPriceResponse priceResponse = new ViewPriceResponse();
    priceResponse.setCurrency("USD");
    priceResponse.setAmount(299.99f);

    ViewProductAttributeResponse attributeResponse = new ViewProductAttributeResponse();
    attributeResponse.setName("Color");
    attributeResponse.setValue("Black");

    ViewProductDetailsResponse productDetailsResponse = new ViewProductDetailsResponse();
    productDetailsResponse.setName("Smartphone");
    productDetailsResponse.setBrand("BrandX");
    productDetailsResponse.setCategory(categoryResponse);
    productDetailsResponse.setDescription("Latest model");
    productDetailsResponse.setAttributes(Set.of(attributeResponse));
    productDetailsResponse.setPrice(priceResponse);
    productDetailsResponse.setInventory(inventoryResponse);

    apiResponse = new ApiResponse(true, Set.of("Success"), Set.of(productDetailsResponse));
  }

  @Test
  void testGetProductDetailsByCategories() throws Exception {
    Set<String> categories = Set.of("Electronics");
    when(customerQueryService.getProductDetailsByCategories(categories)).thenReturn(apiResponse);

    mockMvc
        .perform(
            get("/v1/customer/products")
                .param("categories", "Electronics")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.message[0]").value("Success"))
        .andExpect(jsonPath("$.viewProductDetailsResponses[0].name").value("Smartphone"))
        .andExpect(jsonPath("$.viewProductDetailsResponses[0].brand").value("BrandX"))
        .andExpect(jsonPath("$.viewProductDetailsResponses[0].category.name").value("Electronics"))
        .andExpect(jsonPath("$.viewProductDetailsResponses[0].price.currency").value("USD"))
        .andExpect(jsonPath("$.viewProductDetailsResponses[0].price.amount").value(299.99))
        .andExpect(jsonPath("$.viewProductDetailsResponses[0].inventory.total").value(100))
        .andExpect(jsonPath("$.viewProductDetailsResponses[0].inventory.available").value(80))
        .andExpect(jsonPath("$.viewProductDetailsResponses[0].inventory.reserved").value(20))
        .andExpect(jsonPath("$.viewProductDetailsResponses[0].attributes[0].name").value("Color"))
        .andExpect(jsonPath("$.viewProductDetailsResponses[0].attributes[0].value").value("Black"));
  }
}
