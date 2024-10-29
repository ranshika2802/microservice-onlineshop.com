package com.rajat.admin_service.service.add;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.rajat.admin_service.dto.request.add.AddInventoryDto;
import com.rajat.admin_service.dto.request.add.AddPriceDto;
import com.rajat.admin_service.dto.request.add.AddProductDetailsDto;
import com.rajat.admin_service.dto.request.add.AddProductDto;
import com.rajat.admin_service.dto.response.add.AddProductDetailsResponseDto;
import com.rajat.admin_service.model.inventory.request.AddProductInventoryClientRequest;
import com.rajat.admin_service.model.inventory.response.AddProductInventoryClientResponse;
import com.rajat.admin_service.model.price.request.AddProductPriceClientRequest;
import com.rajat.admin_service.model.price.response.AddProductPriceClientResponse;
import com.rajat.admin_service.model.product.request.AddProductClientRequest;
import com.rajat.admin_service.model.product.response.AddProductClientResponse;
import java.util.Collections;
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
public class AddAdminServiceImplTest {

  @Mock private ModelMapper modelMapper;

  @Mock private AddProductService addProductService;

  @Mock private AddPriceService addPriceService;

  @Mock private AddInventoryService addInventoryService;

  @InjectMocks private AddAdminServiceImpl addAdminService;

  private AddProductDetailsDto productDetailsDto;
  private AddProductDto productDto;
  private AddPriceDto priceDto;
  private AddInventoryDto inventoryDto;

  @BeforeEach
  void setUp() {
    // Initialize DTOs for testing
    productDto = new AddProductDto();
    productDto.setName("TestProduct");
    productDto.setBrand("TestBrand");
    productDto.setCategory("TestCategory");

    priceDto = new AddPriceDto();
    priceDto.setCurrency("USD");
    priceDto.setAmount(100.0f);

    inventoryDto = new AddInventoryDto();
    inventoryDto.setTotal(100);
    inventoryDto.setReserved(10);

    productDetailsDto = new AddProductDetailsDto();
    productDetailsDto.setProduct(productDto);
    productDetailsDto.setPrice(priceDto);
    productDetailsDto.setInventory(inventoryDto);
  }

  @Test
  void addProductsDetails_shouldReturnSuccessfulResponse() {
    UUID productId = UUID.randomUUID();

    AddProductClientRequest productClientRequest = new AddProductClientRequest();
    productClientRequest.setId(productId);
    when(modelMapper.map(any(AddProductDto.class), eq(AddProductClientRequest.class)))
        .thenReturn(productClientRequest);

    AddProductPriceClientRequest priceClientRequest = new AddProductPriceClientRequest();
    priceClientRequest.setProductId(productId);
    when(modelMapper.map(any(AddPriceDto.class), eq(AddProductPriceClientRequest.class)))
        .thenReturn(priceClientRequest);

    AddProductInventoryClientRequest inventoryClientRequest =
        new AddProductInventoryClientRequest();
    inventoryClientRequest.setProductId(productId);
    when(modelMapper.map(any(AddInventoryDto.class), eq(AddProductInventoryClientRequest.class)))
        .thenReturn(inventoryClientRequest);

    when(addProductService.addProducts(anySet()))
        .thenReturn(Set.of(new AddProductClientResponse()));
    when(addPriceService.addProductPrice(anySet()))
        .thenReturn(Set.of(new AddProductPriceClientResponse()));
    when(addInventoryService.addProductInventory(anySet()))
        .thenReturn(Set.of(new AddProductInventoryClientResponse()));

    Set<AddProductDetailsResponseDto> response =
        addAdminService.addProductsDetails(Set.of(productDetailsDto));

    assertEquals(1, response.size());
    assertEquals("Product(s) added successfully.", response.iterator().next().getStatus());
  }

  @Test
  void addProductsDetails_shouldHandleEmptyInput() {
    Set<AddProductDetailsResponseDto> response =
        addAdminService.addProductsDetails(Collections.emptySet());

    assertEquals(1, response.size());
    assertEquals("No products to add.", response.iterator().next().getStatus());

    verifyNoInteractions(addProductService, addPriceService, addInventoryService);
  }

  @Test
  void mapProduct_shouldMapFieldsCorrectly() {
    UUID generatedId = UUID.randomUUID();
    when(modelMapper.map(productDto, AddProductClientRequest.class))
        .thenReturn(new AddProductClientRequest());

    AddProductClientRequest mappedRequest = addAdminService.mapProduct(productDto);
    mappedRequest.setId(generatedId);

    assertEquals(generatedId, mappedRequest.getId());
    verify(modelMapper).map(productDto, AddProductClientRequest.class);
  }

  @Test
  void mapPrice_shouldMapFieldsCorrectly() {
    when(modelMapper.map(priceDto, AddProductPriceClientRequest.class))
        .thenReturn(new AddProductPriceClientRequest());
    UUID productId = UUID.randomUUID();
    AddProductPriceClientRequest mappedPriceRequest = addAdminService.mapPrice(priceDto, productId);

    assertEquals(productId, mappedPriceRequest.getProductId());
    verify(modelMapper).map(priceDto, AddProductPriceClientRequest.class);
  }

  @Test
  void mapInventory_shouldMapFieldsCorrectly() {
    when(modelMapper.map(inventoryDto, AddProductInventoryClientRequest.class))
        .thenReturn(new AddProductInventoryClientRequest());
    UUID productId = UUID.randomUUID();
    AddProductInventoryClientRequest mappedInventoryRequest =
        addAdminService.mapInventory(inventoryDto, productId);

    assertEquals(productId, mappedInventoryRequest.getProductId());
    verify(modelMapper).map(inventoryDto, AddProductInventoryClientRequest.class);
  }
}
