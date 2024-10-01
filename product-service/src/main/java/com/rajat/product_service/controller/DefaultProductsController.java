package com.rajat.product_service.controller;

import com.rajat.product_service.dto.ProductRequest;
import com.rajat.product_service.dto.ProductResponse;
import com.rajat.product_service.dto.UpdateProductRequest;
import com.rajat.product_service.service.ProductsService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DefaultProductsController implements ProductsController {

    private final ProductsService productsService;

    @Override
    public List<ProductResponse> addProducts(@NonNull final List<ProductRequest> productRequests) {
        return productsService.addProducts(productRequests);
    }

    @Override
    public List<ProductResponse> viewProductsByCategory(@NonNull final List<String> categories) {
        return productsService.viewProductsByCategory(categories);
    }

    @Override
    public List<ProductResponse> updateProducts(@NonNull final List<UpdateProductRequest> updateProductRequests) {
        return productsService.updateProducts(updateProductRequests);
    }

    @Override
    public ResponseEntity<Void> deleteProducts(@NonNull final List<String> ids) {
        return productsService.deleteProduct(ids);
    }
}
