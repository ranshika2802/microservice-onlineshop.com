package com.rajat.admin_service.service.add;

import com.rajat.admin_service.client.InventoryClient;
import com.rajat.admin_service.model.inventory.request.AddProductInventoryClientRequest;
import com.rajat.admin_service.model.inventory.response.AddProductInventoryClientResponse;
import com.rajat.admin_service.util.AdminUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddInventoryServiceImplTest {

  @Mock private InventoryClient inventoryClient;

  @Mock private AdminUtils adminUtils;

  @InjectMocks private AddInventoryServiceImpl addInventoryService;

  private Set<AddProductInventoryClientRequest> requestSet;
  private Set<AddProductInventoryClientResponse> responseSet;

  @BeforeEach
  void setUp() {
    // Initialize sample requests with productId
    AddProductInventoryClientRequest request1 = new AddProductInventoryClientRequest();
    request1.setProductId(UUID.randomUUID());
    // Set other properties for request1 as needed
    // e.g., request1.setQuantity(100);

    AddProductInventoryClientRequest request2 = new AddProductInventoryClientRequest();
    request2.setProductId(UUID.randomUUID());
    // Set other properties for request2 as needed

    requestSet = new HashSet<>();
    requestSet.add(request1);
    requestSet.add(request2);

    // Initialize expected responses
    AddProductInventoryClientResponse response1 = new AddProductInventoryClientResponse();
    // Set properties for response1 as needed
    // e.g., response1.setStatus("SUCCESS");

    AddProductInventoryClientResponse response2 = new AddProductInventoryClientResponse();
    // Set properties for response2 as needed

    responseSet = new HashSet<>();
    responseSet.add(response1);
    responseSet.add(response2);
  }

  @Test
  void testAddProductInventory_Success() {
    // Arrange
    when(inventoryClient.addProductInventory(requestSet)).thenReturn(responseSet);

    // Act
    Set<AddProductInventoryClientResponse> actualResponse =
        addInventoryService.addProductInventory(requestSet);

    // Assert
    assertNotNull(actualResponse, "The response should not be null");
    assertEquals(
        responseSet.size(), actualResponse.size(), "The size of the response set should match");
    assertTrue(
        actualResponse.containsAll(responseSet),
        "The response set should contain all expected responses");

    // Verify that inventoryClient.addProductInventory was called exactly once with the correct
    // arguments
    verify(inventoryClient, times(1)).addProductInventory(requestSet);
  }

  @Test
  void testAddProductInventory_EmptyRequest() {
    // Arrange
    Set<AddProductInventoryClientRequest> emptyRequest = new HashSet<>();
    Set<AddProductInventoryClientResponse> emptyResponse = new HashSet<>();

    when(inventoryClient.addProductInventory(emptyRequest)).thenReturn(emptyResponse);

    // Act
    Set<AddProductInventoryClientResponse> actualResponse =
        addInventoryService.addProductInventory(emptyRequest);

    // Assert
    assertNotNull(actualResponse, "The response should not be null");
    assertTrue(actualResponse.isEmpty(), "The response set should be empty");

    // Verify that inventoryClient.addProductInventory was called exactly once with the empty set
    verify(inventoryClient, times(1)).addProductInventory(emptyRequest);
  }

  @Test
  void testAddProductInventory_ExceptionHandling() {
    // Arrange
    when(inventoryClient.addProductInventory(requestSet))
        .thenThrow(new RuntimeException("Inventory Service Unavailable"));

    // Act & Assert
    Exception exception =
        assertThrows(
            RuntimeException.class,
            () -> {
              addInventoryService.addProductInventory(requestSet);
            });

    assertEquals("Inventory Service Unavailable", exception.getMessage());

    // Verify that inventoryClient.addProductInventory was called exactly once
    verify(inventoryClient, times(1)).addProductInventory(requestSet);
  }

  @Test
  void testAddProductInventory_VerifyRequestContent() {
    // Arrange
    when(inventoryClient.addProductInventory(anySet())).thenReturn(responseSet);

    // Act
    addInventoryService.addProductInventory(requestSet);

    // Assert
    ArgumentCaptor<Set<AddProductInventoryClientRequest>> requestCaptor =
        ArgumentCaptor.forClass(Set.class);
    verify(inventoryClient).addProductInventory(requestCaptor.capture());

    Set<AddProductInventoryClientRequest> capturedRequests = requestCaptor.getValue();
    assertEquals(
        requestSet, capturedRequests, "The captured request set should match the input set");
  }
}
