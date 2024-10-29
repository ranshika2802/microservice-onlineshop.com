package com.rajat.product_service.service;

import com.rajat.product_service.dto.UpdateProductRequest;
import com.rajat.product_service.dto.UpdateProductResponse;
import lombok.NonNull;

import java.util.Set;

public interface IUpdateProductsService {
    Set<UpdateProductResponse> updateProducts(@NonNull final Set<UpdateProductRequest> updateProductRequests);
}
