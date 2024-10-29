package com.rajat.inventory_service.service;

import com.rajat.inventory_service.dto.ViewProductInventoryResponse;
import com.rajat.inventory_service.model.Inventory;
import com.rajat.inventory_service.repository.QueryInventoryRepository;
import com.rajat.inventory_service.utils.InventoryUtils;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ViewProductInventoryServiceImpl implements ViewProductInventoryService {

  private final QueryInventoryRepository queryInventoryRepository;
  private final InventoryUtils inventoryUtils;

  @Override
  public List<ViewProductInventoryResponse> viewInventoryByProductId(
      final @NonNull Set<UUID> productIds) {
    List<Inventory> fetchedEntities = queryInventoryRepository.findAllByProductIdIn(productIds);
    return inventoryUtils.mapList(fetchedEntities, ViewProductInventoryResponse.class);
  }
}
