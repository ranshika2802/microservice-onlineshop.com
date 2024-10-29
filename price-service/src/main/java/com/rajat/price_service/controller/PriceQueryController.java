package com.rajat.price_service.controller;

import com.rajat.price_service.dto.ViewProductPriceResponse;
import lombok.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/v1/prices")
public interface PriceQueryController {
    @GetMapping("/productId")
    List<ViewProductPriceResponse> viewPriceByProductId(@RequestParam @NonNull final List<String> productIds);

}
