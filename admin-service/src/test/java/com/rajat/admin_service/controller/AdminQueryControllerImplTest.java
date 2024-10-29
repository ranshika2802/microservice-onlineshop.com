package com.rajat.admin_service.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rajat.admin_service.dto.response.retrieve.*;
import com.rajat.admin_service.service.view.ViewAdminService;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class AdminQueryControllerImplTest {

  private MockMvc mockMvc;

  @Mock private ViewAdminService viewAdminService;

  @InjectMocks private AdminQueryControllerImpl adminQueryControllerImpl;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(adminQueryControllerImpl).build();
  }

  @Test
  void viewProductsDetailsByCategory_validRequest_shouldReturnOk() throws Exception {
    // Mock data setup
    UUID productId = UUID.randomUUID();

    // Setting up a sample product response
    ViewCategoryResponseDto category = new ViewCategoryResponseDto(1L, "Electronics", "Devices");
    ViewPriceResponseDto price = new ViewPriceResponseDto("USD", 299.99f);
    ViewInventoryResponseDto inventory = new ViewInventoryResponseDto(100L, 80L, 20L);

    ViewProductAttributeResponseDto attribute = new ViewProductAttributeResponseDto();
    attribute.setName("Color");
    attribute.setValue("Black");

    Set<ViewProductAttributeResponseDto> attributes = Set.of(attribute);

    ViewProductDetailsResponseDto product = new ViewProductDetailsResponseDto();
    product.setId(productId);
    product.setName("Smartphone");
    product.setBrand("BrandX");
    product.setCategory(category);
    product.setDescription("A high-quality smartphone");
    product.setPrice(price);
    product.setInventory(inventory);
    product.setAttributes(attributes);

    ApiResponse apiResponse =
        new ApiResponse(true, Set.of("Products retrieved successfully"), Set.of(product));

    // Mock the service call
    Set<String> categories = Set.of("Electronics");
    when(viewAdminService.viewProductsDetailsByCategory(categories)).thenReturn(apiResponse);

    // Perform GET request and verify response
    mockMvc
        .perform(
            get("/v1/admin/products")
                .param("categories", "Electronics")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.message[0]").value("Products retrieved successfully"))
        .andExpect(jsonPath("$.dataSet[0].id").value(productId.toString()))
        .andExpect(jsonPath("$.dataSet[0].name").value("Smartphone"))
        .andExpect(jsonPath("$.dataSet[0].brand").value("BrandX"))
        .andExpect(jsonPath("$.dataSet[0].category.name").value("Electronics"))
        .andExpect(jsonPath("$.dataSet[0].price.currency").value("USD"))
        .andExpect(jsonPath("$.dataSet[0].price.amount").value(299.99))
        .andExpect(jsonPath("$.dataSet[0].inventory.total").value(100))
        .andExpect(jsonPath("$.dataSet[0].inventory.available").value(80))
        .andExpect(jsonPath("$.dataSet[0].inventory.reserved").value(20))
        .andExpect(jsonPath("$.dataSet[0].attributes[0].name").value("Color"))
        .andExpect(jsonPath("$.dataSet[0].attributes[0].value").value("Black"));

    // Verify that the service was called once
    verify(viewAdminService, times(1)).viewProductsDetailsByCategory(categories);
  }

  @Test
  void viewProductsDetailsByCategory_emptyCategories_shouldReturnBadRequest() throws Exception {
    // Perform GET request with empty categories and verify response
    mockMvc
        .perform(
            get("/v1/admin/products")
                .param("categories", "") // Empty categories set
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());

    // Verify the service method was not called
    verify(viewAdminService, never()).viewProductsDetailsByCategory(any());
  }
}
