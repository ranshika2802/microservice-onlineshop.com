package com.rajat.admin_service.service.delete;

import com.rajat.admin_service.client.ProductClient;
import com.rajat.admin_service.dto.request.delete.DeleteProductDetailsDto;
import com.rajat.admin_service.model.product.request.DeleteProductAttributesRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class DeleteProductServiceImplTest {

  @Mock private ProductClient productClient;

  @InjectMocks private DeleteProductServiceImpl deleteProductService;

  private DeleteProductDetailsDto deleteProductDetailsDto;
  private DeleteProductAttributesRequest deleteProductAttributesRequest;

  @BeforeEach
  void setUp() {
    deleteProductDetailsDto = new DeleteProductDetailsDto();
    deleteProductDetailsDto.setProductIds(Set.of(UUID.randomUUID(), UUID.randomUUID()));

    deleteProductAttributesRequest = new DeleteProductAttributesRequest();
    deleteProductAttributesRequest.setProductIdToAttributeNamesMap(
        Map.of(
            UUID.randomUUID(), Set.of("color", "size"),
            UUID.randomUUID(), Set.of("weight")));
  }

  @Test
  void testDeleteProduct_callsProductClient() {
    // Act
    deleteProductService.deleteProduct(deleteProductDetailsDto);

    // Assert
    verify(productClient, times(1)).deleteProducts(deleteProductDetailsDto.getProductIds());
  }

  @Test
  void testDeleteProduct_withEmptyProductIds() {
    // Arrange
    deleteProductDetailsDto.setProductIds(Set.of());

    // Act
    deleteProductService.deleteProduct(deleteProductDetailsDto);

    // Assert
    verify(productClient, times(1)).deleteProducts(deleteProductDetailsDto.getProductIds());
  }

  @Test
  void testDeleteProduct_withSingleProductId() {
    // Arrange
    deleteProductDetailsDto.setProductIds(Set.of(UUID.randomUUID()));

    // Act
    deleteProductService.deleteProduct(deleteProductDetailsDto);

    // Assert
    verify(productClient, times(1)).deleteProducts(deleteProductDetailsDto.getProductIds());
  }

  @Test
  void testDeleteProductAttributes_callsProductClient() {
    // Act
    deleteProductService.deleteProductAttributes(deleteProductAttributesRequest);

    // Assert
    verify(productClient, times(1)).deleteProductAttributes(deleteProductAttributesRequest);
  }

  @Test
  void testDeleteProductAttributes_withEmptyAttributeMap() {
    // Arrange
    deleteProductAttributesRequest.setProductIdToAttributeNamesMap(Map.of());

    // Act
    deleteProductService.deleteProductAttributes(deleteProductAttributesRequest);

    // Assert
    verify(productClient, times(1)).deleteProductAttributes(deleteProductAttributesRequest);
  }

  @Test
  void testDeleteProductAttributes_withSingleProductSingleAttribute() {
    // Arrange
    deleteProductAttributesRequest.setProductIdToAttributeNamesMap(
        Map.of(UUID.randomUUID(), Set.of("color")));

    // Act
    deleteProductService.deleteProductAttributes(deleteProductAttributesRequest);

    // Assert
    verify(productClient, times(1)).deleteProductAttributes(deleteProductAttributesRequest);
  }
}
