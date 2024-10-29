package com.rajat.product_service.advice;

import com.rajat.product_service.exception.CategoryNotFoundException;
import com.rajat.product_service.exception.ProductNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler exceptionHandler;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandleCategoryNotFoundException() {
        String invalidCategory = "invalidCategory";
        // Arrange
        CategoryNotFoundException exception = new CategoryNotFoundException(invalidCategory);

        // Act
        ResponseEntity<String> response = exceptionHandler.handleCategoryNotFoundException(exception);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Category not found: " + invalidCategory, response.getBody());
    }

    @Test
    void testHandleProductNotFoundException() {
        // Arrange
        ProductNotFoundException exception = new ProductNotFoundException();

        // Act
        ResponseEntity<String> response = exceptionHandler.handleProductNotFoundException(exception);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Product not found.", response.getBody());
    }

    @Test
    void testHandleValidationExceptions() {
        // Arrange: Set up a field error in MethodArgumentNotValidException
        FieldError fieldError = new FieldError("objectName", "fieldName", "must not be null");
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

        // Act
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleValidationExceptions(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("must not be null", response.getBody().get("fieldName"));
    }

    @Test
    void testHandleConstraintViolationException() {
        // Arrange: Set up a ConstraintViolation mock
        ConstraintViolation<?> violation = mock(ConstraintViolation.class);
        Path path = mock(Path.class);
        when(path.iterator()).thenReturn(List.of(mock(Path.Node.class)).iterator());
        when(violation.getPropertyPath()).thenReturn(path);
        when(violation.getMessage()).thenReturn("must not be blank");

        Set<ConstraintViolation<?>> violations = new HashSet<>();
        violations.add(violation);
        ConstraintViolationException exception = new ConstraintViolationException(violations);

        // Act
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleConstraintViolationException(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("must not be blank", response.getBody().values().iterator().next());
    }

    @Test
    void testHandleMissingServletRequestParameterException() {
        // Arrange
        MissingServletRequestParameterException exception =
                new MissingServletRequestParameterException("category", "String");

        // Act
        ResponseEntity<String> response = exceptionHandler.handleMissingServletRequestParameterException(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Required request parameter 'category' for method parameter type String is not present", response.getBody());
    }
}
