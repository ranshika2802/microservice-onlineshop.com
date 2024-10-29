package com.rajat.admin_service.util;

import com.rajat.admin_service.dto.response.retrieve.*;
import com.rajat.admin_service.model.inventory.ApiInventoryClientResponse;
import com.rajat.admin_service.model.inventory.response.ViewProductInventoryClientResponse;
import com.rajat.admin_service.model.price.ApiPriceClientResponse;
import com.rajat.admin_service.model.price.response.ViewProductPriceClientResponse;
import com.rajat.admin_service.model.product.ApiProductClientResponse;
import com.rajat.admin_service.model.product.response.ViewProductClientResponse;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminUtils {

  private final ModelMapper modelMapper;

  // Prepare Product Map: Keeps the responsibility focused on transforming product data.
  public Map<UUID, ViewProductDetailsResponseDto> prepareProductIdToProductMap(
      Set<ViewProductClientResponse> productResponses) {
    return productResponses.stream()
        .collect(Collectors.toMap(ViewProductClientResponse::getId, this::mapToProductDetails));
  }

  // Map a single product response to ViewProductDetailsResponseDto.
  ViewProductDetailsResponseDto mapToProductDetails(ViewProductClientResponse productResponse) {
    ViewProductDetailsResponseDto productDetails =
        modelMapper.map(productResponse, ViewProductDetailsResponseDto.class);

    // If attributes exist, map them using the utility method.
    if (productResponse.getAttributes() != null) {
      Set<ViewProductAttributeResponseDto> attributes =
          mapSet(productResponse.getAttributes(), ViewProductAttributeResponseDto.class);
      productDetails.setAttributes(attributes);
    }
    return productDetails;
  }

  // Map price response to ViewPriceResponseDto.
  public ViewPriceResponseDto mapToPriceResponse(ViewProductPriceClientResponse priceResponse) {
    return modelMapper.map(priceResponse, ViewPriceResponseDto.class);
  }

  // Map inventory response to ViewInventoryResponseDto.
  public ViewInventoryResponseDto mapToInventoryResponse(
      ViewProductInventoryClientResponse inventoryResponse) {
    return modelMapper.map(inventoryResponse, ViewInventoryResponseDto.class);
  }

  // Utility to map a set of source objects to target DTOs.
  <S, T> Set<T> mapSet(Set<S> source, Class<T> targetClass) {
    return source.stream()
        .map(element -> modelMapper.map(element, targetClass))
        .collect(Collectors.toUnmodifiableSet());
  }

  // Main merging method: merges product, price, and inventory data.
  public Set<ViewProductDetailsResponseDto> mergeResponses(
      ApiProductClientResponse productClientResponse,
      ApiPriceClientResponse priceClientResponse,
      ApiInventoryClientResponse inventoryClientResponse) {

    // Get the map of product details by productId.
    Map<UUID, ViewProductDetailsResponseDto> productDetailsMap =
        prepareProductIdToProductMap(productClientResponse.getViewProductClientResponses());

    // Merge price details into the product details map.
    mergePriceData(priceClientResponse.getViewProductPriceClientResponses(), productDetailsMap);

    // Merge inventory details into the product details map.
    mergeInventoryData(
        inventoryClientResponse.getViewProductInventoryClientResponses(), productDetailsMap);

    return new HashSet<>(productDetailsMap.values());
  }

  // Merges the price responses into the product details map.
  private void mergePriceData(
      Set<ViewProductPriceClientResponse> priceResponses,
      Map<UUID, ViewProductDetailsResponseDto> productDetailsMap) {

    for (ViewProductPriceClientResponse priceResponse : priceResponses) {
      ViewProductDetailsResponseDto productDetails =
          productDetailsMap.get(priceResponse.getProductId());
      if (productDetails != null) {
        ViewPriceResponseDto priceDto = mapToPriceResponse(priceResponse);
        productDetails.setPrice(priceDto);
      }
    }
  }

  // Merges the inventory responses into the product details map.
  private void mergeInventoryData(
      Set<ViewProductInventoryClientResponse> inventoryResponses,
      Map<UUID, ViewProductDetailsResponseDto> productDetailsMap) {

    for (ViewProductInventoryClientResponse inventoryResponse : inventoryResponses) {
      ViewProductDetailsResponseDto productDetails =
          productDetailsMap.get(inventoryResponse.getProductId());
      if (productDetails != null) {
        ViewInventoryResponseDto inventoryDto = mapToInventoryResponse(inventoryResponse);
        productDetails.setInventory(inventoryDto);
      }
    }
  }
}
