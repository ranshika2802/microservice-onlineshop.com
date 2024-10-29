package com.rajat.inventory_service.controller;

import com.rajat.inventory_service.dto.ViewProductInventoryResponse;
import com.rajat.inventory_service.service.ViewProductInventoryService;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InventoryQueryControllerImpl implements InventoryQueryController {
  private final ViewProductInventoryService viewProductInventoryService;

  @Override
  public List<ViewProductInventoryResponse> viewInventoryByProductId(
      @RequestParam @NonNull final Set<UUID> productIds) {
    return viewProductInventoryService.viewInventoryByProductId(productIds);
  }
}
