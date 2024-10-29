package com.rajat.price_service.service;

import com.rajat.price_service.dto.ViewProductPriceResponse;
import lombok.NonNull;

import java.util.List;

public interface ViewProductPriceService {
    List<ViewProductPriceResponse> viewPriceByProductId(@NonNull final List<String> productIds);
}
