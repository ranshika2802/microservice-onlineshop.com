package com.rajat.price_service.service;

import com.rajat.price_service.repository.PriceRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeleteProductPriceServiceImpl implements DeleteProductPriceService {

    private final PriceRepository priceRepository;

    @Override
    public ResponseEntity<Void> deleteProductPrice(@NonNull List<String> productIds) {
        int recordCount = priceRepository.deleteAllByProductIdIn(productIds);
        log.info("Total record deleted = {}",recordCount);
        return ResponseEntity.ok().build();
    }
}
