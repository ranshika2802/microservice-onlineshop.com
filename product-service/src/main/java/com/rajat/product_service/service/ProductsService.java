package com.rajat.product_service.service;

import com.rajat.product_service.dto.ProductRequest;
import com.rajat.product_service.dto.ProductResponse;
import com.rajat.product_service.dto.UpdateProductRequest;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductsService {
    List<ProductResponse> addProducts(@NonNull final List<ProductRequest> productRequest);

    List<ProductResponse> viewProductsByCategory(@NonNull final List<String> categories);

    List<ProductResponse> updateProducts(@NonNull final List<UpdateProductRequest> updateProductRequest);

    ResponseEntity<Void> deleteProduct(@NonNull final List<String> ids);
}
