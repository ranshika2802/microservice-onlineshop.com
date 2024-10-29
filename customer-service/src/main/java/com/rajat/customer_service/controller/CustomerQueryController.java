package com.rajat.customer_service.controller;

import com.rajat.customer_service.dto.ApiResponse;
import lombok.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@RequestMapping("/v1/customer")
public interface CustomerQueryController {

    @GetMapping("/products")
    ApiResponse getProductDetailsByCategories(
            @RequestParam @NonNull final Set<String> categories);
}
