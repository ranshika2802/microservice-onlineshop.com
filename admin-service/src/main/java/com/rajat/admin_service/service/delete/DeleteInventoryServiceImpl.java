package com.rajat.admin_service.service.delete;

import com.rajat.admin_service.client.InventoryClient;
import com.rajat.admin_service.client.PriceClient;
import com.rajat.admin_service.dto.request.delete.DeleteProductDetailsDto;
import com.rajat.admin_service.model.inventory.request.DeleteProductInventoryRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteInventoryServiceImpl implements DeleteInventoryService {

    private final InventoryClient inventoryClient;

    @Override
    public void deleteInventory(final @NonNull DeleteProductInventoryRequest deleteProductInventoryRequest) {
        inventoryClient.deleteProductInventory(deleteProductInventoryRequest);
    }
}
