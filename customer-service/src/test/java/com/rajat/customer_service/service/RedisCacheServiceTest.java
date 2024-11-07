package com.rajat.customer_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.rajat.customer_service.dto.ViewCategoryResponse;
import com.rajat.customer_service.dto.ViewProductDetailsResponse;
import com.rajat.customer_service.helper.CacheHelper;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheManager;

@ExtendWith(MockitoExtension.class)
public class RedisCacheServiceTest {

  @InjectMocks private RedisCacheService redisCacheService;

  @Mock private RedisCacheManager cacheManager;

  @Mock private CacheHelper cacheHelper;

  @Mock private Cache productCache;

  @BeforeEach
  void setup() {
    when(cacheManager.getCache("productCache")).thenReturn(productCache);
  }

  @Test
  void testGetProductsByCategories_WhenCacheIsAvailable() {
    // Prepare mock data
    Set<String> categories = Set.of("Electronics");
    ViewProductDetailsResponse product = new ViewProductDetailsResponse();
    ViewCategoryResponse category = new ViewCategoryResponse();
    category.setName("Electronics");
    product.setCategory(category);

    Map<String, Set<ViewProductDetailsResponse>> cachedProducts = new HashMap<>();
    cachedProducts.put("Electronics", Set.of(product));

    // Mock helper behavior
    when(cacheHelper.getCachedProducts(productCache, categories)).thenReturn(cachedProducts);

    // Execute method
    Map<String, Set<ViewProductDetailsResponse>> result =
        redisCacheService.getProductsByCategories(categories);

    // Verify result
    assertEquals(1, result.size());
    assertTrue(result.containsKey("Electronics"));
    assertEquals(1, result.get("Electronics").size());
    assertEquals(product, result.get("Electronics").iterator().next());

    // Verify interaction with helper
    verify(cacheHelper, times(1)).getCachedProducts(productCache, categories);
  }

  @Test
  void testGetProductsByCategories_WhenCacheIsNotConfigured() {
    // Simulate null cache
    when(cacheManager.getCache("productCache")).thenReturn(null);

    // Execute method
    Map<String, Set<ViewProductDetailsResponse>> result =
        redisCacheService.getProductsByCategories(Set.of("Clothing"));

    // Verify result
    assertTrue(result.isEmpty());

    // Verify no interaction with helper
    verifyNoInteractions(cacheHelper);
  }

  @Test
  void testUpdateCache_WithValidData() {
    // Prepare uncached categories and data
    Set<String> uncachedCategories = Set.of("Furniture");
    ViewProductDetailsResponse product = new ViewProductDetailsResponse();
    product.setName("Chair");
    Map<String, Set<ViewProductDetailsResponse>> uncachedData = new HashMap<>();
    uncachedData.put("Furniture", Set.of(product));

    // Mock productCache behavior for put
    doNothing().when(productCache).put(anyString(), any(Set.class));

    // Execute method
    redisCacheService.updateCache(uncachedCategories, uncachedData);

    // Verify that each category's data is added to the cache
    ArgumentCaptor<String> categoryCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<Set<ViewProductDetailsResponse>> dataCaptor = ArgumentCaptor.forClass(Set.class);
    verify(productCache, times(1)).put(categoryCaptor.capture(), dataCaptor.capture());

    // Validate captured arguments
    assertEquals("Furniture", categoryCaptor.getValue());
    assertEquals(1, dataCaptor.getValue().size());
    assertEquals("Chair", dataCaptor.getValue().iterator().next().getName());
  }

  @Test
  void testUpdateCache_WhenCacheIsNotConfigured() {
    // Simulate null cache
    when(cacheManager.getCache("productCache")).thenReturn(null);

    // Prepare uncached categories and data
    Set<String> uncachedCategories = Set.of("Home Decor");
    ViewProductDetailsResponse product = new ViewProductDetailsResponse();
    product.setName("Lamp");
    Map<String, Set<ViewProductDetailsResponse>> uncachedData = new HashMap<>();
    uncachedData.put("Home Decor", Set.of(product));

    // Execute method
    redisCacheService.updateCache(uncachedCategories, uncachedData);

    // Verify no cache interaction occurs when cache is null
    verifyNoInteractions(productCache);
  }
}
