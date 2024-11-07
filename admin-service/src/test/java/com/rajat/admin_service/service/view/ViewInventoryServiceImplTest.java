package com.rajat.admin_service.service.view;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.*;

import com.rajat.admin_service.client.InventoryClient;
import com.rajat.admin_service.model.inventory.ApiInventoryClientResponse;
import com.rajat.admin_service.model.inventory.response.ViewProductInventoryClientResponse;
import com.rajat.admin_service.service.view.ViewInventoryServiceImpl;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Set;
import java.util.UUID;

class ViewInventoryServiceImplTest {

  @InjectMocks
  private ViewInventoryServiceImpl viewInventoryServiceImpl;

  @Mock
  private InventoryClient inventoryClient;

  @Mock
  private CircuitBreaker circuitBreaker;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    // Mock CircuitBreakerConfig to avoid NullPointerException
    CircuitBreakerConfig circuitBreakerConfig = mock(CircuitBreakerConfig.class);
    when(circuitBreaker.getCircuitBreakerConfig()).thenReturn(circuitBreakerConfig);
    when(circuitBreakerConfig.isWritableStackTraceEnabled()).thenReturn(false);
  }

  @Test
  void testViewInventoryByProductId_Success() {
    UUID productId = UUID.randomUUID();
    Set<UUID> productIds = Set.of(productId);

    ViewProductInventoryClientResponse inventoryResponse = new ViewProductInventoryClientResponse();
    inventoryResponse.setProductId(productId);
    inventoryResponse.setTotal(100);
    inventoryResponse.setReserved(20);

    when(inventoryClient.viewInventoryByProductId(anySet()))
            .thenReturn(Set.of(inventoryResponse));

    ApiInventoryClientResponse response = viewInventoryServiceImpl.viewInventoryByProductId(productIds);

    assertTrue(response.isSuccess());
    assertNull(response.getMessage());
    assertNotNull(response.getViewProductInventoryClientResponses());
    assertEquals(1, response.getViewProductInventoryClientResponses().size());
    assertEquals(80, response.getViewProductInventoryClientResponses().iterator().next().getAvailable());
  }

  @Test
  void testViewInventoryByProductId_Failure() {
    Set<UUID> productIds = Set.of(UUID.randomUUID());

    when(inventoryClient.viewInventoryByProductId(anySet()))
            .thenThrow(new RuntimeException("Inventory service unavailable"));

    ApiInventoryClientResponse response = viewInventoryServiceImpl.fallBackForInventory(productIds, new RuntimeException("Inventory service unavailable"));

    assertFalse(response.isSuccess());
    assertEquals("inventoryService NAN", response.getMessage());
    assertNull(response.getViewProductInventoryClientResponses());
  }

  @Test
  void testFallBackForInventory() {
    Set<UUID> productIds = Set.of(UUID.randomUUID());

    // Use the createCallNotPermittedException with the mock CircuitBreaker
    Throwable throwable = new RuntimeException("Inventory service unavailable");

    ApiInventoryClientResponse response = viewInventoryServiceImpl.fallBackForInventory(productIds, throwable);

    assertFalse(response.isSuccess());
    assertEquals("inventoryService NAN", response.getMessage());
    assertNull(response.getViewProductInventoryClientResponses());
  }

  @Test
  void testViewInventoryByProductId_CircuitBreakerOpen() {
    Set<UUID> productIds = Set.of(UUID.randomUUID());

    // Mock the behavior to simulate an open circuit breaker
    doThrow(CallNotPermittedException.createCallNotPermittedException(circuitBreaker))
            .when(inventoryClient).viewInventoryByProductId(anySet());

    ApiInventoryClientResponse response = viewInventoryServiceImpl.fallBackForInventory(productIds, new RuntimeException("CircuitBreaker Open"));

    assertFalse(response.isSuccess());
    assertEquals("inventoryService NAN", response.getMessage());
    assertNull(response.getViewProductInventoryClientResponses());
  }
}
