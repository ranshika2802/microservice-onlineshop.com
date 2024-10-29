package com.rajat.price_service.service;

import com.rajat.price_service.dto.UpdateProductPriceRequest;
import com.rajat.price_service.dto.UpdateProductPriceResponse;
import com.rajat.price_service.repository.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateProductPriceServiceImplTest {

    @Mock
    private PriceRepository priceRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UpdateProductPriceServiceImpl updateProductPriceService;

    private UpdateProductPriceRequest updateRequest1;
    private UpdateProductPriceRequest updateRequest2;

    @BeforeEach
    void setUp() {
        // Initialize UpdateProductPriceRequest objects for testing
        updateRequest1 = new UpdateProductPriceRequest();
        updateRequest1.setCurrency("USD");
        updateRequest1.setAmount(100.0f);
        updateRequest1.setProductId(UUID.randomUUID());

        updateRequest2 = new UpdateProductPriceRequest();
        updateRequest2.setCurrency("EUR");
        updateRequest2.setAmount(85.0f);
        updateRequest2.setProductId(UUID.randomUUID());
    }

    @Test
    void testUpdateProductPrice_MultipleRequests() {
        // Arrange: Create a list of UpdateProductPriceRequest objects
        List<UpdateProductPriceRequest> updateRequests = List.of(updateRequest1, updateRequest2);

        // Act: Call the updateProductPrice method
        List<UpdateProductPriceResponse> responses = updateProductPriceService.updateProductPrice(updateRequests);

        // Assert: Verify the repository method was called for each request
        verify(priceRepository, times(1)).updatePrice(updateRequest1.getCurrency(), updateRequest1.getAmount(), updateRequest1.getProductId());
        verify(priceRepository, times(1)).updatePrice(updateRequest2.getCurrency(), updateRequest2.getAmount(), updateRequest2.getProductId());

        // Verify the response is empty as per the method's current implementation
        assertTrue(responses.isEmpty());
    }

    @Test
    void testUpdateProductPrice_EmptyRequestList() {
        // Act: Call the updateProductPrice method with an empty list
        List<UpdateProductPriceResponse> responses = updateProductPriceService.updateProductPrice(List.of());

        // Assert: Verify the repository method was never called
        verify(priceRepository, never()).updatePrice(anyString(), anyFloat(), any(UUID.class));

        // Verify the response is empty as per the method's current implementation
        assertTrue(responses.isEmpty());
    }
}
