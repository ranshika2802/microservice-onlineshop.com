package com.rajat.product_service.service;

import com.rajat.product_service.dto.DeleteProductRequest;
import com.rajat.product_service.repository.AttributeRepository;
import com.rajat.product_service.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DeleteProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private AttributeRepository attributeRepository;

    @InjectMocks
    private DeleteProductService deleteProductService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deleteProduct_ShouldReturnOkResponse() {
        // Arrange
        Set<UUID> productIds = Set.of(UUID.randomUUID(), UUID.randomUUID());

        // Act
        ResponseEntity<Void> response = deleteProductService.deleteProduct(productIds);

        // Assert
        assertEquals(ResponseEntity.ok().build(), response);
        verify(productRepository, times(1)).deleteAllById(productIds);
    }

    @Test
    void deleteProductAttributes_ShouldReturnDeletedCount() {
        // Arrange
        UUID productId = UUID.randomUUID();
        Set<String> attributeNames = Set.of("Color", "Size");
        DeleteProductRequest request = new DeleteProductRequest();
        request.setProductIdToAttributeNamesMap(Map.of(productId, attributeNames));

        when(attributeRepository.deleteAllByProductIdAndNameIn(productId, attributeNames)).thenReturn(2);

        // Act
        ResponseEntity<String> response = deleteProductService.deleteProductAttributes(request);

        // Assert
        assertEquals(ResponseEntity.ok("Total Record deleted = 2"), response);
        verify(attributeRepository, times(1)).deleteAllByProductIdAndNameIn(productId, attributeNames);
    }

    @Test
    void deleteProductAttributes_ShouldSkipInvalidEntries() {
        // Arrange
        UUID validProductId = UUID.randomUUID();
        Set<String> validAttributeNames = Set.of("Color", "Size");
        DeleteProductRequest request = new DeleteProductRequest();

        // Using HashMap instead of Map.of to allow null or empty values
        Map<UUID, Set<String>> productIdToAttributeNamesMap = new HashMap<>();
        productIdToAttributeNamesMap.put(validProductId, validAttributeNames);   // valid entry
        productIdToAttributeNamesMap.put(null, Set.of("InvalidAttribute"));      // invalid entry with null key
        productIdToAttributeNamesMap.put(UUID.randomUUID(), Set.of());           // invalid entry with empty set

        request.setProductIdToAttributeNamesMap(productIdToAttributeNamesMap);

        when(attributeRepository.deleteAllByProductIdAndNameIn(validProductId, validAttributeNames)).thenReturn(2);

        // Act
        ResponseEntity<String> response = deleteProductService.deleteProductAttributes(request);

        // Assert
        assertEquals(ResponseEntity.ok("Total Record deleted = 2"), response);
        verify(attributeRepository, times(1)).deleteAllByProductIdAndNameIn(validProductId, validAttributeNames);
    }
}
