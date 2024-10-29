package com.rajat.admin_service.service.delete;

import com.rajat.admin_service.dto.request.delete.DeleteProductAttributesDto;
import com.rajat.admin_service.dto.request.delete.DeleteProductDetailsDto;
import com.rajat.admin_service.dto.response.delete.DeleteProductDetailsResponseDto;
import com.rajat.admin_service.model.inventory.request.DeleteProductInventoryRequest;
import com.rajat.admin_service.model.product.request.DeleteProductAttributesRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeleteAdminServiceImpl implements DeleteAdminService {

  private final ModelMapper modelMapper;
  private final DeleteProductService deleteProductService;
  private final DeletePriceService deletePriceService;
  private final DeleteInventoryService deleteInventoryService;

  @Override
  public DeleteProductDetailsResponseDto deleteProductDetails(
      @NonNull DeleteProductDetailsDto deleteProductDetailsDto) {

    deletePriceService.deletePrice(deleteProductDetailsDto);
    DeleteProductInventoryRequest deleteProductInventoryRequest =
        modelMapper.map(deleteProductDetailsDto, DeleteProductInventoryRequest.class);
    log.info("Model Mapper = {}", deleteProductInventoryRequest);
    deleteInventoryService.deleteInventory(deleteProductInventoryRequest);
    deleteProductService.deleteProduct(deleteProductDetailsDto);

    return DeleteProductDetailsResponseDto.builder().status("Product Deleted.").build();
  }

  @Override
  public DeleteProductDetailsResponseDto deleteProductAttributes(
      @NonNull DeleteProductAttributesDto deleteProductAttributesDto) {
    DeleteProductAttributesRequest deleteProductAttributesRequest =
        modelMapper.map(deleteProductAttributesDto, DeleteProductAttributesRequest.class);
    deleteProductService.deleteProductAttributes(deleteProductAttributesRequest);
    return DeleteProductDetailsResponseDto.builder().status("Product Attribute deleted.").build();
  }
}
