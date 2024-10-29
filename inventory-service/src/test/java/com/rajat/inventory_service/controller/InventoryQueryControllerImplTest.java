package com.rajat.inventory_service.controller;

import com.rajat.inventory_service.dto.ViewProductInventoryResponse;
import com.rajat.inventory_service.service.ViewProductInventoryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;
import java.util.Set;
import java.util.List;

import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = InventoryQueryControllerImpl.class)
public class InventoryQueryControllerImplTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private ViewProductInventoryService viewProductInventoryService;

  @Test
  public void testViewInventoryByProductId() throws Exception {
    // Prepare test data
    UUID productId1 = UUID.randomUUID();
    UUID productId2 = UUID.randomUUID();

    Set<UUID> productIds = Set.of(productId1, productId2);

    ViewProductInventoryResponse response1 = new ViewProductInventoryResponse();
    response1.setInventoryId(1L);
    response1.setProductId(productId1);
    response1.setTotal(100);
    response1.setReserved(10);

    ViewProductInventoryResponse response2 = new ViewProductInventoryResponse();
    response2.setInventoryId(2L);
    response2.setProductId(productId2);
    response2.setTotal(200);
    response2.setReserved(20);

    List<ViewProductInventoryResponse> responses = List.of(response1, response2);

    // Mock the service method to return our test data
    when(viewProductInventoryService.viewInventoryByProductId(anySet())).thenReturn(responses);

    // Perform the GET request
    mockMvc
        .perform(
            get("/v1/inventory/query")
                .param("productIds", productId1.toString(), productId2.toString()))
        .andExpect(status().isOk())
        // Verify the response content
        .andExpect(jsonPath("$[0].inventoryId").value(1))
        .andExpect(jsonPath("$[0].productId").value(productId1.toString()))
        .andExpect(jsonPath("$[0].total").value(100))
        .andExpect(jsonPath("$[0].reserved").value(10))
        .andExpect(jsonPath("$[0].available").value(90))
        .andExpect(jsonPath("$[1].inventoryId").value(2))
        .andExpect(jsonPath("$[1].productId").value(productId2.toString()))
        .andExpect(jsonPath("$[1].total").value(200))
        .andExpect(jsonPath("$[1].reserved").value(20))
        .andExpect(jsonPath("$[1].available").value(180));

    // Verify that the service method was called with the correct parameters
    verify(viewProductInventoryService, times(1)).viewInventoryByProductId(productIds);
  }
}
