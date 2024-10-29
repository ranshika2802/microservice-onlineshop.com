package com.rajat.inventory_service.controller;

import com.rajat.inventory_service.dto.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;
import lombok.NonNull;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/inventory")
public interface InventoryCommandController {
  @PostMapping(value = "/create")
  List<AddProductInventoryResponse> addProductInventory(
      @Valid @RequestBody @NonNull
          final Set<AddProductInventoryRequest> addProductInventoryRequests);

  @PutMapping(value = "/update")
  List<UpdateProductInventoryResponse> updateProductInventory(
      @Valid @RequestBody @NonNull
          final Set<UpdateProductInventoryRequest> updateProductInventoryRequests);

  @DeleteMapping(value = "/delete")
  DeleteProductInventoryResponse deleteProductInventory(
      @Valid @RequestBody @NonNull
          final DeleteProductInventoryRequest deleteProductInventoryRequest);
}
