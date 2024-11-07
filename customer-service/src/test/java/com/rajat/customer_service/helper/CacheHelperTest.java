package com.rajat.customer_service.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.rajat.customer_service.dto.ViewCategoryResponse;
import com.rajat.customer_service.dto.ViewProductDetailsResponse;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;

@ExtendWith(MockitoExtension.class)
public class CacheHelperTest {

  private CacheHelper cacheHelper;

  @Mock private Cache productCache;

  @BeforeEach
  void setup() {
    cacheHelper = new CacheHelper();
  }

  @Test
  void testGetCachedProducts_WithCachedData() {
    // Setup categories and mock data for the cache
    Set<String> categories = Set.of("Electronics", "Furniture");

    ViewProductDetailsResponse electronicsProduct = new ViewProductDetailsResponse();
    ViewCategoryResponse electronicsCategory = new ViewCategoryResponse();
    electronicsCategory.setName("Electronics");
    electronicsProduct.setCategory(electronicsCategory);

    Set<ViewProductDetailsResponse> electronicsProducts = Set.of(electronicsProduct);

    ViewProductDetailsResponse furnitureProduct = new ViewProductDetailsResponse();
    ViewCategoryResponse furnitureCategory = new ViewCategoryResponse();
    furnitureCategory.setName("Furniture");
    furnitureProduct.setCategory(furnitureCategory);

    Set<ViewProductDetailsResponse> furnitureProducts = Set.of(furnitureProduct);

    // Mock cache retrievals
    when(productCache.get(eq("Electronics"), eq(Set.class))).thenReturn(electronicsProducts);
    when(productCache.get(eq("Furniture"), eq(Set.class))).thenReturn(furnitureProducts);

    // Call method
    Map<String, Set<ViewProductDetailsResponse>> result =
        cacheHelper.getCachedProducts(productCache, categories);

    // Assert result
    assertEquals(2, result.size());
    assertEquals(electronicsProducts, result.get("Electronics"));
    assertEquals(furnitureProducts, result.get("Furniture"));
  }

  @Test
  void testGetCachedProducts_WithNoCachedData() {
    // Setup categories
    Set<String> categories = Set.of("Toys");

    // Mock cache to return empty for "Toys"
    when(productCache.get(eq("Toys"), eq(Set.class))).thenReturn(null);

    // Call method
    Map<String, Set<ViewProductDetailsResponse>> result =
        cacheHelper.getCachedProducts(productCache, categories);

    // Assert result is empty
    assertTrue(result.isEmpty());
  }

  @Test
  void testGetCachedProducts_MixOfCachedAndUncachedData() {
    // Setup categories
    Set<String> categories = Set.of("Electronics", "Clothing");

    ViewProductDetailsResponse electronicsProduct = new ViewProductDetailsResponse();
    ViewCategoryResponse electronicsCategory = new ViewCategoryResponse();
    electronicsCategory.setName("Electronics");
    electronicsProduct.setCategory(electronicsCategory);

    Set<ViewProductDetailsResponse> electronicsProducts = Set.of(electronicsProduct);

    // Mock cache: "Electronics" has data, "Clothing" has none
    when(productCache.get(eq("Electronics"), eq(Set.class))).thenReturn(electronicsProducts);
    when(productCache.get(eq("Clothing"), eq(Set.class))).thenReturn(null);

    // Call method
    Map<String, Set<ViewProductDetailsResponse>> result =
        cacheHelper.getCachedProducts(productCache, categories);

    // Assert result only contains "Electronics"
    assertEquals(1, result.size());
    assertEquals(electronicsProducts, result.get("Electronics"));
  }

  @Test
  void testGetCachedCategoryProducts_WhenDataExists() {
    // Prepare mock product set
    ViewProductDetailsResponse product = new ViewProductDetailsResponse();
    product.setName("Smartphone");

    Set<ViewProductDetailsResponse> productSet = Set.of(product);

    // Mock cache get method
    when(productCache.get(eq("Electronics"), eq(Set.class))).thenReturn(productSet);

    // Call method
    Set<ViewProductDetailsResponse> result =
        cacheHelper.getCachedProducts(productCache, Set.of("Electronics")).get("Electronics");

    // Assert result
    assertEquals(productSet, result);
  }

  @Test
  void testGetCachedCategoryProducts_WhenNoDataExists() {
    // Mock cache to return null for "Books" category
    when(productCache.get(eq("Books"), eq(Set.class))).thenReturn(null);

    // Call method
    Map<String, Set<ViewProductDetailsResponse>> resultMap =
        cacheHelper.getCachedProducts(productCache, Set.of("Books"));

    // Verify that the resultMap does not contain the "Books" key or contains an empty set for it
    assertTrue(resultMap.getOrDefault("Books", Collections.emptySet()).isEmpty());
  }
}
