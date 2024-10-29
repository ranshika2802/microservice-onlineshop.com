package com.rajat.product_service.controller;

import com.rajat.product_service.dto.*;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RequestMapping("/v1/products")
public interface ProductCommandController {

    @PostMapping
    Set<AddProductResponse> addProducts(@Valid @RequestBody @NonNull final Set<AddProductRequest> addProductRequests);

    @PutMapping
    Set<UpdateProductResponse> updateProducts(@Valid @RequestBody @NonNull final Set<UpdateProductRequest> updateProductRequests);

    @DeleteMapping(value = "/ids")
    ResponseEntity<Void> deleteProducts(@RequestParam @NonNull final Set<UUID> productIds);

    @DeleteMapping(value = "/attributes")
    ResponseEntity<Void> deleteProductAttributes(@Valid @RequestBody @NonNull final DeleteProductRequest deleteProductRequest);
}
