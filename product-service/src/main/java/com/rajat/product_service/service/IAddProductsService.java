package com.rajat.product_service.service;

import com.rajat.product_service.dto.AddProductRequest;
import com.rajat.product_service.dto.AddProductResponse;
import lombok.NonNull;

import java.util.Set;

public interface IAddProductsService {
    Set<AddProductResponse> addProducts(@NonNull final Set<AddProductRequest> addProductRequests);
}

