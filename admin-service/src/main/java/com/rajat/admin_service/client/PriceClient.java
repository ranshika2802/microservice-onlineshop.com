package com.rajat.admin_service.client;

import com.rajat.admin_service.model.price.request.AddProductPriceClientRequest;
import com.rajat.admin_service.model.price.request.UpdateProductPriceClientRequest;
import com.rajat.admin_service.model.price.response.AddProductPriceClientResponse;
import com.rajat.admin_service.model.price.response.UpdateProductPriceClientResponse;
import com.rajat.admin_service.model.price.response.ViewProductPriceClientResponse;
import jakarta.validation.Valid;

import java.util.Set;
import java.util.UUID;

import lombok.NonNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "price-service/v1/prices"/*, url = "http://localhost:9092/v1/prices"*/)
public interface PriceClient {
  @PostMapping("/create")
  Set<AddProductPriceClientResponse> addProductPrice(
      @Valid @RequestBody @NonNull
          final Set<AddProductPriceClientRequest> addProductPriceClientRequests);

  @PutMapping("/update")
  Set<UpdateProductPriceClientResponse> updateProductPrice(
      @RequestBody @NonNull
          final Set<UpdateProductPriceClientRequest> updateProductPriceClientRequests);

  @DeleteMapping("/ids")
  ResponseEntity<Void> deleteProductPrice(@RequestParam @NonNull final Set<UUID> productIds);

  @GetMapping("/productId")
  Set<ViewProductPriceClientResponse> viewPriceByProductId(
      @RequestParam @NonNull final Set<UUID> productIds);
}
