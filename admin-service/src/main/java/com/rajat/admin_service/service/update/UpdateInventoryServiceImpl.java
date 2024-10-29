package com.rajat.admin_service.service.update;

import com.rajat.admin_service.client.InventoryClient;
import com.rajat.admin_service.model.inventory.request.UpdateProductInventoryClientRequest;
import com.rajat.admin_service.model.inventory.response.UpdateProductInventoryClientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UpdateInventoryServiceImpl implements UpdateInventoryService {

    private final InventoryClient inventoryClient;
    @Override
    public Set<UpdateProductInventoryClientResponse> updateProductInventory(Set<UpdateProductInventoryClientRequest> inventoryRequests) {
        return inventoryClient.updateProductInventory(inventoryRequests);
    }
}
