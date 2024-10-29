package com.rajat.product_service.controller;

import com.rajat.product_service.dto.ProductResponse;
import lombok.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@RequestMapping("/v1/products")
public interface ProductQueryController {

    @GetMapping("/categories")
    Set<ProductResponse> viewProductsByCategory(@RequestParam @NonNull final Set<String> categories);

}
