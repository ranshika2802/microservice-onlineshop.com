package com.rajat.admin_service.service.view;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.*;

import com.rajat.admin_service.dto.response.retrieve.ApiResponse;
import com.rajat.admin_service.dto.response.retrieve.ViewProductDetailsResponseDto;
import com.rajat.admin_service.model.inventory.ApiInventoryClientResponse;
import com.rajat.admin_service.model.price.ApiPriceClientResponse;
import com.rajat.admin_service.model.product.ApiProductClientResponse;
import com.rajat.admin_service.model.product.response.ViewProductClientResponse;
import com.rajat.admin_service.util.AdminUtils;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ViewAdminServiceImplTest {

  @Mock private AdminUtils adminUtils;

  @Mock private ViewProductService viewProductService;

  @Mock private ViewPriceService viewPriceService;

  @Mock private ViewInventoryService viewInventoryService;

  @InjectMocks private ViewAdminServiceImpl viewAdminServiceImpl;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testViewProductsDetailsByCategorySuccess() {
    // Arrange
    Set<String> categories = Set.of("Electronics");
    Set<UUID> productIds = Set.of(UUID.randomUUID());

    ApiProductClientResponse productClientResponse = mock(ApiProductClientResponse.class);
    when(productClientResponse.isSuccess()).thenReturn(true);
    when(productClientResponse.getViewProductClientResponses()).thenReturn(Set.of());
    when(viewProductService.viewProductsByCategory(categories)).thenReturn(productClientResponse);

    ApiPriceClientResponse priceClientResponse = mock(ApiPriceClientResponse.class);
    when(priceClientResponse.isSuccess()).thenReturn(true);
    when(priceClientResponse.getViewProductPriceClientResponses()).thenReturn(Set.of());
    when(viewPriceService.viewPriceByProductId(productIds)).thenReturn(priceClientResponse);

    ApiInventoryClientResponse inventoryClientResponse = mock(ApiInventoryClientResponse.class);
    when(inventoryClientResponse.isSuccess()).thenReturn(true);
    when(inventoryClientResponse.getViewProductInventoryClientResponses()).thenReturn(Set.of());
    when(viewInventoryService.viewInventoryByProductId(productIds))
        .thenReturn(inventoryClientResponse);

    when(adminUtils.prepareProductIdToProductMap(anySet())).thenReturn(new HashMap<>());
    when(adminUtils.mergeResponses(
            productClientResponse, priceClientResponse, inventoryClientResponse))
        .thenReturn(Set.of());

    // Act
    ApiResponse response = viewAdminServiceImpl.viewProductsDetailsByCategory(categories);

    // Assert
    assertNotNull(response);
    assertTrue(response.isSuccess());
    assertNull(response.getMessage());
    assertNotNull(response.getDataSet());
  }

  @Test
  void testViewProductsDetailsByCategoryProductServiceFailure() {
    // Arrange
    Set<String> categories = Set.of("Electronics");

    ApiProductClientResponse productClientResponse = mock(ApiProductClientResponse.class);
    when(productClientResponse.isSuccess()).thenReturn(false);
    when(productClientResponse.getMessage()).thenReturn("Product Service NaN");
    when(productClientResponse.getViewProductClientResponses()).thenReturn(null);
    when(viewProductService.viewProductsByCategory(categories)).thenReturn(productClientResponse);

    // Act
    ApiResponse response = viewAdminServiceImpl.viewProductsDetailsByCategory(categories);

    // Assert
    assertNotNull(response);
    assertFalse(response.isSuccess());
    assertEquals(1, response.getMessage().size());
  }

  @Test
  void testViewProductsDetailsByCategoryNoProductsFound() {
    // Arrange
    Set<String> categories = Set.of("Electronics");

    ApiProductClientResponse productClientResponse = mock(ApiProductClientResponse.class);
    when(productClientResponse.isSuccess()).thenReturn(true);
    when(productClientResponse.getViewProductClientResponses()).thenReturn(Collections.emptySet());
    when(viewProductService.viewProductsByCategory(categories)).thenReturn(productClientResponse);

    when(adminUtils.prepareProductIdToProductMap(anySet())).thenReturn(new HashMap<>());

    // Act
    ApiResponse response = viewAdminServiceImpl.viewProductsDetailsByCategory(categories);

    // Assert
    assertNotNull(response);
    assertTrue(response.isSuccess());
    assertNull(response.getMessage());
    assertNotNull(response.getDataSet());
  }

  @Test
  void testViewProductsDetailsByCategoryPriceServiceFailure() {
    Set<String> categories = Set.of("Electronics");
    UUID productId = UUID.randomUUID();
    ViewProductDetailsResponseDto viewProductDetailsResponseDto =
        new ViewProductDetailsResponseDto();
    viewProductDetailsResponseDto.setId(productId);
    Map<UUID, ViewProductDetailsResponseDto> uuidViewProductDetailsResponseDtoMap = new HashMap<>();
    uuidViewProductDetailsResponseDtoMap.put(productId, viewProductDetailsResponseDto);
    ViewProductClientResponse viewProductClientResponse = new ViewProductClientResponse();
    viewProductClientResponse.setId(productId);
    when(adminUtils.prepareProductIdToProductMap(anySet()))
        .thenReturn(uuidViewProductDetailsResponseDtoMap);
    // Set up ProductService response with success
    ApiProductClientResponse productClientResponse = mock(ApiProductClientResponse.class);
    when(productClientResponse.isSuccess()).thenReturn(true);
    when(productClientResponse.getViewProductClientResponses())
        .thenReturn(Set.of(viewProductClientResponse));
    when(viewProductService.viewProductsByCategory(anySet())).thenReturn(productClientResponse);

    // Set up PriceService response with failure
    ApiPriceClientResponse priceClientResponse = mock(ApiPriceClientResponse.class);
    when(priceClientResponse.isSuccess()).thenReturn(false);
    when(priceClientResponse.getMessage()).thenReturn("Price service failure");
    when(viewPriceService.viewPriceByProductId(anySet())).thenReturn(priceClientResponse);

    // Execute the service method
    ApiResponse response = viewAdminServiceImpl.viewProductsDetailsByCategory(categories);

    // Assertions
    assertNotNull(response);
    assertFalse(
        response.isSuccess(), "Expected response to be unsuccessful due to price service failure");
    assertTrue(
        response.getMessage().contains("Price service failure"),
        "Expected message to indicate price service failure");
  }

  @Test
  void testViewProductsDetailsByCategoryInventoryServiceFailure() {
    Set<String> categories = Set.of("Electronics");
    UUID productId = UUID.randomUUID();
    ViewProductDetailsResponseDto viewProductDetailsResponseDto =
            new ViewProductDetailsResponseDto();
    viewProductDetailsResponseDto.setId(productId);
    Map<UUID, ViewProductDetailsResponseDto> uuidViewProductDetailsResponseDtoMap = new HashMap<>();
    uuidViewProductDetailsResponseDtoMap.put(productId, viewProductDetailsResponseDto);
    ViewProductClientResponse viewProductClientResponse = new ViewProductClientResponse();
    viewProductClientResponse.setId(productId);
    when(adminUtils.prepareProductIdToProductMap(anySet()))
            .thenReturn(uuidViewProductDetailsResponseDtoMap);

    // Set up ProductService response with success
    ApiProductClientResponse productClientResponse = mock(ApiProductClientResponse.class);
    when(productClientResponse.isSuccess()).thenReturn(true);
    when(productClientResponse.getViewProductClientResponses()).thenReturn(Set.of());
    when(viewProductService.viewProductsByCategory(categories)).thenReturn(productClientResponse);

    // Set up PriceService response with success
    ApiPriceClientResponse priceClientResponse = mock(ApiPriceClientResponse.class);
    when(priceClientResponse.isSuccess()).thenReturn(true);
    when(priceClientResponse.getViewProductPriceClientResponses()).thenReturn(Set.of());
    when(viewPriceService.viewPriceByProductId(anySet())).thenReturn(priceClientResponse);

    // Set up InventoryService response with failure
    ApiInventoryClientResponse inventoryClientResponse = mock(ApiInventoryClientResponse.class);
    when(inventoryClientResponse.isSuccess()).thenReturn(false);
    when(inventoryClientResponse.getMessage()).thenReturn("Inventory service failure");
    when(viewInventoryService.viewInventoryByProductId(anySet()))
        .thenReturn(inventoryClientResponse);

    // Execute the service method
    ApiResponse response = viewAdminServiceImpl.viewProductsDetailsByCategory(categories);

    // Assertions
    assertNotNull(response);
    assertFalse(
        response.isSuccess(),
        "Expected response to be unsuccessful due to inventory service failure");
    assertTrue(
        response.getMessage().contains("Inventory service failure"),
        "Expected message to indicate inventory service failure");
  }
}
