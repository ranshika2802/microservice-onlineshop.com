package com.rajat.inventory_service.service;

import com.rajat.inventory_service.dto.DeleteProductInventoryRequest;
import com.rajat.inventory_service.dto.DeleteProductInventoryResponse;
import com.rajat.inventory_service.repository.CommandInventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteProductInventoryServiceImplTest {

  @Mock private CommandInventoryRepository commandInventoryRepository;

  @InjectMocks private DeleteProductInventoryServiceImpl deleteProductInventoryService;

  private DeleteProductInventoryRequest deleteProductInventoryRequest;

  @BeforeEach
  void setUp() {
    deleteProductInventoryRequest = new DeleteProductInventoryRequest();
    deleteProductInventoryRequest.setProductIds(Set.of(UUID.randomUUID(), UUID.randomUUID()));
  }

  @Test
  void testDeleteProductInventory() {
    when(commandInventoryRepository.deleteAllByProductIdIn(
            deleteProductInventoryRequest.getProductIds()))
        .thenReturn(2);

    DeleteProductInventoryResponse response =
        deleteProductInventoryService.deleteProductInventory(deleteProductInventoryRequest);

    assertEquals("Total records deleted = 2", response.getStatus());

    verify(commandInventoryRepository, times(1))
        .deleteAllByProductIdIn(deleteProductInventoryRequest.getProductIds());
  }
}
