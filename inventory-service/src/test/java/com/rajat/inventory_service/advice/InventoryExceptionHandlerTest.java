package com.rajat.inventory_service.advice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;

@ExtendWith(MockitoExtension.class)
class InventoryExceptionHandlerTest {

  @InjectMocks private InventoryExceptionHandler exceptionHandler;

  @Mock private BindingResult bindingResult;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testHandleTypeMismatchException() {
    HttpMessageNotReadableException exception =
        new HttpMessageNotReadableException("Invalid JSON format");

    ResponseEntity<String> response = exceptionHandler.handleTypeMismatchException(exception);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Invalid request body: Invalid JSON format", response.getBody());
  }

  @Test
  void testHandleValidationExceptions() {
    FieldError fieldError = new FieldError("objectName", "fieldName", "Field must not be null");
    when(bindingResult.getAllErrors()).thenReturn(Collections.singletonList(fieldError));
    MethodArgumentNotValidException exception =
        new MethodArgumentNotValidException(null, bindingResult);

    ResponseEntity<Map<String, String>> response =
        exceptionHandler.handleValidationExceptions(exception);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Field must not be null", response.getBody().get("fieldName"));
  }

  @Test
  void testHandleConstraintViolationExceptions() {
    // Arrange: Create a mock Path object and configure it
    Path mockPath = mock(Path.class);
    when(mockPath.toString()).thenReturn("fieldName");

    // Arrange: Mock a ConstraintViolation object and set up its behavior
    ConstraintViolation<?> mockViolation = mock(ConstraintViolation.class);
    when(mockViolation.getPropertyPath()).thenReturn(mockPath); // Use the mock Path here
    when(mockViolation.getMessage()).thenReturn("must not be null");

    // Create a ConstraintViolationException with the mock violation
    ConstraintViolationException exception =
        new ConstraintViolationException(Set.of(mockViolation));

    // Act: Call the handler method
    ResponseEntity<Map<String, String>> response =
        exceptionHandler.handleConstraintViolationExceptions(exception);

    // Assert: Verify the response
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("must not be null", response.getBody().get("fieldName"));
  }

  @Test
  void testHandleMissingServletRequestParameterException() {
    MissingServletRequestParameterException exception =
        new MissingServletRequestParameterException("param", "String");

    ResponseEntity<Map<String, String>> response =
        exceptionHandler.handleMissingServletRequestParameterException(exception);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Missing required request parameter", response.getBody().get("error"));
    assertEquals("param parameter is missing", response.getBody().get("message"));
  }

  @Test
  void testHandleIllegalArgumentException() {
    IllegalArgumentException exception = new IllegalArgumentException("Invalid argument");

    ResponseEntity<Map<String, String>> response =
        exceptionHandler.handleIllegalArgumentException(exception);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Invalid argument", response.getBody().get("message"));
    assertEquals("Invalid argument", response.getBody().get("error"));
  }

  @Test
  void testHandleGeneralException() {
    Exception exception = new Exception("An unknown error occurred");

    ResponseEntity<String> response = exceptionHandler.handle(exception);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("An unknown error occurred", response.getBody());
  }
}
