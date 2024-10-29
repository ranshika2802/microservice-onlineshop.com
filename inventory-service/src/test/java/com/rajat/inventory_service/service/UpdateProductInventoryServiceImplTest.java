package com.rajat.inventory_service.service;

import com.rajat.inventory_service.dto.UpdateProductInventoryRequest;
import com.rajat.inventory_service.dto.UpdateProductInventoryResponse;
import com.rajat.inventory_service.model.Inventory;
import com.rajat.inventory_service.repository.CommandInventoryRepository;
import com.rajat.inventory_service.utils.InventoryUtils;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateProductInventoryServiceImplTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private CommandInventoryRepository commandInventoryRepository;

    @Mock
    private InventoryUtils inventoryUtils;  // Change to @Mock instead of @Spy

    @InjectMocks
    private UpdateProductInventoryServiceImpl updateProductInventoryService;

    private Set<UpdateProductInventoryRequest> updateProductInventoryRequests;
    private Inventory inventory;

    @BeforeEach
    void setUp() {
        UUID productId = UUID.randomUUID();
        updateProductInventoryRequests = Set.of(
                new UpdateProductInventoryRequest(1L, productId, 100, 10)
        );
        inventory = new Inventory(1L, 100, 10, productId);
    }

    @Test
    void testUpdateProductInventory() {
        // Arrange: Stub the mapping behavior of InventoryUtils to return expected inventory
        when(inventoryUtils.mapSet(any(), eq(Inventory.class)))
                .thenReturn(Set.of(inventory));

        // Mock repository behavior to simulate one record being updated
        when(commandInventoryRepository.updateInventoryBYProductId(
                inventory.getTotal(), inventory.getReserved(), inventory.getProductId()))
                .thenReturn(1);

        // Act: Call the method to test
        List<UpdateProductInventoryResponse> responses =
                updateProductInventoryService.updateProductInventory(updateProductInventoryRequests);

        // Assert: Verify outcomes and interactions
        assertEquals(1, responses.size());
        assertEquals("Total Records Updated= 1", responses.get(0).getStatus());

        // Verify interactions with repository and entity manager
        verify(commandInventoryRepository, times(1))
                .updateInventoryBYProductId(
                        inventory.getTotal(), inventory.getReserved(), inventory.getProductId());

        verify(entityManager, times(1)).flush();
        verify(entityManager, times(1)).clear();

        // Verify the mapping method is called once
        verify(inventoryUtils, times(1)).mapSet(any(), eq(Inventory.class));
    }
}
