package com.rajat.price_service.controller;

import com.rajat.price_service.dto.*;
import com.rajat.price_service.service.AddProductPriceService;
import com.rajat.price_service.service.DeleteProductPriceService;
import com.rajat.price_service.service.UpdateProductPriceService;
import com.rajat.price_service.service.ViewProductPriceService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@Validated
public class PriceCommandControllerImpl implements PriceCommandController {

    private final AddProductPriceService priceAddService;
    private final UpdateProductPriceService priceUpdateService;
    private final DeleteProductPriceService priceDeleteService;

    @Override
    public List<AddProductPriceResponse> addProductPrice(@Valid @NonNull final Set<AddProductPriceRequest> addProductPriceRequests) {
        return priceAddService.addProductPrice(addProductPriceRequests);
    }

    @Override
    public List<UpdateProductPriceResponse> updateProductPrice(@NonNull final List<UpdateProductPriceRequest> updateProductPriceRequests) {
        return priceUpdateService.updateProductPrice(updateProductPriceRequests);
    }

    @Override
    @Transactional
    public ResponseEntity<Void> deleteProductPrice(@NonNull List<String> productIds) {
        priceDeleteService.deleteProductPrice(productIds);
        return ResponseEntity.ok().build();
    }
}
