package com.rajat.admin_service.advice;

import com.rajat.admin_service.dto.response.retrieve.ApiResponse;
import feign.FeignException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GlobalExceptionHandlerTest {

  private GlobalExceptionHandler exceptionHandler;

  @BeforeEach
  void setUp() {
    exceptionHandler = new GlobalExceptionHandler();
  }

  @Test
  void handleConstraintViolationException_shouldReturnBadRequest() {
    // Create mock violations
    ConstraintViolation<?> violation1 = mock(ConstraintViolation.class);
    ConstraintViolation<?> violation2 = mock(ConstraintViolation.class);
    when(violation1.getMessage()).thenReturn("Field must not be null");
    when(violation2.getMessage()).thenReturn("Value must be greater than zero");

    Set<ConstraintViolation<?>> violations = Set.of(violation1, violation2);
    ConstraintViolationException exception = new ConstraintViolationException(violations);

    // Call the handler method
    ApiResponse response = exceptionHandler.handleConstraintViolationException(exception);

    // Verify response
    assertEquals(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value());
    assertEquals(Set.of("Field must not be null", "Value must be greater than zero"), response.getMessage());
    assertEquals(false, response.isSuccess());
  }

  @Test
  void handleValidationException_shouldReturnBadRequest() {
    // Create mock BindingResult and FieldErrors
    FieldError error1 = new FieldError("objectName", "fieldName1", "Field cannot be empty");
    FieldError error2 = new FieldError("objectName", "fieldName2", "Invalid format");

    BindingResult bindingResult = mock(BindingResult.class);
    when(bindingResult.getFieldErrors()).thenReturn(List.of(error1, error2));
    MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

    // Call the handler method
    ApiResponse response = exceptionHandler.handleValidationException(exception);

    // Verify response
    assertEquals(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value());
    assertEquals(Set.of("Field cannot be empty", "Invalid format"), response.getMessage());
    assertEquals(false, response.isSuccess());
  }

  @Test
  void handleNotFoundException_shouldReturnNotFound() {
    // Simulate Feign NotFound exception
    FeignException.NotFound notFoundException = mock(FeignException.NotFound.class);
    when(notFoundException.getMessage()).thenReturn("Product not found");

    // Call the handler method
    ApiResponse response = exceptionHandler.handleNotFoundException(notFoundException);

    // Verify response
    assertEquals(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.value());
    assertEquals(Set.of("Product not found"), response.getMessage());
    assertEquals(false, response.isSuccess());
  }

  @Test
  void handleException_shouldReturnInternalServerError() {
    // Create generic exception
    Exception exception = new Exception("Unexpected error occurred");

    // Call the handler method
    ApiResponse response = exceptionHandler.handleException(exception);

    // Verify response
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    assertEquals(Set.of("Unexpected error occurred"), response.getMessage());
    assertEquals(false, response.isSuccess());
  }
}
