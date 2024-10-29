package com.rajat.admin_service.service.view;

import com.rajat.admin_service.client.PriceClient;
import com.rajat.admin_service.model.price.ApiPriceClientResponse;
import com.rajat.admin_service.model.product.ApiProductClientResponse;
import feign.FeignException;
import feign.RetryableException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Set;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ViewPriceServiceImpl implements ViewPriceService {

  private static final String PRICE_SERVICE = "priceService";
  private static final String FALLBACK_METHOD = "fallBackForPrice";
  private final PriceClient priceClient;

  @Override
  @Retry(name = PRICE_SERVICE, fallbackMethod = FALLBACK_METHOD)
  @CircuitBreaker(name = PRICE_SERVICE)
  public ApiPriceClientResponse viewPriceByProductId(@NonNull Set<UUID> productId) {
    log.info("inside viewPriceByProductId method.");
    return new ApiPriceClientResponse(true, null, priceClient.viewPriceByProductId(productId));
  }

  public ApiPriceClientResponse fallBackForPrice(
      Set<String> categories, SocketTimeoutException socketTimeoutException) {
    log.info("inside fallBackForPrice for SocketTimeoutException");
    return new ApiPriceClientResponse(false, PRICE_SERVICE + "NAN", null);
  }

  public ApiPriceClientResponse fallBackForPrice(
      Set<String> categories, FeignException.ServiceUnavailable serviceUnavailable) {
    log.info("inside fallBackForPrice for FeignException.ServiceUnavailable");
    return new ApiPriceClientResponse(false, PRICE_SERVICE + "NAN", null);
  }

  public ApiPriceClientResponse fallBackForPrice(
      Set<String> categories, RetryableException retryableException) {
    log.info("inside fallBackForPrice for RetryableException");
    return new ApiPriceClientResponse(false, PRICE_SERVICE + "NAN", null);
  }

  public ApiPriceClientResponse fallBackForPrice(
      Set<String> categories, ConnectException connectException) {
    log.info("inside fallBackForPrice for ConnectException");
    return new ApiPriceClientResponse(false, PRICE_SERVICE + "NAN", null);
  }
}
