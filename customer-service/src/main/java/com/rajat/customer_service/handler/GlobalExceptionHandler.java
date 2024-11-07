package com.rajat.customer_service.handler;

import com.rajat.customer_service.dto.ApiResponse;
import feign.FeignException;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

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
