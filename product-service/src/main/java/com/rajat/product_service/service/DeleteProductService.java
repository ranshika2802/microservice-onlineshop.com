package com.rajat.product_service.service;

import com.rajat.product_service.dto.DeleteProductRequest;
import com.rajat.product_service.repository.AttributeRepository;
import com.rajat.product_service.repository.ProductRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeleteProductService implements IDeleteProductService {

    private final ProductRepository productRepository;
    private final AttributeRepository attributeRepository;

    @Override
    public ResponseEntity<Void> deleteProduct(@NonNull final Set<UUID> productIds) {
        productRepository.deleteAllById(productIds);
        return ResponseEntity.ok().build();
    }

    @Override
    @Transactional
    public ResponseEntity<String> deleteProductAttributes(@NonNull final DeleteProductRequest deleteProductRequest) {
        final int[] deletedCount = new int[1];
        deleteProductRequest.getProductIdToAttributeNamesMap().forEach((productId, attributeNames) -> {
            if (productId == null || attributeNames == null || attributeNames.isEmpty()) {
                log.error("Invalid input: productId or attributeNames is null or empty");
                return; // skip to avoid errors
            }
            deletedCount[0] = attributeRepository.deleteAllByProductIdAndNameIn(productId, attributeNames);
        });
        return ResponseEntity.ok("Total Record deleted = "+ deletedCount[0]);
    }
}
