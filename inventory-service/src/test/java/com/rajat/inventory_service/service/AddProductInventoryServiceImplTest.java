package com.rajat.inventory_service.service;

import com.rajat.inventory_service.dto.AddProductInventoryRequest;
import com.rajat.inventory_service.dto.AddProductInventoryResponse;
import com.rajat.inventory_service.model.Inventory;
import com.rajat.inventory_service.repository.CommandInventoryRepository;
import com.rajat.inventory_service.service.AddProductInventoryServiceImpl;
import com.rajat.inventory_service.utils.InventoryUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class AddProductInventoryServiceImplTest {

  @Mock
  private CommandInventoryRepository commandInventoryRepository;

  @Mock
  private InventoryUtils inventoryUtils;

  @Mock
  private EntityManager entityManager;

  @InjectMocks
  private AddProductInventoryServiceImpl addProductInventoryService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testAddProductInventory() {
    // Arrange
    UUID productId = UUID.randomUUID();
    AddProductInventoryRequest request = new AddProductInventoryRequest(1L, productId, 10, 2);
    Set<AddProductInventoryRequest> requests = Set.of(request);

    Inventory inventory = new Inventory();
    inventory.setProductId(productId);
    inventory.setTotal(10);
    inventory.setReserved(2);
    Set<Inventory> inventories = Set.of(inventory);

    when(inventoryUtils.mapSet(requests, Inventory.class)).thenReturn(inventories);
    when(commandInventoryRepository.insertInventory(anyInt(), anyInt(), any(UUID.class))).thenReturn(1);

    List<AddProductInventoryResponse> response = addProductInventoryService.addProductInventory(requests);

    // Assert
    assertEquals(1, response.size());
    assertEquals("Total Records inserted = 1", response.get(0).getStatus());

    // Verify interactions
    verify(inventoryUtils, times(1)).mapSet(requests, Inventory.class);
    verify(commandInventoryRepository, times(1)).insertInventory(anyInt(), anyInt(), any(UUID.class));
    verify(entityManager, times(1)).flush();
    verify(entityManager, times(1)).clear();
  }
}
