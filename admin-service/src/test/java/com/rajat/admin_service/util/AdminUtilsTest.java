package com.rajat.admin_service.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.rajat.admin_service.dto.response.retrieve.*;
import com.rajat.admin_service.model.inventory.ApiInventoryClientResponse;
import com.rajat.admin_service.model.inventory.response.ViewProductInventoryClientResponse;
import com.rajat.admin_service.model.price.ApiPriceClientResponse;
import com.rajat.admin_service.model.price.response.ViewProductPriceClientResponse;
import com.rajat.admin_service.model.product.ApiProductClientResponse;
import com.rajat.admin_service.model.product.response.ViewProductClientResponse;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

class AdminUtilsTest {

  @Mock private ModelMapper modelMapper;

  @InjectMocks private AdminUtils adminUtils;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testPrepareProductIdToProductMap() {
    UUID productId = UUID.randomUUID();
    ViewProductClientResponse productResponse = new ViewProductClientResponse();
    productResponse.setId(productId);

    ViewProductDetailsResponseDto productDetailsDto = new ViewProductDetailsResponseDto();
    when(modelMapper.map(productResponse, ViewProductDetailsResponseDto.class))
        .thenReturn(productDetailsDto);

    Map<UUID, ViewProductDetailsResponseDto> result =
        adminUtils.prepareProductIdToProductMap(Set.of(productResponse));

    assertEquals(productDetailsDto, result.get(productId));
  }

  @Test
  void testMapToProductDetails() {
    ViewProductClientResponse productResponse = new ViewProductClientResponse();
    ViewProductDetailsResponseDto productDetailsDto = new ViewProductDetailsResponseDto();
    when(modelMapper.map(productResponse, ViewProductDetailsResponseDto.class))
        .thenReturn(productDetailsDto);

    ViewProductDetailsResponseDto result = adminUtils.mapToProductDetails(productResponse);

    assertEquals(productDetailsDto, result);
  }

  @Test
  void testMapToPriceResponse() {
    ViewProductPriceClientResponse priceResponse = new ViewProductPriceClientResponse();
    ViewPriceResponseDto priceDto = new ViewPriceResponseDto();
    when(modelMapper.map(priceResponse, ViewPriceResponseDto.class)).thenReturn(priceDto);

    ViewPriceResponseDto result = adminUtils.mapToPriceResponse(priceResponse);

    assertEquals(priceDto, result);
  }

  @Test
  void testMapToInventoryResponse() {
    ViewProductInventoryClientResponse inventoryResponse = new ViewProductInventoryClientResponse();
    ViewInventoryResponseDto inventoryDto = new ViewInventoryResponseDto();
    when(modelMapper.map(inventoryResponse, ViewInventoryResponseDto.class))
        .thenReturn(inventoryDto);

    ViewInventoryResponseDto result = adminUtils.mapToInventoryResponse(inventoryResponse);

    assertEquals(inventoryDto, result);
  }

  @Test
  void testMergeResponses() {
    UUID productId = UUID.randomUUID();

    // Prepare Product Response
    ViewProductClientResponse productResponse = new ViewProductClientResponse();
    productResponse.setId(productId);
    ViewProductDetailsResponseDto productDetailsDto = new ViewProductDetailsResponseDto();
    when(modelMapper.map(productResponse, ViewProductDetailsResponseDto.class))
        .thenReturn(productDetailsDto);
    ApiProductClientResponse productClientResponse =
        new ApiProductClientResponse(true, null, Set.of(productResponse));

    // Prepare Price Response
    ViewProductPriceClientResponse priceResponse = new ViewProductPriceClientResponse();
    priceResponse.setProductId(productId);
    ViewPriceResponseDto priceDto = new ViewPriceResponseDto();
    when(modelMapper.map(priceResponse, ViewPriceResponseDto.class)).thenReturn(priceDto);
    ApiPriceClientResponse priceClientResponse =
        new ApiPriceClientResponse(true, null, Set.of(priceResponse));

    // Prepare Inventory Response
    ViewProductInventoryClientResponse inventoryResponse = new ViewProductInventoryClientResponse();
    inventoryResponse.setProductId(productId);
    ViewInventoryResponseDto inventoryDto = new ViewInventoryResponseDto();
    when(modelMapper.map(inventoryResponse, ViewInventoryResponseDto.class))
        .thenReturn(inventoryDto);
    ApiInventoryClientResponse inventoryClientResponse =
        new ApiInventoryClientResponse(true, null, Set.of(inventoryResponse));

    // Execute mergeResponses
    Set<ViewProductDetailsResponseDto> result =
        adminUtils.mergeResponses(
            productClientResponse, priceClientResponse, inventoryClientResponse);

    ViewProductDetailsResponseDto mergedProduct = result.iterator().next();
    assertEquals(priceDto, mergedProduct.getPrice());
    assertEquals(inventoryDto, mergedProduct.getInventory());
  }
}
