package com.rajat.price_service.service;

import com.rajat.price_service.repository.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteProductPriceServiceImplTest {

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private DeleteProductPriceServiceImpl deleteProductPriceService;

    private List<String> productIds;

    @BeforeEach
    void setUp() {
        // Set up a list of product IDs to delete
        productIds = List.of("product-id-1", "product-id-2", "product-id-3");
    }

    @Test
    void testDeleteProductPrice() {
        // Arrange: Mock the repository to return a count of deleted records
        when(priceRepository.deleteAllByProductIdIn(productIds)).thenReturn(3);

        // Act: Call the deleteProductPrice method
        ResponseEntity<Void> response = deleteProductPriceService.deleteProductPrice(productIds);

        // Assert: Verify the response and interactions
        assertEquals(ResponseEntity.ok().build(), response);
        verify(priceRepository, times(1)).deleteAllByProductIdIn(productIds);
    }

    @Test
    void testDeleteProductPrice_NoRecordsDeleted() {
        // Arrange: Mock the repository to return 0, indicating no records deleted
        when(priceRepository.deleteAllByProductIdIn(productIds)).thenReturn(0);

        // Act: Call the deleteProductPrice method
        ResponseEntity<Void> response = deleteProductPriceService.deleteProductPrice(productIds);

        // Assert: Verify the response and interactions
        assertEquals(ResponseEntity.ok().build(), response);
        verify(priceRepository, times(1)).deleteAllByProductIdIn(productIds);
    }
}
