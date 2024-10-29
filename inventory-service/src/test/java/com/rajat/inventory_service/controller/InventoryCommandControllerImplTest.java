package com.rajat.inventory_service.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.rajat.inventory_service.dto.AddProductInventoryRequest;
import com.rajat.inventory_service.service.AddProductInventoryService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class InventoryCommandControllerImplTest {

  @Mock private AddProductInventoryService addInventoryService;

  @Mock private Validator validator;

  @InjectMocks private InventoryCommandControllerImpl inventoryCommandController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testAddProductInventory_ValidRequest() {
    // Arrange
    UUID productId = UUID.randomUUID();
    AddProductInventoryRequest request = new AddProductInventoryRequest(1L, productId, 10, 2);
    Set<AddProductInventoryRequest> requests = Set.of(request);

    when(addInventoryService.addProductInventory(requests)).thenReturn(Collections.emptyList());

    // Act
    ResponseEntity<?> response =
        ResponseEntity.ok(inventoryCommandController.addProductInventory(requests));

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void testAddProductInventory_InvalidRequest() {
    // Arrange
    UUID productId = UUID.randomUUID();
    AddProductInventoryRequest request = new AddProductInventoryRequest(1L, productId, -10, -2);
    Set<AddProductInventoryRequest> requests = Set.of(request);

    // Act and Assert
    try {
      inventoryCommandController.addProductInventory(requests);
    } catch (ConstraintViolationException e) {
      assertEquals(2, e.getConstraintViolations().size());
    }
  }

  @Test
  void testAddProductInventory_EmptyProductID() {
    // Arrange
    AddProductInventoryRequest request = new AddProductInventoryRequest(1L, null, 10, 2);
    Set<AddProductInventoryRequest> requests = Set.of(request);

    // Act and Assert
    try {
      inventoryCommandController.addProductInventory(requests);
    } catch (ConstraintViolationException e) {
      assertEquals(1, e.getConstraintViolations().size());
      assertEquals(
          "Product ID must not be null",
          e.getConstraintViolations().iterator().next().getMessage());
    }
  }
}
