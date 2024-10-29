package com.rajat.inventory_service.service;

import com.rajat.inventory_service.dto.AddProductInventoryRequest;
import com.rajat.inventory_service.dto.AddProductInventoryResponse;

import java.util.List;
import java.util.Set;

public interface AddProductInventoryService {
    List<AddProductInventoryResponse> addProductInventory(Set<AddProductInventoryRequest> addProductInventoryRequests);
}
