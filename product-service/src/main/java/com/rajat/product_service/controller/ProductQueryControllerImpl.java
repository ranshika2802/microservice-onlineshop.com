package com.rajat.product_service.controller;

import com.rajat.product_service.dto.ProductResponse;
import com.rajat.product_service.service.IViewProductsService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
public class ProductQueryControllerImpl implements ProductQueryController {

    private final IViewProductsService viewProductsService;

    @Override
    public Set<ProductResponse> viewProductsByCategory(@NonNull final Set<String> categories) {
        return viewProductsService.viewProductsByCategory(categories);
    }
}
