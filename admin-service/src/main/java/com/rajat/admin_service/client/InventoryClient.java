package com.rajat.admin_service.client;

import com.rajat.admin_service.model.inventory.request.AddProductInventoryClientRequest;
import com.rajat.admin_service.model.inventory.request.DeleteProductInventoryRequest;
import com.rajat.admin_service.model.inventory.request.UpdateProductInventoryClientRequest;
import com.rajat.admin_service.model.inventory.response.AddProductInventoryClientResponse;
import com.rajat.admin_service.model.inventory.response.DeleteProductInventoryResponse;
import com.rajat.admin_service.model.inventory.response.UpdateProductInventoryClientResponse;
import com.rajat.admin_service.model.inventory.response.ViewProductInventoryClientResponse;

import java.util.Set;
import java.util.UUID;
import lombok.NonNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "inventory-service/v1/inventory"/*, url = "http://localhost:9093/v1/inventory"*/)
public interface InventoryClient {
  @PostMapping(value = "/create")
  Set<AddProductInventoryClientResponse> addProductInventory(
      @RequestBody @NonNull
          final Set<AddProductInventoryClientRequest> addProductInventoryClientRequests);

  @PutMapping(value = "/update")
  Set<UpdateProductInventoryClientResponse> updateProductInventory(
      @RequestBody @NonNull
          final Set<UpdateProductInventoryClientRequest> updateProductInventoryClientRequests);

  @DeleteMapping(value = "/delete")
  DeleteProductInventoryResponse deleteProductInventory(
      @RequestBody @NonNull final DeleteProductInventoryRequest deleteProductInventoryRequest);

  @GetMapping(value = "/query")
  Set<ViewProductInventoryClientResponse> viewInventoryByProductId(
      @RequestParam @NonNull final Set<UUID> productIds);
}
