package com.rajat.customer_service.service;

import com.rajat.customer_service.dto.ViewProductDetailsResponse;
import java.util.Map;
import java.util.Set;

import lombok.NonNull;

public interface ViewService {
  Map<String, Set<ViewProductDetailsResponse>> fetchProductsByCategories(
      @NonNull final Set<String> uncachedCategories);
}
