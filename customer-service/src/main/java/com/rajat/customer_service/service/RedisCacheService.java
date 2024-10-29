package com.rajat.customer_service.service;

import com.rajat.customer_service.dto.ViewProductDetailsResponse;
import com.rajat.customer_service.helper.CacheHelper;
import java.util.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisCacheService implements CacheService {

  private final RedisCacheManager cacheManager;
  private final CacheHelper cacheHelper; // Separated out cache-specific logic

  @Override
  public Map<String, Set<ViewProductDetailsResponse>> getProductsByCategories(
      @NonNull final Set<String> categories) {

    Cache productCache = cacheManager.getCache("productCache");

    if (productCache == null) {
      log.warn(
          "[RedisCacheService] -> getProductsByCategories: Product cache isn't configured. Fetching all from DB.");
      return Collections.emptyMap(); // Return empty map when cache is not available
    }

    // Delegate cached data retrieval and uncached category filtering to CacheHelper
    return cacheHelper.getCachedProducts(productCache, categories);
  }

  @Override
  public void updateCache(
      Set<String> uncachedCategories, Map<String, Set<ViewProductDetailsResponse>> uncachedData) {
    Cache productCache = cacheManager.getCache("productCache");

    if (productCache != null) {
      log.info("Attempting to update cache for categories: {}", uncachedCategories);
      try {
        uncachedCategories.forEach(
            category -> {
              Set<ViewProductDetailsResponse> data = uncachedData.get(category);
              log.info("Data for category {}: {}", category, data);
              if (data != null) {
                productCache.put(category, data);
                log.info("Cache updated for category: {}, data size: {}", category, data.size());

                // Verify the cache update
                Set<ViewProductDetailsResponse> cachedData =
                    (Set<ViewProductDetailsResponse>) productCache.get(category).get();
                log.info("Cached data for category {}: {}", category, cachedData);
              } else {
                log.warn("No data found for category: {}", category);
              }
            });
      } catch (Exception e) {
        log.error("Error updating cache: ", e);
      }
    } else {
      log.error("Product cache is null");
    }
  }
}
