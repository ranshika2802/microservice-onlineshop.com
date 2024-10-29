package com.rajat.admin_service.controller;

import static org.hamcrest.Matchers.hasItem;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rajat.admin_service.dto.request.add.*;
import com.rajat.admin_service.dto.request.delete.DeleteProductAttributesDto;
import com.rajat.admin_service.dto.request.delete.DeleteProductDetailsDto;
import com.rajat.admin_service.dto.request.update.UpdateProductDetailsDto;
import com.rajat.admin_service.dto.response.add.AddProductDetailsResponseDto;
import com.rajat.admin_service.dto.response.delete.DeleteProductDetailsResponseDto;
import com.rajat.admin_service.service.add.AddAdminService;
import com.rajat.admin_service.service.delete.DeleteAdminService;
import com.rajat.admin_service.service.update.UpdateAdminService;
import java.util.Collections;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(AdminCommandControllerImpl.class)
public class AdminCommandControllerImplTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private AddAdminService addAdminService;

  @MockBean private UpdateAdminService updateAdminService;

  @MockBean private DeleteAdminService deleteAdminService;

  @Autowired private ObjectMapper objectMapper;

  @Test
  void addProductsDetails_validRequest_shouldReturnOk() throws Exception {
    // Arrange
    AddProductDetailsDto addProductDetailsDto = new AddProductDetailsDto();
    AddProductDto addProductDto = new AddProductDto();
    AddAttributeDto attributeDto = new AddAttributeDto();
    attributeDto.setName("ram");
    attributeDto.setValue("16gb");
    addProductDto.setName("Mobile");
    addProductDto.setBrand("Apple");
    addProductDto.setCategory("Electronics");
    addProductDto.setAttributes(Set.of(attributeDto));
    AddPriceDto addPriceDto = new AddPriceDto();
    addPriceDto.setCurrency("INR");
    addPriceDto.setAmount(34000.00f);
    AddInventoryDto addInventoryDto = new AddInventoryDto();
    addInventoryDto.setReserved(100);
    addInventoryDto.setTotal(200);
    addProductDetailsDto.setProduct(addProductDto);
    addProductDetailsDto.setPrice(addPriceDto);
    addProductDetailsDto.setInventory(addInventoryDto);

    AddProductDetailsResponseDto responseDto =
        AddProductDetailsResponseDto.builder().status("Product(s) added successfully.").build();
    Set<AddProductDetailsResponseDto> responseSet = Collections.singleton(responseDto);

    Mockito.when(addAdminService.addProductsDetails(any())).thenReturn(responseSet);

    // Act and Assert
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/v1/admin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Set.of(addProductDetailsDto))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray());
  }

  @Test
  void updateProductsDetails_invalidRequest_shouldReturnBadRequest() throws Exception {
    // Creating invalid data (null product fields)
    UpdateProductDetailsDto invalidUpdateProduct = new UpdateProductDetailsDto();
    invalidUpdateProduct.setProduct(null); // Violates @NotNull constraint

    mockMvc
        .perform(
            MockMvcRequestBuilders.put("/v1/admin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Set.of(invalidUpdateProduct))))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").isNotEmpty());
  }

  @Test
  void deleteProductsDetails_validRequest_shouldReturnOk() throws Exception {
    // Mocking the response from deleteAdminService
    DeleteProductDetailsResponseDto mockResponse =
        DeleteProductDetailsResponseDto.builder().status("Product Deleted.").build();

    // Mock the service layer response
    Mockito.when(deleteAdminService.deleteProductDetails(any(DeleteProductDetailsDto.class)))
        .thenReturn(mockResponse);

    // Perform the DELETE request with a valid UUID in productIds
    mockMvc
        .perform(
            delete("/v1/admin/ids")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"productIds\": [\"d040842c-07b6-44cf-a146-60f12a1f4b33\"]}"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status").value("Product Deleted."));
  }

  @Test
  void deleteProductAttributes_invalidRequest_shouldReturnBadRequest() throws Exception {
    // Sending a request with `productIdToAttributeNamesMap` as `null` to trigger validation
    mockMvc
        .perform(
            delete("/v1/admin/attributes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"productIdToAttributeNamesMap\":null}"))
        .andExpect(status().isBadRequest()) // Expecting 400 status
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.message").isArray())
        .andExpect(jsonPath("$.message").value(hasItem("Provide attribute details to delete.")));
  }
}
