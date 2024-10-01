package com.rajat.product_service.controller;

import com.rajat.product_service.dto.ProductRequest;
import com.rajat.product_service.dto.ProductResponse;
import com.rajat.product_service.dto.UpdateProductRequest;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/v1/products")
public interface ProductsController {

    @PostMapping
    List<ProductResponse> addProducts(@RequestBody @NonNull final List<ProductRequest> productRequests);

    @GetMapping("/categories")
    List<ProductResponse> viewProductsByCategory(@RequestParam @NonNull final List<String> categories);

    @PutMapping
    List<ProductResponse> updateProducts(@RequestBody @NonNull final List<UpdateProductRequest> UpdateProductRequest);

    @DeleteMapping("/ids")
    ResponseEntity<Void> deleteProducts(@RequestParam @NonNull final List<String> ids);
}
