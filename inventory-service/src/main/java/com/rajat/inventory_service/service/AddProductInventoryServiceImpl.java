package com.rajat.inventory_service.service;

import com.rajat.inventory_service.dto.AddProductInventoryRequest;
import com.rajat.inventory_service.dto.AddProductInventoryResponse;
import com.rajat.inventory_service.model.Inventory;
import com.rajat.inventory_service.repository.CommandInventoryRepository;
import com.rajat.inventory_service.utils.InventoryUtils;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddProductInventoryServiceImpl implements AddProductInventoryService {

  private final CommandInventoryRepository commandInventoryRepository;
  private final InventoryUtils inventoryUtils;
  private final EntityManager entityManager;

  @Override
  @Transactional
  public List<AddProductInventoryResponse> addProductInventory(
      Set<AddProductInventoryRequest> addProductInventoryRequests) {
    Set<Inventory> requestToModels = inventoryUtils.mapSet(addProductInventoryRequests, Inventory.class);
    int recordCount = 0;
    try {
      for (Inventory inventory : requestToModels) {
        int rowsAffected =
            commandInventoryRepository.insertInventory(
                inventory.getTotal(), inventory.getReserved(), inventory.getProductId());
        if (rowsAffected == 1) recordCount++;
      }
    } finally {
      entityManager.flush();
      entityManager.clear();
    }

    return List.of(
        AddProductInventoryResponse.builder().status("Total Records inserted = " + recordCount).build());
  }
}
