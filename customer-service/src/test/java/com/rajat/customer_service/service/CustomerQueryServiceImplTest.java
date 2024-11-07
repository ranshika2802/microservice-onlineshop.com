package com.rajat.customer_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.rajat.customer_service.dto.ApiResponse;
import com.rajat.customer_service.dto.ViewProductDetailsResponse;
import com.rajat.customer_service.utils.ApiResponseUtils;
import com.rajat.customer_service.utils.CustomerUtils;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CustomerQueryServiceImplTest {

  @Mock private CacheService cacheService;

  @Mock private ViewService viewService;

  @Mock private CustomerUtils customerUtils;

  @Mock private ApiResponseUtils apiResponseUtils;

  @InjectMocks private CustomerQueryServiceImpl customerQueryService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetProductDetailsByCategories_AllFromCache() {
    Set<String> categories = Set.of("Electronics", "Books");

    // Mock cache service to return products for given categories
    Map<String, Set<ViewProductDetailsResponse>> cachedProducts = new HashMap<>();
    cachedProducts.put("Electronics", Set.of(new ViewProductDetailsResponse()));
    when(cacheService.getProductsByCategories(categories)).thenReturn(cachedProducts);

    // Mock utils to return no uncached categories
    when(customerUtils.getUncachedCategories(categories, cachedProducts.keySet()))
        .thenReturn(Collections.emptySet());

    ApiResponse expectedResponse =
        new ApiResponse(true, Set.of("Data fetched successfully"), Set.of());
    when(apiResponseUtils.generateApiResponse(cachedProducts, null)).thenReturn(expectedResponse);

    // Call the service method
    ApiResponse actualResponse = customerQueryService.getProductDetailsByCategories(categories);

    // Assertions
    assertEquals(expectedResponse, actualResponse);
    verify(cacheService).getProductsByCategories(categories);
    verifyNoInteractions(viewService);
  }

  @Test
  void testGetProductDetailsByCategories_UncachedCategories() {
    Set<String> categories = Set.of("Electronics", "Books");
    Set<String> uncachedCategories = Set.of("Books");

    // Mock cache service to return cached data
    Map<String, Set<ViewProductDetailsResponse>> cachedProducts = new HashMap<>();
    cachedProducts.put("Electronics", Set.of(new ViewProductDetailsResponse()));
    when(cacheService.getProductsByCategories(categories)).thenReturn(cachedProducts);

    // Mock customerUtils to identify uncached categories
    when(customerUtils.getUncachedCategories(categories, cachedProducts.keySet()))
        .thenReturn(uncachedCategories);

    // Mock the view service to return data for uncached categories
    Map<String, Set<ViewProductDetailsResponse>> dbProducts = new HashMap<>();
    dbProducts.put("Books", Set.of(new ViewProductDetailsResponse()));
    when(viewService.fetchProductsByCategories(uncachedCategories)).thenReturn(dbProducts);

    // Mock API response generation
    ApiResponse expectedResponse =
        new ApiResponse(true, Set.of("Data fetched successfully"), Set.of());
    when(apiResponseUtils.generateApiResponse(cachedProducts, dbProducts))
        .thenReturn(expectedResponse);

    // Call the service method
    ApiResponse actualResponse = customerQueryService.getProductDetailsByCategories(categories);

    // Assertions for expected outcome
    verify(viewService).fetchProductsByCategories(uncachedCategories);

    // Awaitility to wait until `updateCache` is called
    Awaitility.await()
        .atMost(5, TimeUnit.SECONDS)
        .untilAsserted(
            () -> verify(cacheService, times(1)).updateCache(uncachedCategories, dbProducts));
  }

  @Test
  void testGetProductDetailsByCategories_InvalidKeysInDb() {
    Set<String> categories = Set.of("Electronics", "Books");
    Set<String> uncachedCategories = Set.of("Books");

    // Mock cache service with empty data
    Map<String, Set<ViewProductDetailsResponse>> cachedProducts = new HashMap<>();
    when(cacheService.getProductsByCategories(categories)).thenReturn(cachedProducts);

    // Mock customerUtils to return uncached categories
    when(customerUtils.getUncachedCategories(categories, cachedProducts.keySet()))
        .thenReturn(uncachedCategories);

    // Mock view service to return invalid keys in database response
    Map<String, Set<ViewProductDetailsResponse>> dbProducts = new HashMap<>();
    dbProducts.put("NAN", Set.of(new ViewProductDetailsResponse()));
    when(viewService.fetchProductsByCategories(uncachedCategories)).thenReturn(dbProducts);

    // Mock API response with invalid keys
    when(apiResponseUtils.containsInvalidKeys(dbProducts)).thenReturn(true);
    ApiResponse expectedResponse = new ApiResponse(false, dbProducts.keySet(), null);

    // Call the service method
    ApiResponse actualResponse = customerQueryService.getProductDetailsByCategories(categories);

    // Assertions
    assertEquals(expectedResponse, actualResponse);
    verify(viewService).fetchProductsByCategories(uncachedCategories);
    verify(cacheService, never()).updateCache(any(), any());
  }
}
