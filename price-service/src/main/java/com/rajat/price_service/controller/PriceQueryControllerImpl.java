package com.rajat.price_service.controller;

import com.rajat.price_service.dto.ViewProductPriceResponse;
import com.rajat.price_service.service.ViewProductPriceService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PriceQueryControllerImpl implements PriceQueryController {

    private final ViewProductPriceService priceViewService;

    @Override
    public List<ViewProductPriceResponse> viewPriceByProductId(@NonNull List<String> productIds) {
        if (productIds.isEmpty()) {
            throw new IllegalArgumentException("ProductIds cannot be empty");
        }
        return priceViewService.viewPriceByProductId(productIds);
    }
}
