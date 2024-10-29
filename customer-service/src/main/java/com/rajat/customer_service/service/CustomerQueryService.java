package com.rajat.customer_service.service;

import com.rajat.customer_service.dto.ApiResponse;
import lombok.NonNull;

import java.util.Set;

public interface CustomerQueryService {
    ApiResponse getProductDetailsByCategories(@NonNull final Set<String> categories);
}
