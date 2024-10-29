package com.rajat.admin_service.service.delete;

import com.rajat.admin_service.client.PriceClient;
import com.rajat.admin_service.dto.request.delete.DeleteProductDetailsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class DeletePriceServiceImplTest {

    @Mock
    private PriceClient priceClient;

    @InjectMocks
    private DeletePriceServiceImpl deletePriceService;

    private DeleteProductDetailsDto deleteProductDetailsDto;

    @BeforeEach
    void setUp() {
        deleteProductDetailsDto = new DeleteProductDetailsDto();
        deleteProductDetailsDto.setProductIds(Set.of(UUID.randomUUID(), UUID.randomUUID()));
    }

    @Test
    void testDeletePrice_callsPriceClient() {
        // Act
        deletePriceService.deletePrice(deleteProductDetailsDto);

        // Assert
        verify(priceClient, times(1)).deleteProductPrice(deleteProductDetailsDto.getProductIds());
    }

    @Test
    void testDeletePrice_withEmptyProductIds() {
        // Arrange
        deleteProductDetailsDto.setProductIds(Set.of());

        // Act
        deletePriceService.deletePrice(deleteProductDetailsDto);

        // Assert
        verify(priceClient, times(1)).deleteProductPrice(deleteProductDetailsDto.getProductIds());
    }

    @Test
    void testDeletePrice_withSingleProductId() {
        // Arrange
        deleteProductDetailsDto.setProductIds(Set.of(UUID.randomUUID()));

        // Act
        deletePriceService.deletePrice(deleteProductDetailsDto);

        // Assert
        verify(priceClient, times(1)).deleteProductPrice(deleteProductDetailsDto.getProductIds());
    }
}
