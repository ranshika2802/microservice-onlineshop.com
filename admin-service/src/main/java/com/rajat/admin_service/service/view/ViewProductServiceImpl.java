package com.rajat.admin_service.service.view;

import com.rajat.admin_service.client.ProductClient;
import com.rajat.admin_service.model.product.ApiProductClientResponse;
import com.rajat.admin_service.model.product.response.ViewProductClientResponse;
import feign.FeignException;
import feign.RetryableException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.Set;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ViewProductServiceImpl implements ViewProductService {

  private static final String PRODUCT_SERVICE = "productService";
  private static final String FALLBACK_METHOD = "fallBackForProduct";
  private final ProductClient productClient;

  @Override
  @Retry(name = PRODUCT_SERVICE, fallbackMethod = FALLBACK_METHOD)
  @CircuitBreaker(name = PRODUCT_SERVICE)
  public ApiProductClientResponse viewProductsByCategory(@NonNull final Set<String> categories) {
    Set<ViewProductClientResponse> viewProductClientResponses =
        productClient.viewProductsByCategory(categories);
    log.info("View Product Service at time = {}", new Date());
    return new ApiProductClientResponse(true, null, viewProductClientResponses);
  }

  public ApiProductClientResponse fallBackForProduct(
      Set<String> categories, SocketTimeoutException socketTimeoutException) {
    return new ApiProductClientResponse(false, PRODUCT_SERVICE + "NAN", null);
  }

  public ApiProductClientResponse fallBackForProduct(
      Set<String> categories, FeignException.ServiceUnavailable serviceUnavailable) {
    return new ApiProductClientResponse(false, PRODUCT_SERVICE + "NAN", null);
  }

  public ApiProductClientResponse fallBackForProduct(
      Set<String> categories, RetryableException retryableException) {
    return new ApiProductClientResponse(false, PRODUCT_SERVICE + "NAN", null);
  }

  public ApiProductClientResponse fallBackForProduct(
      Set<String> categories, ConnectException connectException) {
    return new ApiProductClientResponse(false, PRODUCT_SERVICE + "NAN", null);
  }
}
