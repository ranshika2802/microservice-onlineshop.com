package com.rajat.price_service.service;

import com.rajat.price_service.dto.UpdateProductPriceRequest;
import com.rajat.price_service.dto.UpdateProductPriceResponse;
import lombok.NonNull;

import java.util.List;

public interface UpdateProductPriceService {
    List<UpdateProductPriceResponse> updateProductPrice(@NonNull final List<UpdateProductPriceRequest> updateProductPriceRequests);
}
