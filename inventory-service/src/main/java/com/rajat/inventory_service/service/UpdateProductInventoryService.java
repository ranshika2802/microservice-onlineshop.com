package com.rajat.inventory_service.service;

import com.rajat.inventory_service.dto.UpdateProductInventoryRequest;
import com.rajat.inventory_service.dto.UpdateProductInventoryResponse;

import java.util.List;
import java.util.Set;

public interface UpdateProductInventoryService {
    List<UpdateProductInventoryResponse> updateProductInventory(Set<UpdateProductInventoryRequest> updateProductInventoryRequests);
}
