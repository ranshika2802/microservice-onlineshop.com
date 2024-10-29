package com.rajat.inventory_service.service;

import com.rajat.inventory_service.dto.ViewProductInventoryRequest;
import com.rajat.inventory_service.dto.ViewProductInventoryResponse;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import lombok.NonNull;

public interface ViewProductInventoryService {
  List<ViewProductInventoryResponse> viewInventoryByProductId(@NonNull final Set<UUID> productIds);
}
