package com.rajat.admin_service.service.delete;

import com.rajat.admin_service.dto.request.delete.DeleteProductDetailsDto;
import com.rajat.admin_service.model.inventory.request.DeleteProductInventoryRequest;
import lombok.NonNull;

public interface DeleteInventoryService {
    void deleteInventory(final @NonNull DeleteProductInventoryRequest deleteProductInventoryRequest);
}
