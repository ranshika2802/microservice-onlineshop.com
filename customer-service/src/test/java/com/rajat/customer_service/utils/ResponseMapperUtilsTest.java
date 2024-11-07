package com.rajat.customer_service.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.rajat.customer_service.dto.*;
import com.rajat.customer_service.model.ApiClientResponse;
import com.rajat.customer_service.model.CategoryClientResponse;
import com.rajat.customer_service.model.ViewProductDetailsClientResponse;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

class ResponseMapperUtilsTest {

  private ResponseMapperUtils responseMapperUtils;
  private ModelMapper modelMapper;

  @BeforeEach
  void setUp() {
    modelMapper = new ModelMapper();
    responseMapperUtils = new ResponseMapperUtils(modelMapper);
  }

  @Test
  void mapClientResponse_withValidApiClientResponse_shouldReturnMappedCategoryMap() {
    ViewProductDetailsClientResponse clientResponse = new ViewProductDetailsClientResponse();
    clientResponse.setName("Laptop");
    clientResponse.setBrand("BrandA");
    clientResponse.setCategory(createCategoryResponse("Electronics"));

    ApiClientResponse apiClientResponse =
        new ApiClientResponse(true, Set.of("Success"), Set.of(clientResponse));

    Map<String, Set<ViewProductDetailsResponse>> result =
        responseMapperUtils.mapClientResponse(apiClientResponse);

    assertEquals(1, result.size());
    assertTrue(result.containsKey("Electronics"));
    assertEquals("Laptop", result.get("Electronics").iterator().next().getName());
  }

  @Test
  void mergeProductMaps_withNonEmptyMaps_shouldMergeCorrectly() {
    Set<ViewProductDetailsResponse> cacheProducts = new HashSet<>();
    cacheProducts.add(createProductResponse("Tablet", "BrandB", "Electronics"));

    Set<ViewProductDetailsResponse> dbProducts = new HashSet<>();
    dbProducts.add(createProductResponse("Laptop", "BrandA", "Electronics"));

    Map<String, Set<ViewProductDetailsResponse>> cacheMap = new HashMap<>();
    cacheMap.put("Electronics", cacheProducts);

    Map<String, Set<ViewProductDetailsResponse>> dbMap = new HashMap<>();
    dbMap.put("Electronics", dbProducts);

    Map<String, Set<ViewProductDetailsResponse>> result =
        responseMapperUtils.mergeProductMaps(cacheMap, dbMap);

    assertEquals(1, result.size());
    assertTrue(result.containsKey("Electronics"));
    assertEquals(2, result.get("Electronics").size());
  }

  @Test
  void mergeProductMaps_withEmptyCacheMap_shouldReturnDbMapOnly() {
    Map<String, Set<ViewProductDetailsResponse>> cacheMap = new HashMap<>();
    Set<ViewProductDetailsResponse> dbProducts = new HashSet<>();
    dbProducts.add(createProductResponse("Laptop", "BrandA", "Electronics"));

    Map<String, Set<ViewProductDetailsResponse>> dbMap = new HashMap<>();
    dbMap.put("Electronics", dbProducts);

    Map<String, Set<ViewProductDetailsResponse>> result =
        responseMapperUtils.mergeProductMaps(cacheMap, dbMap);

    assertEquals(dbMap, result);
  }

  @Test
  void mergeProductMaps_withEmptyDbMap_shouldReturnCacheMapOnly() {
    Set<ViewProductDetailsResponse> cacheProducts = new HashSet<>();
    cacheProducts.add(createProductResponse("Tablet", "BrandB", "Electronics"));

    Map<String, Set<ViewProductDetailsResponse>> cacheMap = new HashMap<>();
    cacheMap.put("Electronics", cacheProducts);

    Map<String, Set<ViewProductDetailsResponse>> dbMap = new HashMap<>();

    Map<String, Set<ViewProductDetailsResponse>> result =
        responseMapperUtils.mergeProductMaps(cacheMap, dbMap);

    assertEquals(cacheMap, result);
  }

  private ViewProductDetailsResponse createProductResponse(
      String name, String brand, String categoryName) {
    ViewProductDetailsResponse product = new ViewProductDetailsResponse();
    product.setName(name);
    product.setBrand(brand);

    ViewCategoryResponse category = new ViewCategoryResponse();
    category.setName(categoryName);
    product.setCategory(category);

    return product;
  }

  private CategoryClientResponse createCategoryResponse(String name) {
    CategoryClientResponse category = new CategoryClientResponse();
    category.setName(name);
    return category;
  }
}
