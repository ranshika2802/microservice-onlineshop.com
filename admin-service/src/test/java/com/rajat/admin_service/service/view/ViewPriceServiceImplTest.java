package com.rajat.admin_service.service.view;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.*;

import com.rajat.admin_service.client.PriceClient;
import com.rajat.admin_service.model.price.ApiPriceClientResponse;
import com.rajat.admin_service.model.price.response.ViewProductPriceClientResponse;
import feign.FeignException;
import feign.Request;
import feign.RequestTemplate;
import feign.RetryableException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ViewPriceServiceImplTest {

  @InjectMocks private ViewPriceServiceImpl viewPriceServiceImpl;

  @Mock private PriceClient priceClient;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testViewPriceByProductId_Success() {
    UUID productId = UUID.randomUUID();
    Set<UUID> productIds = Set.of(productId);

    ViewProductPriceClientResponse priceResponse = new ViewProductPriceClientResponse();
    priceResponse.setProductId(productId);
    priceResponse.setCurrency("USD");
    priceResponse.setAmount(99.99f);

    when(priceClient.viewPriceByProductId(anySet())).thenReturn(Set.of(priceResponse));

    ApiPriceClientResponse response = viewPriceServiceImpl.viewPriceByProductId(productIds);

    assertTrue(response.isSuccess());
    assertNull(response.getMessage());
    assertNotNull(response.getViewProductPriceClientResponses());
    assertEquals(1, response.getViewProductPriceClientResponses().size());
    assertEquals(
        "USD", response.getViewProductPriceClientResponses().iterator().next().getCurrency());
  }

  @Test
  void testFallBackForPrice_SocketTimeoutException() {
    Set<String> categories = Set.of("Electronics");

    ApiPriceClientResponse response =
        viewPriceServiceImpl.fallBackForPrice(categories, new SocketTimeoutException("Timeout"));

    assertFalse(response.isSuccess());
    assertEquals("priceServiceNAN", response.getMessage());
    assertNull(response.getViewProductPriceClientResponses());
  }

  @Test
  void testFallBackForPrice_ServiceUnavailable() {
    Set<String> categories = Set.of("Electronics");

    // Create a minimal Feign.Request object as required by the FeignException.ServiceUnavailable
    // constructor
    Request request =
        Request.create(
            Request.HttpMethod.GET,
            "",
            Collections.emptyMap(),
            Request.Body.empty(),
            new RequestTemplate());

    ApiPriceClientResponse response =
        viewPriceServiceImpl.fallBackForPrice(
            categories,
            new FeignException.ServiceUnavailable(
                "Service Unavailable", request, null, Collections.emptyMap()));

    assertFalse(response.isSuccess());
    assertEquals("priceServiceNAN", response.getMessage());
    assertNull(response.getViewProductPriceClientResponses());
  }

  @Test
  void testFallBackForPrice_RetryableException() {
    Set<String> categories = Set.of("Electronics");

    // Create a minimal Feign.Request object as required by the RetryableException constructor
    Request request =
        Request.create(
            Request.HttpMethod.GET,
            "",
            Collections.emptyMap(),
            Request.Body.empty(),
            new RequestTemplate());

    ApiPriceClientResponse response =
        viewPriceServiceImpl.fallBackForPrice(
            categories,
            new RetryableException(
                503, "Retryable Error", Request.HttpMethod.GET, (Long) null, request));

    assertFalse(response.isSuccess());
    assertEquals("priceServiceNAN", response.getMessage());
    assertNull(response.getViewProductPriceClientResponses());
  }

  @Test
  void testFallBackForPrice_ConnectException() {
    Set<String> categories = Set.of("Electronics");

    ApiPriceClientResponse response =
        viewPriceServiceImpl.fallBackForPrice(
            categories, new ConnectException("Connection refused"));

    assertFalse(response.isSuccess());
    assertEquals("priceServiceNAN", response.getMessage());
    assertNull(response.getViewProductPriceClientResponses());
  }
}
