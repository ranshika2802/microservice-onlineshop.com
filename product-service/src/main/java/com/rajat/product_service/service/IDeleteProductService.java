package com.rajat.product_service.service;

import com.rajat.product_service.dto.DeleteProductRequest;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;

import java.util.Set;
import java.util.UUID;

public interface IDeleteProductService {

    ResponseEntity<Void> deleteProduct(@NonNull final Set<UUID> productIds);

    ResponseEntity<String> deleteProductAttributes(@NonNull final DeleteProductRequest deleteProductRequest);
}
