package com.rajat.admin_service.service.update;

import com.rajat.admin_service.client.InventoryClient;
import com.rajat.admin_service.model.inventory.request.UpdateProductInventoryClientRequest;
import com.rajat.admin_service.model.inventory.response.UpdateProductInventoryClientResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UpdateInventoryServiceImplTest {

  @Mock private InventoryClient inventoryClient;

  @InjectMocks private UpdateInventoryServiceImpl updateInventoryService;

  private UpdateProductInventoryClientRequest inventoryRequest;

  @BeforeEach
  void setUp() {
    inventoryRequest = new UpdateProductInventoryClientRequest();
    inventoryRequest.setProductId(UUID.randomUUID());
    inventoryRequest.setTotal(100);
    inventoryRequest.setReserved(10);
  }

  @Test
  void testUpdateProductInventory() {
    Set<UpdateProductInventoryClientRequest> inventoryRequests = Set.of(inventoryRequest);

    UpdateProductInventoryClientResponse expectedResponse =
        new UpdateProductInventoryClientResponse();
    expectedResponse.setStatus("Success");

    Set<UpdateProductInventoryClientResponse> expectedResponses = Set.of(expectedResponse);

    when(inventoryClient.updateProductInventory(inventoryRequests)).thenReturn(expectedResponses);

    Set<UpdateProductInventoryClientResponse> actualResponses =
        updateInventoryService.updateProductInventory(inventoryRequests);

    verify(inventoryClient).updateProductInventory(inventoryRequests);
    assertEquals(expectedResponses, actualResponses);
  }
}
