package com.rajat.inventory_service.controller;

import com.rajat.inventory_service.dto.ViewProductInventoryResponse;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/v1/inventory")
public interface InventoryQueryController {
  @GetMapping(value = "/query")
  List<ViewProductInventoryResponse> viewInventoryByProductId(
      @RequestParam @NonNull final Set<UUID> productIds);
}
