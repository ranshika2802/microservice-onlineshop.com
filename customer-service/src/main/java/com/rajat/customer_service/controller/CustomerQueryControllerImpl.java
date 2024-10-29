package com.rajat.customer_service.controller;

import com.rajat.customer_service.dto.ApiResponse;
import com.rajat.customer_service.service.CustomerQueryService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RequiredArgsConstructor
@RestController
public class CustomerQueryControllerImpl implements CustomerQueryController {

    private final CustomerQueryService customerQueryService;

    @Override
    public ApiResponse getProductDetailsByCategories(@NonNull Set<String> categories) {
        return customerQueryService.getProductDetailsByCategories(categories);
    }
}
