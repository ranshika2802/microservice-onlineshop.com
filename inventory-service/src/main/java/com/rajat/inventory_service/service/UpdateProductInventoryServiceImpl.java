package com.rajat.inventory_service.service;

import com.rajat.inventory_service.dto.UpdateProductInventoryRequest;
import com.rajat.inventory_service.dto.UpdateProductInventoryResponse;
import com.rajat.inventory_service.model.Inventory;
import com.rajat.inventory_service.repository.CommandInventoryRepository;
import com.rajat.inventory_service.utils.InventoryUtils;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Set;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateProductInventoryServiceImpl implements UpdateProductInventoryService {

  private final EntityManager entityManager;
  private final CommandInventoryRepository commandInventoryRepository;
  private final InventoryUtils inventoryUtils;

  @Override
  @Transactional
  public List<UpdateProductInventoryResponse> updateProductInventory(
      @NonNull final Set<UpdateProductInventoryRequest> updateProductInventoryRequests) {
    Set<Inventory> requestToModels =
        inventoryUtils.mapSet(updateProductInventoryRequests, Inventory.class);
    int recordCount = 0;
    try {
      for (Inventory inventory : requestToModels) {
        int rowsAffected =
            commandInventoryRepository.updateInventoryBYProductId(
                inventory.getTotal(), inventory.getReserved(), inventory.getProductId());
        if (rowsAffected == 1) recordCount++;
      }
    } finally {
      entityManager.flush();
      entityManager.clear();
    }

    return List.of(
        UpdateProductInventoryResponse.builder()
            .status("Total Records Updated= " + recordCount)
            .build());
  }
}
