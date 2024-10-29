package com.rajat.admin_service.service.add;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.rajat.admin_service.client.PriceClient;
import com.rajat.admin_service.model.price.request.AddProductPriceClientRequest;
import com.rajat.admin_service.model.price.response.AddProductPriceClientResponse;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AddPriceServiceImplTest {

  @Mock private PriceClient priceClient;

  @InjectMocks private AddPriceServiceImpl addPriceService;

  private Set<AddProductPriceClientRequest> requestSet;
  private Set<AddProductPriceClientResponse> responseSet;

  @BeforeEach
  void setUp() {
    // Initialize sample requests with productId and other necessary fields
    AddProductPriceClientRequest request1 = new AddProductPriceClientRequest();
    request1.setProductId(UUID.randomUUID());
    request1.setAmount(99.99f);
    request1.setCurrency("USD");
    // Set other properties as needed

    AddProductPriceClientRequest request2 = new AddProductPriceClientRequest();
    request2.setProductId(UUID.randomUUID());
    request2.setAmount(149.99f);
    request2.setCurrency("USD");
    // Set other properties as needed

    requestSet = new HashSet<>();
    requestSet.add(request1);
    requestSet.add(request2);

    // Initialize expected responses
    AddProductPriceClientResponse response1 = new AddProductPriceClientResponse();
    response1.setProductId(request1.getProductId());
    // Set other properties as needed

    AddProductPriceClientResponse response2 = new AddProductPriceClientResponse();
    response2.setProductId(request2.getProductId());
    // Set other properties as needed

    responseSet = new HashSet<>();
    responseSet.add(response1);
    responseSet.add(response2);
  }

  @Test
  void testAddProductPrice_Success() {
    // Arrange
    when(priceClient.addProductPrice(requestSet)).thenReturn(responseSet);

    // Act
    Set<AddProductPriceClientResponse> actualResponse = addPriceService.addProductPrice(requestSet);

    // Assert
    assertNotNull(actualResponse, "The response should not be null");
    assertEquals(
        responseSet.size(), actualResponse.size(), "The size of the response set should match");
    assertTrue(
        actualResponse.containsAll(responseSet),
        "The response set should contain all expected responses");

    // Verify that priceClient.addProductPrice was called exactly once with the correct arguments
    verify(priceClient, times(1)).addProductPrice(requestSet);
  }

  @Test
  void testAddProductPrice_EmptyRequest() {
    // Arrange
    Set<AddProductPriceClientRequest> emptyRequest = new HashSet<>();
    Set<AddProductPriceClientResponse> emptyResponse = new HashSet<>();

    when(priceClient.addProductPrice(emptyRequest)).thenReturn(emptyResponse);

    // Act
    Set<AddProductPriceClientResponse> actualResponse =
        addPriceService.addProductPrice(emptyRequest);

    // Assert
    assertNotNull(actualResponse, "The response should not be null");
    assertTrue(actualResponse.isEmpty(), "The response set should be empty");

    // Verify that priceClient.addProductPrice was called exactly once with the empty set
    verify(priceClient, times(1)).addProductPrice(emptyRequest);
  }

  @Test
  void testAddProductPrice_ExceptionHandling() {
    // Arrange
    when(priceClient.addProductPrice(requestSet))
        .thenThrow(new RuntimeException("Price Service Unavailable"));

    // Act & Assert
    Exception exception =
        assertThrows(
            RuntimeException.class,
            () -> {
              addPriceService.addProductPrice(requestSet);
            });

    assertEquals(
        "Price Service Unavailable", exception.getMessage(), "Exception message should match");

    // Verify that priceClient.addProductPrice was called exactly once
    verify(priceClient, times(1)).addProductPrice(requestSet);
  }

  @Test
  void testAddProductPrice_VerifyRequestContent() {
    // Arrange
    when(priceClient.addProductPrice(anySet())).thenReturn(responseSet);

    // Act
    addPriceService.addProductPrice(requestSet);

    // Assert
    ArgumentCaptor<Set<AddProductPriceClientRequest>> requestCaptor =
        ArgumentCaptor.forClass(Set.class);
    verify(priceClient).addProductPrice(requestCaptor.capture());

    Set<AddProductPriceClientRequest> capturedRequests = requestCaptor.getValue();
    assertEquals(
        requestSet, capturedRequests, "The captured request set should match the input set");
  }
}
