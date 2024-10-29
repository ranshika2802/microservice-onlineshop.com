package com.rajat.admin_service.service.update;

import com.rajat.admin_service.model.inventory.request.UpdateProductInventoryClientRequest;
import com.rajat.admin_service.model.inventory.response.UpdateProductInventoryClientResponse;

import java.util.Set;

public interface UpdateInventoryService {
  Set<UpdateProductInventoryClientResponse> updateProductInventory(
      Set<UpdateProductInventoryClientRequest> inventoryRequests);
}
