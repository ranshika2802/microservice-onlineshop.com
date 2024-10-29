package com.rajat.admin_service.service.delete;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.rajat.admin_service.dto.request.delete.DeleteProductAttributesDto;
import com.rajat.admin_service.dto.request.delete.DeleteProductDetailsDto;
import com.rajat.admin_service.dto.response.delete.DeleteProductDetailsResponseDto;
import com.rajat.admin_service.model.inventory.request.DeleteProductInventoryRequest;
import com.rajat.admin_service.model.product.request.DeleteProductAttributesRequest;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
class DeleteAdminServiceImplTest {

  @Mock private ModelMapper modelMapper;

  @Mock private DeleteProductService deleteProductService;

  @Mock private DeletePriceService deletePriceService;

  @Mock private DeleteInventoryService deleteInventoryService;

  @InjectMocks private DeleteAdminServiceImpl deleteAdminService;

  private DeleteProductDetailsDto deleteProductDetailsDto;
  private DeleteProductAttributesDto deleteProductAttributesDto;

  @BeforeEach
  void setUp() {
    // Initialize DeleteProductDetailsDto with sample data
    deleteProductDetailsDto = new DeleteProductDetailsDto();
    deleteProductDetailsDto.setProductIds(
        new HashSet<>(Arrays.asList(UUID.randomUUID(), UUID.randomUUID())));

    // Initialize DeleteProductAttributesDto with sample data
    deleteProductAttributesDto = new DeleteProductAttributesDto();
    Map<UUID, Set<String>> productToAttributesMap = new HashMap<>();

    UUID productId1 = UUID.randomUUID();
    Set<String> attributes1 = new HashSet<>(Arrays.asList("Color", "Size"));
    productToAttributesMap.put(productId1, attributes1);

    UUID productId2 = UUID.randomUUID();
    Set<String> attributes2 = new HashSet<>(Collections.singletonList("Material"));
    productToAttributesMap.put(productId2, attributes2);

    deleteProductAttributesDto.setProductIdToAttributeNamesMap(productToAttributesMap);
  }

  @Test
  void testDeleteProductDetails_Success() {
    // Arrange
    DeleteProductInventoryRequest inventoryRequest = new DeleteProductInventoryRequest();
    when(modelMapper.map(deleteProductDetailsDto, DeleteProductInventoryRequest.class))
        .thenReturn(inventoryRequest);

    // Act
    DeleteProductDetailsResponseDto response =
        deleteAdminService.deleteProductDetails(deleteProductDetailsDto);

    // Assert
    assertNotNull(response, "Response should not be null");
    assertEquals("Product Deleted.", response.getStatus(), "Status message should match");

    // Verify interactions
    verify(deletePriceService, times(1)).deletePrice(deleteProductDetailsDto);
    verify(modelMapper, times(1)).map(deleteProductDetailsDto, DeleteProductInventoryRequest.class);
    verify(deleteInventoryService, times(1)).deleteInventory(inventoryRequest);
    verify(deleteProductService, times(1)).deleteProduct(deleteProductDetailsDto);
  }

  @Test
  void testDeleteProductDetails_NullInput() {
    // Arrange
    // No setup needed as passing null should trigger validation

    // Act & Assert
    assertThrows(
        NullPointerException.class,
        () -> {
          deleteAdminService.deleteProductDetails(null);
        },
        "Should throw NullPointerException when input is null");

    // Verify that no interactions occurred
    verifyNoInteractions(
        deletePriceService, modelMapper, deleteInventoryService, deleteProductService);
  }

  @Test
  void testDeleteProductDetails_ExceptionHandling() {
    // Arrange
    // Mock the void method to throw an exception using doThrow()
    doThrow(new RuntimeException("Price Service Error"))
        .when(deletePriceService)
        .deletePrice(deleteProductDetailsDto);

    // Act & Assert
    Exception exception =
        assertThrows(
            RuntimeException.class,
            () -> {
              deleteAdminService.deleteProductDetails(deleteProductDetailsDto);
            },
            "Should throw RuntimeException when Price Service fails");

    assertEquals("Price Service Error", exception.getMessage(), "Exception message should match");

    // Verify interactions up to the point of exception
    verify(deletePriceService, times(1)).deletePrice(deleteProductDetailsDto);
    // Ensure that no further interactions occurred after the exception
    verifyNoMoreInteractions(modelMapper, deleteInventoryService, deleteProductService);
  }

  @Test
  void testDeleteProductAttributes_Success() {
    // Arrange
    DeleteProductAttributesRequest attributesRequest = new DeleteProductAttributesRequest();
    when(modelMapper.map(deleteProductAttributesDto, DeleteProductAttributesRequest.class))
        .thenReturn(attributesRequest);

    // Act
    DeleteProductDetailsResponseDto response =
        deleteAdminService.deleteProductAttributes(deleteProductAttributesDto);

    // Assert
    assertNotNull(response, "Response should not be null");
    assertEquals("Product Attribute deleted.", response.getStatus(), "Status message should match");

    // Verify interactions
    verify(modelMapper, times(1))
        .map(deleteProductAttributesDto, DeleteProductAttributesRequest.class);
    verify(deleteProductService, times(1)).deleteProductAttributes(attributesRequest);
    verifyNoInteractions(deletePriceService, deleteInventoryService);
  }

  @Test
  void testDeleteProductAttributes_NullInput() {
    // Arrange
    // No setup needed as passing null should trigger validation

    // Act & Assert
    assertThrows(
        NullPointerException.class,
        () -> {
          deleteAdminService.deleteProductAttributes(null);
        },
        "Should throw NullPointerException when input is null");

    // Verify that no interactions occurred
    verifyNoInteractions(modelMapper, deleteProductService);
  }

  @Test
  void testDeleteProductAttributes_ExceptionHandling() {
    // Arrange
    DeleteProductAttributesRequest attributesRequest = new DeleteProductAttributesRequest();
    when(modelMapper.map(deleteProductAttributesDto, DeleteProductAttributesRequest.class))
        .thenReturn(attributesRequest);
    // Mock the void method to throw an exception using doThrow()
    doThrow(new RuntimeException("Product Service Error"))
        .when(deleteProductService)
        .deleteProductAttributes(attributesRequest);

    // Act & Assert
    Exception exception =
        assertThrows(
            RuntimeException.class,
            () -> {
              deleteAdminService.deleteProductAttributes(deleteProductAttributesDto);
            },
            "Should throw RuntimeException when Product Service fails");

    assertEquals("Product Service Error", exception.getMessage(), "Exception message should match");

    // Verify interactions
    verify(modelMapper, times(1))
        .map(deleteProductAttributesDto, DeleteProductAttributesRequest.class);
    verify(deleteProductService, times(1)).deleteProductAttributes(attributesRequest);
  }

  @Test
  void testDeleteProductDetails_VerifyMappingAndDeletion() {
    // Arrange
    DeleteProductInventoryRequest inventoryRequest = new DeleteProductInventoryRequest();
    when(modelMapper.map(deleteProductDetailsDto, DeleteProductInventoryRequest.class))
        .thenReturn(inventoryRequest);

    // Act
    deleteAdminService.deleteProductDetails(deleteProductDetailsDto);

    // Assert
    ArgumentCaptor<DeleteProductInventoryRequest> inventoryRequestCaptor =
        ArgumentCaptor.forClass(DeleteProductInventoryRequest.class);
    ArgumentCaptor<DeleteProductDetailsDto> detailsDtoCaptor =
        ArgumentCaptor.forClass(DeleteProductDetailsDto.class);

    verify(modelMapper).map(detailsDtoCaptor.capture(), eq(DeleteProductInventoryRequest.class));
    assertEquals(
        deleteProductDetailsDto, detailsDtoCaptor.getValue(), "Mapped DTO should match input");

    verify(deleteInventoryService).deleteInventory(inventoryRequestCaptor.capture());
    assertEquals(
        inventoryRequest,
        inventoryRequestCaptor.getValue(),
        "Mapped inventory request should match expected");
  }

  @Test
  void testDeleteProductAttributes_VerifyMappingAndDeletion() {
    // Arrange
    DeleteProductAttributesRequest attributesRequest = new DeleteProductAttributesRequest();
    when(modelMapper.map(deleteProductAttributesDto, DeleteProductAttributesRequest.class))
        .thenReturn(attributesRequest);

    // Act
    deleteAdminService.deleteProductAttributes(deleteProductAttributesDto);

    // Assert
    ArgumentCaptor<DeleteProductAttributesRequest> attributesRequestCaptor =
        ArgumentCaptor.forClass(DeleteProductAttributesRequest.class);
    ArgumentCaptor<DeleteProductAttributesDto> attributesDtoCaptor =
        ArgumentCaptor.forClass(DeleteProductAttributesDto.class);

    verify(modelMapper)
        .map(attributesDtoCaptor.capture(), eq(DeleteProductAttributesRequest.class));
    assertEquals(
        deleteProductAttributesDto,
        attributesDtoCaptor.getValue(),
        "Mapped DTO should match input");

    verify(deleteProductService).deleteProductAttributes(attributesRequestCaptor.capture());
    assertEquals(
        attributesRequest,
        attributesRequestCaptor.getValue(),
        "Mapped attributes request should match expected");
  }
}
