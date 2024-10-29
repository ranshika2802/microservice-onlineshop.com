package com.rajat.admin_service.service.update;

import com.rajat.admin_service.dto.request.update.UpdateInventoryDto;
import com.rajat.admin_service.dto.request.update.UpdatePriceDto;
import com.rajat.admin_service.dto.request.update.UpdateProductDetailsDto;
import com.rajat.admin_service.dto.request.update.UpdateProductDto;
import com.rajat.admin_service.dto.response.update.UpdateProductDetailsResponseDto;
import com.rajat.admin_service.model.inventory.request.UpdateProductInventoryClientRequest;
import com.rajat.admin_service.model.inventory.response.UpdateProductInventoryClientResponse;
import com.rajat.admin_service.model.price.request.UpdateProductPriceClientRequest;
import com.rajat.admin_service.model.price.response.UpdateProductPriceClientResponse;
import com.rajat.admin_service.model.product.request.UpdateProductClientRequest;
import com.rajat.admin_service.model.product.response.UpdateProductClientResponse;
import java.util.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
public class UpdateAdminServiceImpl implements UpdateAdminService {

  private final ModelMapper modelMapper;
  private final UpdateProductService updateProductService;
  private final UpdatePriceService updatePriceService;
  private final UpdateInventoryService updateInventoryService;

  @Override
  public Set<UpdateProductDetailsResponseDto> updateProductsDetails(
      @NonNull Set<UpdateProductDetailsDto> updateProductDetailsDtos) {
    Set<UpdateProductClientRequest> productRequests = new HashSet<>();
    Set<UpdateProductPriceClientRequest> priceRequests = new HashSet<>();
    Set<UpdateProductInventoryClientRequest> inventoryRequests = new HashSet<>();

    // Single iteration over product details to collect product, price, and inventory requests
    updateProductDetailsDtos.forEach(
        updateProductDetail -> {
          UpdateProductClientRequest productRequest = mapProduct(updateProductDetail.getProduct());
          productRequests.add(productRequest);

          if (Objects.nonNull(updateProductDetail.getPrice())) {
            UpdateProductPriceClientRequest priceRequest =
                mapPrice(updateProductDetail.getPrice(), productRequest.getId());
            priceRequests.add(priceRequest);
          }

          if (Objects.nonNull(updateProductDetail.getInventory())) {
            UpdateProductInventoryClientRequest inventoryRequest =
                mapInventory(updateProductDetail.getInventory(), productRequest.getId());
            inventoryRequests.add(inventoryRequest);
          }
        });

    // Call the respective services to add products, prices, and inventory
    if (!CollectionUtils.isEmpty(productRequests)) {
      Set<UpdateProductClientResponse> productResponses =
          updateProductService.updateProducts(productRequests);
    }
    if (!CollectionUtils.isEmpty(priceRequests)) {
      Set<UpdateProductPriceClientResponse> priceResponses =
          updatePriceService.updateProductPrice(priceRequests);
    }
    if (!CollectionUtils.isEmpty(inventoryRequests)) {
      Set<UpdateProductInventoryClientResponse> inventoryResponses =
          updateInventoryService.updateProductInventory(inventoryRequests);
    }

    return Set.of(UpdateProductDetailsResponseDto.builder().status("Product updated.").build());
  }

  /** Maps product DTO to client request and generates a new UUID for each product. */
  private UpdateProductClientRequest mapProduct(UpdateProductDto product) {
    UpdateProductClientRequest productClientRequest =
        modelMapper.map(product, UpdateProductClientRequest.class);
    return productClientRequest;
  }

  /** Maps price DTO to client request and sets the product ID. */
  private UpdateProductPriceClientRequest mapPrice(UpdatePriceDto price, UUID productId) {
    UpdateProductPriceClientRequest priceClientRequest =
        modelMapper.map(price, UpdateProductPriceClientRequest.class);
    priceClientRequest.setProductId(productId);
    return priceClientRequest;
  }

  /** Maps inventory DTO to client request and sets the product ID. */
  private UpdateProductInventoryClientRequest mapInventory(
      UpdateInventoryDto inventory, UUID productId) {
    UpdateProductInventoryClientRequest inventoryClientRequest =
        modelMapper.map(inventory, UpdateProductInventoryClientRequest.class);
    inventoryClientRequest.setProductId(productId);
    return inventoryClientRequest;
  }
}
