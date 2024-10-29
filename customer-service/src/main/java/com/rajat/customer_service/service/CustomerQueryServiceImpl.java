package com.rajat.customer_service.service;

import com.rajat.customer_service.dto.ApiResponse;
import com.rajat.customer_service.dto.ViewProductDetailsResponse;
import com.rajat.customer_service.utils.ApiResponseUtils;
import com.rajat.customer_service.utils.CustomerUtils;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerQueryServiceImpl implements CustomerQueryService {

  private final CacheService cacheService;
  private final ViewService viewService;
  private final CustomerUtils customerUtils;
  private final ApiResponseUtils apiResponseUtils;

  @Override
  public ApiResponse getProductDetailsByCategories(@NonNull Set<String> categories) {
    // Fetch cached products
    Map<String, Set<ViewProductDetailsResponse>> categoryToProdMapFromCache =
        cacheService.getProductsByCategories(categories);

    // Remove cached categories from the original set to get uncached categories
    Set<String> uncachedCategories =
        customerUtils.getUncachedCategories(categories, categoryToProdMapFromCache.keySet());

    // Fetch uncached products from the database
    Map<String, Set<ViewProductDetailsResponse>> categoryToProdMapFromDb ;
    if (!uncachedCategories.isEmpty()) {
      categoryToProdMapFromDb = viewService.fetchProductsByCategories(uncachedCategories);

      if (apiResponseUtils.containsInvalidKeys(categoryToProdMapFromDb)) {
        return new ApiResponse(false, categoryToProdMapFromDb.keySet(), null);
      }
      // Update the cache asynchronously
      if (categoryToProdMapFromDb.keySet().size() > 0) {
        CompletableFuture.runAsync(
            () -> cacheService.updateCache(uncachedCategories, categoryToProdMapFromDb));
      }
    } else {
      categoryToProdMapFromDb = null;
    }

    // Merge cached and uncached product data and map to API response
    return apiResponseUtils.generateApiResponse(
        categoryToProdMapFromCache, categoryToProdMapFromDb);
  }
}
