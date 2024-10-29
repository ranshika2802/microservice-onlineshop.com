package com.rajat.admin_service.service.update;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.rajat.admin_service.dto.request.update.*;
import com.rajat.admin_service.dto.response.update.UpdateProductDetailsResponseDto;
import com.rajat.admin_service.model.inventory.request.UpdateProductInventoryClientRequest;
import com.rajat.admin_service.model.inventory.response.UpdateProductInventoryClientResponse;
import com.rajat.admin_service.model.price.request.UpdateProductPriceClientRequest;
import com.rajat.admin_service.model.price.response.UpdateProductPriceClientResponse;
import com.rajat.admin_service.model.product.request.UpdateProductClientRequest;
import com.rajat.admin_service.model.product.response.UpdateProductClientResponse;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class UpdateAdminServiceImplTest {

  @Mock private ModelMapper modelMapper;
  @Mock private UpdateProductService updateProductService;
  @Mock private UpdatePriceService updatePriceService;
  @Mock private UpdateInventoryService updateInventoryService;

  @InjectMocks private UpdateAdminServiceImpl updateAdminService;

  private UpdateProductDetailsDto updateProductDetailsDto;
  private UpdateProductDto updateProductDto;
  private UpdatePriceDto updatePriceDto;
  private UpdateInventoryDto updateInventoryDto;

  @BeforeEach
  void setUp() {
    updateProductDto = new UpdateProductDto();
    updateProductDto.setProductId(UUID.randomUUID());
    updateProductDto.setName("Sample Product");

    updatePriceDto = new UpdatePriceDto();
    updatePriceDto.setCurrency("USD");
    updatePriceDto.setAmount(100.0f);

    updateInventoryDto = new UpdateInventoryDto();
    updateInventoryDto.setTotal(10);
    updateInventoryDto.setReserved(2);

    updateProductDetailsDto = new UpdateProductDetailsDto();
    updateProductDetailsDto.setProduct(updateProductDto);
    updateProductDetailsDto.setPrice(updatePriceDto);
    updateProductDetailsDto.setInventory(updateInventoryDto);
  }

  @Test
  void testUpdateProductsDetails_withAllComponents() {
    Set<UpdateProductDetailsDto> requestDtos = Set.of(updateProductDetailsDto);

    UpdateProductClientRequest productClientRequest = new UpdateProductClientRequest();
    UpdateProductPriceClientRequest priceClientRequest = new UpdateProductPriceClientRequest();
    UpdateProductInventoryClientRequest inventoryClientRequest =
        new UpdateProductInventoryClientRequest();

    when(modelMapper.map(updateProductDto, UpdateProductClientRequest.class))
        .thenReturn(productClientRequest);
    when(modelMapper.map(updatePriceDto, UpdateProductPriceClientRequest.class))
        .thenReturn(priceClientRequest);
    when(modelMapper.map(updateInventoryDto, UpdateProductInventoryClientRequest.class))
        .thenReturn(inventoryClientRequest);
    when(modelMapper.map(updateProductDto, UpdateProductClientRequest.class))
        .thenAnswer(
            invocation -> {
              UpdateProductClientRequest request = new UpdateProductClientRequest();
              request.setId(updateProductDto.getProductId()); // Ensure ID is mapped
              return request;
            });

    Set<UpdateProductClientResponse> productResponses = Set.of(new UpdateProductClientResponse());
    Set<UpdateProductPriceClientResponse> priceResponses =
        Set.of(new UpdateProductPriceClientResponse());
    Set<UpdateProductInventoryClientResponse> inventoryResponses =
        Set.of(new UpdateProductInventoryClientResponse());

    when(updateProductService.updateProducts(anySet())).thenReturn(productResponses);
    when(updatePriceService.updateProductPrice(anySet())).thenReturn(priceResponses);
    when(updateInventoryService.updateProductInventory(anySet())).thenReturn(inventoryResponses);

    Set<UpdateProductDetailsResponseDto> result =
        updateAdminService.updateProductsDetails(requestDtos);

    verify(updateProductService, times(1)).updateProducts(anySet());
    verify(updatePriceService, times(1)).updateProductPrice(anySet());
    verify(updateInventoryService, times(1)).updateProductInventory(anySet());

    assertEquals(1, result.size());
    assertEquals("Product updated.", result.iterator().next().getStatus());
  }
}
