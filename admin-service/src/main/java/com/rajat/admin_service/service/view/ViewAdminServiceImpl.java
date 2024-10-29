package com.rajat.admin_service.service.view;

import com.rajat.admin_service.dto.response.retrieve.ApiResponse;
import com.rajat.admin_service.dto.response.retrieve.ViewProductDetailsResponseDto;
import com.rajat.admin_service.model.inventory.ApiInventoryClientResponse;
import com.rajat.admin_service.model.price.ApiPriceClientResponse;
import com.rajat.admin_service.model.product.ApiProductClientResponse;
import com.rajat.admin_service.util.AdminUtils;
import java.util.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViewAdminServiceImpl implements ViewAdminService {

  private final AdminUtils adminUtils;
  private final ViewProductService viewProductService;
  private final ViewPriceService viewPriceService;
  private final ViewInventoryService viewInventoryService;

  @Override
  public ApiResponse viewProductsDetailsByCategory(@NonNull final Set<String> categories) {

    // Step 1: Fetch products and map them to DTO
    ApiProductClientResponse productClientResponse =
        viewProductService.viewProductsByCategory(categories);
    if (Objects.isNull(productClientResponse) || !productClientResponse.isSuccess()) {
      return new ApiResponse(
          productClientResponse.isSuccess(), Set.of(productClientResponse.getMessage()), null);
    }
    Map<UUID, ViewProductDetailsResponseDto> productIdToProductMap =
        adminUtils.prepareProductIdToProductMap(
            productClientResponse.getViewProductClientResponses());

    ApiPriceClientResponse priceClientResponse = null;
    ApiInventoryClientResponse inventoryClientResponse = null;

    Set<UUID> productIds = productIdToProductMap.keySet();
    if (!productIds.isEmpty()) {
      // Step 2: Fetch prices and map to products
      priceClientResponse = viewPriceService.viewPriceByProductId(productIds);
      if (Objects.isNull(priceClientResponse) || !priceClientResponse.isSuccess()) {
        return new ApiResponse(
            priceClientResponse.isSuccess(), Set.of(priceClientResponse.getMessage()), null);
      }
      if (Objects.nonNull(priceClientResponse.getViewProductPriceClientResponses())) {
        priceClientResponse
            .getViewProductPriceClientResponses()
            .forEach(
                priceResponse -> {
                  ViewProductDetailsResponseDto productDetails =
                      productIdToProductMap.get(priceResponse.getProductId());
                  if (productDetails != null) {
                    productDetails.setPrice(adminUtils.mapToPriceResponse(priceResponse));
                  }
                });
      }

      // Step 3: Fetch inventory and map to products
      inventoryClientResponse = viewInventoryService.viewInventoryByProductId(productIds);
      if (Objects.isNull(inventoryClientResponse) || !inventoryClientResponse.isSuccess()) {
        return new ApiResponse(
            inventoryClientResponse.isSuccess(),
            Set.of(inventoryClientResponse.getMessage()),
            null);
      }
      if (Objects.nonNull(inventoryClientResponse.getViewProductInventoryClientResponses())) {
        inventoryClientResponse
            .getViewProductInventoryClientResponses()
            .forEach(
                inventoryResponse -> {
                  ViewProductDetailsResponseDto productDetails =
                      productIdToProductMap.get(inventoryResponse.getProductId());
                  if (productDetails != null) {
                    productDetails.setInventory(
                        adminUtils.mapToInventoryResponse(inventoryResponse));
                  }
                });
      }
    }

    return new ApiResponse(
        true,
        null,
        adminUtils.mergeResponses(
            productClientResponse, priceClientResponse, inventoryClientResponse));
  }
}
