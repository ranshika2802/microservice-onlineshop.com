package com.rajat.admin_service.service.delete;

import com.rajat.admin_service.client.InventoryClient;
import com.rajat.admin_service.model.inventory.request.DeleteProductInventoryRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class DeleteInventoryServiceImplTest {

  @Mock private InventoryClient inventoryClient;

  @InjectMocks private DeleteInventoryServiceImpl deleteInventoryService;

  private DeleteProductInventoryRequest deleteProductInventoryRequest;

  @BeforeEach
  void setUp() {
    deleteProductInventoryRequest = new DeleteProductInventoryRequest();
    deleteProductInventoryRequest.setProductIds(Set.of(UUID.randomUUID(), UUID.randomUUID()));
  }

  @Test
  void testDeleteInventory_callsInventoryClient() {
    // Act
    deleteInventoryService.deleteInventory(deleteProductInventoryRequest);

    // Assert
    verify(inventoryClient, times(1)).deleteProductInventory(deleteProductInventoryRequest);
  }

  @Test
  void testDeleteInventory_withEmptyProductIds() {
    // Arrange
    deleteProductInventoryRequest.setProductIds(Set.of());

    // Act
    deleteInventoryService.deleteInventory(deleteProductInventoryRequest);

    // Assert
    verify(inventoryClient, times(1)).deleteProductInventory(deleteProductInventoryRequest);
  }

  @Test
  void testDeleteInventory_withSingleProductId() {
    // Arrange
    deleteProductInventoryRequest.setProductIds(Set.of(UUID.randomUUID()));

    // Act
    deleteInventoryService.deleteInventory(deleteProductInventoryRequest);

    // Assert
    verify(inventoryClient, times(1)).deleteProductInventory(deleteProductInventoryRequest);
  }
}
