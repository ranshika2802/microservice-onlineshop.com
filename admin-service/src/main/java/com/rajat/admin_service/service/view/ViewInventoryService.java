package com.rajat.admin_service.service.view;

import com.rajat.admin_service.dto.response.retrieve.ApiResponse;
import com.rajat.admin_service.model.inventory.ApiInventoryClientResponse;
import com.rajat.admin_service.model.inventory.response.ViewProductInventoryClientResponse;

import java.util.Set;
import java.util.UUID;

public interface ViewInventoryService {
    ApiInventoryClientResponse viewInventoryByProductId(Set<UUID> productIds);
}
