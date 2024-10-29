package com.rajat.inventory_service.service;

import com.rajat.inventory_service.dto.DeleteProductInventoryRequest;
import com.rajat.inventory_service.dto.DeleteProductInventoryResponse;
import com.rajat.inventory_service.repository.CommandInventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteProductInventoryServiceImpl implements DeleteProductInventoryService {

  private final CommandInventoryRepository commandInventoryRepository;

  @Override
  @Transactional
  public DeleteProductInventoryResponse deleteProductInventory(
      DeleteProductInventoryRequest deleteProductInventoryRequest) {
    int rowsDeleted =
        commandInventoryRepository.deleteAllByProductIdIn(
            deleteProductInventoryRequest.getProductIds());
    DeleteProductInventoryResponse response = new DeleteProductInventoryResponse();
    response.setStatus("Total records deleted = " + rowsDeleted);
    return response;
  }
}
