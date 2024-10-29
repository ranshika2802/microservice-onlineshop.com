package com.rajat.inventory_service.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.rajat.inventory_service.dto.UpdateProductInventoryRequest;
import com.rajat.inventory_service.model.Inventory;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class InventoryUtilsTest {

  @Mock private ModelMapper modelMapper;

  @Spy @InjectMocks private InventoryUtils inventoryUtils;

  private UpdateProductInventoryRequest updateProductInventoryRequest;
  private Inventory inventory;

  @BeforeEach
  void setUp() {
    UUID productId = UUID.randomUUID();
    updateProductInventoryRequest = new UpdateProductInventoryRequest(1L, productId, 100, 10);
    inventory = new Inventory(1L, 100, 10, productId);
  }

  @Test
  void testMapSet() {
    when(modelMapper.map(any(UpdateProductInventoryRequest.class), eq(Inventory.class)))
        .thenReturn(inventory);

    Set<UpdateProductInventoryRequest> requestSet = Set.of(updateProductInventoryRequest);

    Set<Inventory> inventorySet = inventoryUtils.mapSet(requestSet, Inventory.class);

    assertEquals(1, inventorySet.size());
    assertEquals(inventory, inventorySet.iterator().next());
  }

  @Test
  void testMapList() {
    when(modelMapper.map(any(UpdateProductInventoryRequest.class), eq(Inventory.class)))
        .thenReturn(inventory);

    List<UpdateProductInventoryRequest> requestList = List.of(updateProductInventoryRequest);

    List<Inventory> inventoryList = inventoryUtils.mapList(requestList, Inventory.class);

    assertEquals(1, inventoryList.size());
    assertEquals(inventory, inventoryList.get(0));
  }
}
