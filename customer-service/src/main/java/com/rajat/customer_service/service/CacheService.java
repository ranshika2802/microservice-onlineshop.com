package com.rajat.customer_service.service;

import com.rajat.customer_service.dto.ViewProductDetailsResponse;

import java.util.Map;
import java.util.Set;
import lombok.NonNull;

public interface CacheService {

  Map<String, Set<ViewProductDetailsResponse>> getProductsByCategories(
      @NonNull final Set<String> categories);

  void updateCache(Set<String> uncachedCategories, Map<String, Set<ViewProductDetailsResponse>> uncachedData);

}
