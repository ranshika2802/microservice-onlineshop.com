package com.rajat.customer_service.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.rajat.customer_service.client.ViewClient;
import com.rajat.customer_service.dto.ViewCategoryResponse;
import com.rajat.customer_service.dto.ViewProductDetailsResponse;
import com.rajat.customer_service.model.ApiClientResponse;
import com.rajat.customer_service.model.CategoryClientResponse;
import com.rajat.customer_service.model.ViewProductDetailsClientResponse;
import com.rajat.customer_service.utils.ApiResponseUtils;
import feign.FeignException;
import feign.RetryableException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ViewServiceImplTest {

  private static final Set<String> CATEGORIES = Set.of("Clothes");
  @Mock private ViewClient viewClient;
  @Mock private ApiResponseUtils apiResponseUtils;
  @InjectMocks private ViewServiceImpl viewService;

  @BeforeEach
  void setUp() {
//    MockitoAnnotations.openMocks(this);
    reset(viewClient, apiResponseUtils);
  }

  @Test
  void testFetchProductsByCategories_SuccessResponse() {
    // Set up mock response for client
    ViewProductDetailsClientResponse clientProductResponse = new ViewProductDetailsClientResponse();
    CategoryClientResponse category = new CategoryClientResponse();
    category.setId(1L);
    category.setName("Clothes");
    clientProductResponse.setCategory(category);

    ApiClientResponse clientResponse = new ApiClientResponse(true, Set.of("Data fetched successfully"), Set.of(clientProductResponse));

    // Define the expected response for viewClient mock
    when(viewClient.viewProductsDetailsByCategory(anySet())).thenReturn(clientResponse);

    // Mock the mapped response
    ViewProductDetailsResponse mappedProductResponse = new ViewProductDetailsResponse();
    ViewCategoryResponse mappedCategory = new ViewCategoryResponse();
    mappedCategory.setId(1L);
    mappedCategory.setName("Clothes");
    mappedProductResponse.setCategory(mappedCategory);

    Map<String, Set<ViewProductDetailsResponse>> expectedResponse = new HashMap<>();
    expectedResponse.put("Clothes", Set.of(mappedProductResponse));

    // Set up the mapping mock
    when(apiResponseUtils.mapResponse(clientResponse)).thenReturn(expectedResponse);

    // Invoke method
    Map<String, Set<ViewProductDetailsResponse>> actualResponse = viewService.fetchProductsByCategories(Set.of("Clothes"));

    // Capture and verify the viewClient call
    ArgumentCaptor<Set<String>> captor = ArgumentCaptor.forClass(Set.class);
    verify(viewClient, times(1)).viewProductsDetailsByCategory(captor.capture());

    // Verify the captured argument matches the expected input
    Assertions.assertEquals(Set.of("Clothes"), captor.getValue());

    // Assert the response
    Assertions.assertEquals(expectedResponse, actualResponse);
  }

  @Test
  void testFetchProductsByCategories_UnsuccessfulResponse() {
    // Mock unsuccessful response from client
    ApiClientResponse clientResponse = new ApiClientResponse(false, Set.of("Error"), null);
    when(viewClient.viewProductsDetailsByCategory(CATEGORIES)).thenReturn(clientResponse);

    // Call method and verify response contains fallback data
    Map<String, Set<ViewProductDetailsResponse>> actualResponse =
        viewService.fetchProductsByCategories(CATEGORIES);
    assertThat(actualResponse).containsKey("Error");
    assertThat(actualResponse.get("Error")).isNull();
  }

  @Test
  void testFallbackForViewService_SocketTimeoutException() {
    // Invoke fallback directly for SocketTimeoutException
    Map<String, Set<ViewProductDetailsResponse>> response =
        viewService.fallBackForViewService(CATEGORIES, new SocketTimeoutException());

    // Verify the response contains the expected fallback data
    assertThat(response).containsKey("viewServiceNAN");
    assertThat(response.get("viewServiceNAN")).isNull();
  }

  @Test
  void testFallbackForViewService_ServiceUnavailableException() {
    // Invoke fallback directly for ServiceUnavailableException
    Map<String, Set<ViewProductDetailsResponse>> response =
        viewService.fallBackForViewService(
            CATEGORIES, mock(FeignException.ServiceUnavailable.class));

    // Verify the response contains the expected fallback data
    assertThat(response).containsKey("viewServiceNAN");
    assertThat(response.get("viewServiceNAN")).isNull();
  }

  @Test
  void testFallbackForViewService_RetryableException() {
    // Invoke fallback directly for RetryableException
    Map<String, Set<ViewProductDetailsResponse>> response =
        viewService.fallBackForViewService(CATEGORIES, mock(RetryableException.class));

    // Verify the response contains the expected fallback data
    assertThat(response).containsKey("viewServiceNAN");
    assertThat(response.get("viewServiceNAN")).isNull();
  }
}
