package com.rajat.product_service.service;

import com.rajat.product_service.dto.ProductResponse;
import lombok.NonNull;

import java.util.Set;

public interface IViewProductsService {
    Set<ProductResponse> viewProductsByCategory(@NonNull final Set<String> categories);
}
