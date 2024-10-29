package com.rajat.product_service.controller;

import com.rajat.product_service.dto.*;
import com.rajat.product_service.service.IAddProductsService;
import com.rajat.product_service.service.IDeleteProductService;
import com.rajat.product_service.service.IUpdateProductsService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Validated
public class ProductCommandControllerImpl implements ProductCommandController {

    private final IAddProductsService addProductsService;
    private final IUpdateProductsService updateProductsService;
    private final IDeleteProductService deleteProductService;

    @Override
    public Set<AddProductResponse> addProducts(@NonNull final Set<AddProductRequest> addProductRequests) {
        return addProductsService.addProducts(addProductRequests);
    }

    @Override
    public Set<UpdateProductResponse> updateProducts(@RequestBody @NonNull final Set<UpdateProductRequest> updateProductRequests) {
        return updateProductsService.updateProducts(updateProductRequests);
    }

    @Override
    public ResponseEntity<Void> deleteProducts(@NonNull final Set<UUID> productIds) {
        return deleteProductService.deleteProduct(productIds);
    }

    @Override
    public ResponseEntity<Void> deleteProductAttributes(@RequestBody @NonNull final DeleteProductRequest deleteProductRequest) {
        deleteProductService.deleteProductAttributes(deleteProductRequest);
        return ResponseEntity.ok().build();
    }
}
