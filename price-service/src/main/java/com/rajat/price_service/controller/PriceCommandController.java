package com.rajat.price_service.controller;

import com.rajat.price_service.dto.*;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RequestMapping("/v1/prices")
public interface PriceCommandController {
    @PostMapping("/create")
    List<AddProductPriceResponse> addProductPrice(@Valid @RequestBody @NonNull final Set<AddProductPriceRequest> addProductPriceRequests);

    @PutMapping("/update")
    List<UpdateProductPriceResponse> updateProductPrice(@Valid @RequestBody @NonNull final List<UpdateProductPriceRequest> updateProductPriceRequests);

    @DeleteMapping("/ids")
    ResponseEntity<Void> deleteProductPrice(@RequestParam @NonNull final List<String> productIds);
}
