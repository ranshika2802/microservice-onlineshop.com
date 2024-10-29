package com.rajat.inventory_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.rajat.inventory_service.dto.ViewProductInventoryResponse;
import com.rajat.inventory_service.model.Inventory;
import com.rajat.inventory_service.repository.QueryInventoryRepository;
import com.rajat.inventory_service.utils.InventoryUtils;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ViewProductInventoryServiceImplTest {

  @Mock private QueryInventoryRepository queryInventoryRepository;

  @Mock private InventoryUtils inventoryUtils;

  @InjectMocks private ViewProductInventoryServiceImpl viewProductInventoryService;

  private Set<UUID> productIds;
  private Inventory inventory;
  private ViewProductInventoryResponse inventoryResponse;

  @BeforeEach
  void setUp() {
    UUID productId = UUID.randomUUID();
    productIds = Set.of(productId);

    inventory = new Inventory(1L, 100, 10, productId);

    inventoryResponse = new ViewProductInventoryResponse();
    inventoryResponse.setInventoryId(1L);
    inventoryResponse.setProductId(productId);
    inventoryResponse.setTotal(100);
    inventoryResponse.setReserved(10);
  }

  @Test
  void testViewInventoryByProductId() {
    when(queryInventoryRepository.findAllByProductIdIn(productIds)).thenReturn(List.of(inventory));
    when(inventoryUtils.mapList(anyList(), eq(ViewProductInventoryResponse.class)))
        .thenReturn(List.of(inventoryResponse));

    List<ViewProductInventoryResponse> responses =
        viewProductInventoryService.viewInventoryByProductId(productIds);

    assertEquals(1, responses.size());
    assertEquals(inventoryResponse, responses.get(0));

    verify(queryInventoryRepository, times(1)).findAllByProductIdIn(productIds);
    verify(inventoryUtils, times(1)).mapList(anyList(), eq(ViewProductInventoryResponse.class));
  }
}
