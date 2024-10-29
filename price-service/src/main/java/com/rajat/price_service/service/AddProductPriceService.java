package com.rajat.price_service.service;

import com.rajat.price_service.dto.AddProductPriceRequest;
import com.rajat.price_service.dto.AddProductPriceResponse;
import lombok.NonNull;

import java.util.List;
import java.util.Set;

public interface AddProductPriceService {
    List<AddProductPriceResponse> addProductPrice(@NonNull final Set<AddProductPriceRequest> addProductPriceRequests);
}
