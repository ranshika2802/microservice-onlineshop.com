package com.rajat.admin_service.client;

import java.util.Set;
import java.util.UUID;

import com.rajat.admin_service.model.product.request.AddProductClientRequest;
import com.rajat.admin_service.model.product.request.DeleteProductAttributesRequest;
import com.rajat.admin_service.model.product.request.UpdateProductClientRequest;
import com.rajat.admin_service.model.product.response.AddProductClientResponse;
import com.rajat.admin_service.model.product.response.ViewProductClientResponse;
import com.rajat.admin_service.model.product.response.UpdateProductClientResponse;
import lombok.NonNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "product-service/v1/products" /*, url = "http://localhost:9091/v1/products"*/)
public interface ProductClient {
  @PostMapping
  Set<AddProductClientResponse> addProducts(
      @RequestBody @NonNull final Set<AddProductClientRequest> addProductClientRequests);

  @PutMapping
  Set<UpdateProductClientResponse> updateProducts(
      @RequestBody @NonNull final Set<UpdateProductClientRequest> updateProductClientRequests);

  @DeleteMapping("/ids")
  ResponseEntity<Void> deleteProducts(@RequestParam @NonNull final Set<UUID> productIds);

  @DeleteMapping("/attributes")
  ResponseEntity<Void> deleteProductAttributes(
      @RequestBody @NonNull final DeleteProductAttributesRequest deleteProductAttributesRequest);

  @GetMapping("/categories")
  Set<ViewProductClientResponse> viewProductsByCategory(@RequestParam @NonNull final Set<String> categories);
}
