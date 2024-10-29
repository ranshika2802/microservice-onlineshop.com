package com.rajat.customer_service.helper;

import com.rajat.customer_service.dto.ViewProductDetailsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class CacheHelper {

    /**
     * Retrieves products from the cache by their categories and filters out categories with no cached products.
     */
    public Map<String, Set<ViewProductDetailsResponse>> getCachedProducts(
            Cache productCache, Set<String> categories) {

        return categories.stream()
                .map(category -> Map.entry(category, getCachedCategoryProducts(productCache, category)))
                .filter(entry -> !entry.getValue().isEmpty())  // Filter out categories with no cached products
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Helper method to retrieve products from the cache for a specific category.
     */
    private Set<ViewProductDetailsResponse> getCachedCategoryProducts(Cache productCache, String category) {
        return Optional.ofNullable(productCache.get(category, Set.class))
                .orElse(Collections.emptySet());  // Return empty set if no cached products found
    }
}
