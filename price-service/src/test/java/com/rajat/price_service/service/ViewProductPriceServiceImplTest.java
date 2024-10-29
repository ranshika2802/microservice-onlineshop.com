package com.rajat.price_service.service;

import com.rajat.price_service.dto.ViewProductPriceResponse;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ViewProductPriceServiceImplTest {

    @Mock
    private PriceRepository priceRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ViewProductPriceServiceImpl viewProductPriceService;

    private List<String> productIds;
    private Price price;
    private ViewProductPriceResponse expectedResponse;

    @BeforeEach
    void setUp() {
        // Initialize product IDs and set up Price and ViewProductPriceResponse objects for testing
        productIds = List.of(UUID.randomUUID().toString(), UUID.randomUUID().toString());

        price = new Price();
        price.setCurrency("USD");
        price.setAmount(99.99f);
        price.setProductId(UUID.fromString(productIds.get(0)));

        expectedResponse = new ViewProductPriceResponse();
        expectedResponse.setCurrency("USD");
        expectedResponse.setAmount(99.99f);
        expectedResponse.setProductId(price.getProductId());
    }

    @Test
    void testViewPriceByProductId_SuccessfulResponse() {
        // Arrange: Mock repository to return a list of Price entities
        when(priceRepository.findAllByProductIdIn(productIds)).thenReturn(List.of(price));

        // Mock ModelMapper to convert Price to ViewProductPriceResponse
        when(modelMapper.map(price, ViewProductPriceResponse.class)).thenReturn(expectedResponse);

        // Act: Call the viewPriceByProductId method
        List<ViewProductPriceResponse> responses = viewProductPriceService.viewPriceByProductId(productIds);

        // Assert: Verify the response contains the expected data
        assertEquals(1, responses.size());
        assertEquals(expectedResponse.getCurrency(), responses.get(0).getCurrency());
        assertEquals(expectedResponse.getAmount(), responses.get(0).getAmount());
        assertEquals(expectedResponse.getProductId(), responses.get(0).getProductId());

        // Verify repository interaction
        verify(priceRepository, times(1)).findAllByProductIdIn(productIds);
    }

    @Test
    void testViewPriceByProductId_NoMatchingProducts() {
        // Arrange: Mock repository to return an empty list for no matching product IDs
        when(priceRepository.findAllByProductIdIn(productIds)).thenReturn(List.of());

        // Act: Call the viewPriceByProductId method
        List<ViewProductPriceResponse> responses = viewProductPriceService.viewPriceByProductId(productIds);

        // Assert: Verify the response is empty
        assertTrue(responses.isEmpty());

        // Verify repository interaction
        verify(priceRepository, times(1)).findAllByProductIdIn(productIds);
        verify(modelMapper, never()).map(any(), eq(ViewProductPriceResponse.class));
    }
}
