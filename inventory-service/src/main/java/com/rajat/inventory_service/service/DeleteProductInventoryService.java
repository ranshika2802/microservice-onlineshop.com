package com.rajat.inventory_service.service;

import com.rajat.inventory_service.dto.DeleteProductInventoryRequest;
import com.rajat.inventory_service.dto.DeleteProductInventoryResponse;

public interface DeleteProductInventoryService {
    DeleteProductInventoryResponse deleteProductInventory(DeleteProductInventoryRequest deleteProductInventoryRequest);
}
