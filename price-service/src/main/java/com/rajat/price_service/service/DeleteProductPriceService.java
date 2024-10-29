package com.rajat.price_service.service;

import lombok.NonNull;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DeleteProductPriceService {
    ResponseEntity<Void> deleteProductPrice(@NonNull final List<String> productIds);
}
