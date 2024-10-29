package com.rajat.customer_service.service;

import com.rajat.customer_service.client.ViewClient;
import com.rajat.customer_service.dto.ViewProductDetailsResponse;
import com.rajat.customer_service.model.ApiClientResponse;
import com.rajat.customer_service.utils.ApiResponseUtils;
import feign.FeignException;
import feign.RetryableException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ViewServiceImpl implements ViewService {

  private static final String VIEW_SERVICE = "viewService";
  private static final String FALLBACK_METHOD = "fallBackForViewService";
  private static Integer counter = 1;
  private final ViewClient viewClient;
  private final ApiResponseUtils apiResponseUtils;

  @Override
  @CircuitBreaker(name = VIEW_SERVICE)
  @Retry(name = VIEW_SERVICE, fallbackMethod = FALLBACK_METHOD)
  public Map<String, Set<ViewProductDetailsResponse>> fetchProductsByCategories(
      @NonNull Set<String> uncachedCategories) {
    log.info("Retry count = {}", counter++);
    ApiClientResponse apiClientResponse =
        viewClient.viewProductsDetailsByCategory(uncachedCategories);
    if (!apiClientResponse.isSuccess()) {
      String key = apiClientResponse.getMessage().stream().findFirst().orElse("NaN");
      Map NanToNull = new HashMap<>();
      NanToNull.put(key, null);
      return NanToNull;
    }
    return apiResponseUtils.mapResponse(apiClientResponse);
  }

  public Map<String, Set<ViewProductDetailsResponse>> fallBackForViewService(
      Set<String> uncachedCategories, SocketTimeoutException socketTimeoutException) {
    log.info("inside fallback method.");
    counter = 0;
    Map NanToNull = new HashMap<>();
    NanToNull.put(VIEW_SERVICE + "NAN", null);
    return NanToNull;
  }

  public Map<String, Set<ViewProductDetailsResponse>> fallBackForViewService(
      Set<String> uncachedCategories, FeignException.ServiceUnavailable serviceUnavailable) {
    log.info("inside fallback method.");
    counter = 0;
    Map NanToNull = new HashMap<>();
    NanToNull.put(VIEW_SERVICE + "NAN", null);
    return NanToNull;
  }

  public Map<String, Set<ViewProductDetailsResponse>> fallBackForViewService(
      Set<String> uncachedCategories, RetryableException retryableException) {
    log.info("inside fallback method.");
    counter = 0;
    Map NanToNull = new HashMap<>();
    NanToNull.put(VIEW_SERVICE + "NAN", null);
    return NanToNull;
  }
}
