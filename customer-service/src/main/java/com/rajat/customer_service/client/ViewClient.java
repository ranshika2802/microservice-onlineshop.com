package com.rajat.customer_service.client;

import com.rajat.customer_service.dto.ApiResponse;
import com.rajat.customer_service.model.ApiClientResponse;
import com.rajat.customer_service.model.ViewProductDetailsClientResponse;
import lombok.NonNull;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@FeignClient(value = "admin-service")
public interface ViewClient {
    @GetMapping("/v1/admin/products")
    ApiClientResponse viewProductsDetailsByCategory(
            @RequestParam @NonNull final Set<String> categories);
}
