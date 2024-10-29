package com.rajat.price_service.service;

import com.rajat.price_service.dto.AddProductPriceRequest;
import com.rajat.price_service.dto.AddProductPriceResponse;
import com.rajat.price_service.model.Price;
import com.rajat.price_service.repository.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddProductPriceServiceImplTest {

    @Mock
    private PriceRepository priceRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AddProductPriceServiceImpl addProductPriceService;

    private AddProductPriceRequest addProductPriceRequest;
    private Price newPrice;
    private AddProductPriceResponse expectedResponse;

    @BeforeEach
    void setUp() {
        // Initialize a valid AddProductPriceRequest for testing
        addProductPriceRequest = new AddProductPriceRequest();
        addProductPriceRequest.setCurrency("USD");
        addProductPriceRequest.setAmount(99.99f);
        addProductPriceRequest.setProductId(UUID.randomUUID());

        // Set up corresponding Price and expected response objects
        newPrice = new Price(null, "USD", 99.99f, addProductPriceRequest.getProductId());
        expectedResponse = new AddProductPriceResponse();
        expectedResponse.setCurrency(newPrice.getCurrency());
        expectedResponse.setAmount(newPrice.getAmount());
        expectedResponse.setProductId(newPrice.getProductId());
    }

    @Test
    void testAddProductPrice_NewProductId() {
        // Arrange: Configure ModelMapper to map any AddProductPriceRequest to a Price
        when(modelMapper.map(any(AddProductPriceRequest.class), eq(Price.class))).thenReturn(newPrice);

        // Configure ModelMapper to map any Price to AddProductPriceResponse
        when(modelMapper.map(any(Price.class), eq(AddProductPriceResponse.class))).thenReturn(expectedResponse);

        // Mock repository to simulate no existing product IDs and return the saved price
        when(priceRepository.findAllProductIdsByProductIdIn(anySet())).thenReturn(List.of());
        when(priceRepository.saveAll(anyList())).thenReturn(List.of(newPrice));

        // Act: Call addProductPrice with a new product ID
        List<AddProductPriceResponse> responses = addProductPriceService.addProductPrice(Set.of(addProductPriceRequest));

        // Assert: Verify response contains the expected new price
        assertEquals(1, responses.size());
        assertEquals(addProductPriceRequest.getCurrency(), responses.get(0).getCurrency());
        assertEquals(addProductPriceRequest.getAmount(), responses.get(0).getAmount());
        assertEquals(addProductPriceRequest.getProductId(), responses.get(0).getProductId());

        // Verify interactions with the repository
        verify(priceRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testAddProductPrice_ExistingProductId() {
        // Arrange: Configure ModelMapper to map AddProductPriceRequest to Price
        when(modelMapper.map(addProductPriceRequest, Price.class)).thenReturn(newPrice);

        // Mock repository to return existing product ID
        when(priceRepository.findAllProductIdsByProductIdIn(anySet())).thenReturn(List.of(addProductPriceRequest.getProductId()));

        // Act: Call addProductPrice with an existing product ID
        List<AddProductPriceResponse> responses = addProductPriceService.addProductPrice(Set.of(addProductPriceRequest));

        // Assert: No new prices should be added
        assertTrue(responses.isEmpty());

        // Verify saveAll was not called since product ID exists
        verify(priceRepository, never()).saveAll(anyList());
    }
}
