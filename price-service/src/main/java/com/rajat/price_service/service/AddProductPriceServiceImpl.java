package com.rajat.price_service.service;

import com.rajat.price_service.dto.AddProductPriceRequest;
import com.rajat.price_service.dto.AddProductPriceResponse;
import com.rajat.price_service.model.Price;
import com.rajat.price_service.repository.PriceRepository;
import com.rajat.price_service.utils.PriceUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddProductPriceServiceImpl implements AddProductPriceService {

    private final PriceRepository priceRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public List<AddProductPriceResponse> addProductPrice(@NonNull Set<AddProductPriceRequest> addProductPriceRequests) {
        Set<Price> mapRequestToPrice = PriceUtils.mapSet(addProductPriceRequests, Price.class, modelMapper);
        // Get all productIds from the incoming request
        Set<UUID> productIds = mapRequestToPrice.stream()
                .map(Price::getProductId)
                .collect(Collectors.toSet());

// Fetch existing productIds from the database in one call
        Set<UUID> existingProductIds = new HashSet<>(priceRepository.findAllProductIdsByProductIdIn(productIds));

// Filter out those prices whose productId already exists in the DB
        List<Price> newPrices = mapRequestToPrice.stream()
                .filter(price -> !existingProductIds.contains(price.getProductId()))  // Filter non-existing productIds
                .collect(Collectors.toList());

// Save only the new prices
        if (!newPrices.isEmpty()) {
            List<Price> savedPrice = priceRepository.saveAll(newPrices);
            return PriceUtils.mapList(savedPrice, AddProductPriceResponse.class, modelMapper);
        }
        return List.of();
    }
}
