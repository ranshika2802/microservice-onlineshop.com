package com.rajat.admin_service.service.add;

import com.rajat.admin_service.client.InventoryClient;
import com.rajat.admin_service.model.inventory.request.AddProductInventoryClientRequest;
import com.rajat.admin_service.model.inventory.response.AddProductInventoryClientResponse;
import com.rajat.admin_service.util.AdminUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AddInventoryServiceImpl implements AddInventoryService {

    private final InventoryClient inventoryClient;
    private final AdminUtils adminUtils;
    @Override
    public Set<AddProductInventoryClientResponse> addProductInventory(Set<AddProductInventoryClientRequest> addProductInventoryClientRequests) {
        return inventoryClient.addProductInventory(addProductInventoryClientRequests);
    }
}
