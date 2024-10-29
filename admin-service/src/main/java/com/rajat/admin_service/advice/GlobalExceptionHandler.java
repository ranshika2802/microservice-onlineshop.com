package com.rajat.admin_service.advice;

import com.rajat.admin_service.dto.response.retrieve.ApiResponse;
import feign.FeignException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiResponse handleConstraintViolationException(ConstraintViolationException ex) {
    Set<String> errorMessages =
        ex.getConstraintViolations().stream()
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.toSet());
    return new ApiResponse(false, errorMessages, null);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiResponse handleValidationException(MethodArgumentNotValidException ex) {
    Set<String> messages =
        ex.getBindingResult().getFieldErrors().stream()
            .map(error -> error.getDefaultMessage())
            .collect(Collectors.toSet());
    return new ApiResponse(false, messages, null);
  }

  @ExceptionHandler(FeignException.NotFound.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ApiResponse handleNotFoundException(FeignException.NotFound notFoundException) {
    return new ApiResponse(false, Set.of(notFoundException.getMessage()), null);
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ApiResponse handleException(Exception exception) {
    return new ApiResponse(false, Set.of(exception.getMessage()), null);
  }
}
