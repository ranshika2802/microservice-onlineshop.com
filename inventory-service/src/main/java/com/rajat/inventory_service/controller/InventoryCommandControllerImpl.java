package com.rajat.inventory_service.controller;

import com.rajat.inventory_service.dto.*;
import com.rajat.inventory_service.service.AddProductInventoryService;
import com.rajat.inventory_service.service.DeleteProductInventoryService;
import com.rajat.inventory_service.service.UpdateProductInventoryService;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
public class InventoryCommandControllerImpl implements InventoryCommandController {
  private final AddProductInventoryService addInventoryService;
  private final UpdateProductInventoryService updateInventoryService;
  private final DeleteProductInventoryService deleteProductInventoryService;

  @Override
  public List<AddProductInventoryResponse> addProductInventory(
      Set<AddProductInventoryRequest> addProductInventoryRequests) {
    return addInventoryService.addProductInventory(addProductInventoryRequests);
  }

  @Override
  public List<UpdateProductInventoryResponse> updateProductInventory(
      Set<UpdateProductInventoryRequest> updateProductInventoryRequests) {
    return updateInventoryService.updateProductInventory(updateProductInventoryRequests);
  }

  @Override
  public DeleteProductInventoryResponse deleteProductInventory(
      DeleteProductInventoryRequest deleteProductInventoryRequest) {
    return deleteProductInventoryService.deleteProductInventory(deleteProductInventoryRequest);
  }
}
