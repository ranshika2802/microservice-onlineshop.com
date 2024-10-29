package com.rajat.admin_service.service.add;

import com.rajat.admin_service.model.inventory.request.AddProductInventoryClientRequest;
import com.rajat.admin_service.model.inventory.response.AddProductInventoryClientResponse;

import java.util.Set;

public interface AddInventoryService {
  Set<AddProductInventoryClientResponse> addProductInventory(
      Set<AddProductInventoryClientRequest> addProductInventoryClientRequests);
}
