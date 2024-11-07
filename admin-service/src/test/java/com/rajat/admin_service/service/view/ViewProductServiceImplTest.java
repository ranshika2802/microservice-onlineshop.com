package com.rajat.admin_service.service.view;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.*;

import com.rajat.admin_service.client.ProductClient;
import com.rajat.admin_service.model.product.ApiProductClientResponse;
import com.rajat.admin_service.model.product.response.ViewAttributeClientResponse;
import com.rajat.admin_service.model.product.response.ViewCategoryClientResponse;
import com.rajat.admin_service.model.product.response.ViewProductClientResponse;
import com.rajat.admin_service.service.view.ViewProductServiceImpl;
import feign.FeignException;
import feign.Request;
import feign.RequestTemplate;
import feign.RetryableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

public class ViewProductServiceImplTest {

    @InjectMocks
    private ViewProductServiceImpl viewProductServiceImpl;

    @Mock
    private ProductClient productClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testViewProductsByCategory_Success() {
        String category = "Electronics";
        Set<String> categories = Set.of(category);

        ViewProductClientResponse productResponse = new ViewProductClientResponse();
        productResponse.setId(UUID.randomUUID());
        productResponse.setName("Laptop");
        productResponse.setBrand("BrandX");

        ViewCategoryClientResponse categoryResponse = new ViewCategoryClientResponse();
        categoryResponse.setId(1L);
        categoryResponse.setName(category);

        productResponse.setCategory(categoryResponse);

        when(productClient.viewProductsByCategory(anySet())).thenReturn(Set.of(productResponse));

        ApiProductClientResponse response = viewProductServiceImpl.viewProductsByCategory(categories);

        assertTrue(response.isSuccess());
        assertNull(response.getMessage());
        assertNotNull(response.getViewProductClientResponses());
        assertEquals(1, response.getViewProductClientResponses().size());
        assertEquals("Laptop", response.getViewProductClientResponses().iterator().next().getName());
    }

    @Test
    void testFallBackForProduct_SocketTimeoutException() {
        Set<String> categories = Set.of("Electronics");

        ApiProductClientResponse response = viewProductServiceImpl.fallBackForProduct(categories, new SocketTimeoutException("Timeout"));

        assertFalse(response.isSuccess());
        assertEquals("productServiceNAN", response.getMessage());
        assertNull(response.getViewProductClientResponses());
    }

    @Test
    void testFallBackForProduct_ServiceUnavailable() {
        Set<String> categories = Set.of("Electronics");

        // Create a minimal Feign.Request object as required by FeignException.ServiceUnavailable constructor
        Request request = Request.create(Request.HttpMethod.GET, "", Collections.emptyMap(), Request.Body.empty(), new RequestTemplate());

        ApiProductClientResponse response = viewProductServiceImpl.fallBackForProduct(categories, new FeignException.ServiceUnavailable("Service Unavailable", request, null, Collections.emptyMap()));

        assertFalse(response.isSuccess());
        assertEquals("productServiceNAN", response.getMessage());
        assertNull(response.getViewProductClientResponses());
    }

    @Test
    void testFallBackForProduct_RetryableException() {
        Set<String> categories = Set.of("Electronics");

        // Create a minimal Feign.Request object as required by RetryableException constructor
        Request request = Request.create(Request.HttpMethod.GET, "", Collections.emptyMap(), Request.Body.empty(), new RequestTemplate());

        ApiProductClientResponse response = viewProductServiceImpl.fallBackForProduct(categories, new RetryableException(503, "Retryable Error", Request.HttpMethod.GET, (Long) null, request));

        assertFalse(response.isSuccess());
        assertEquals("productServiceNAN", response.getMessage());
        assertNull(response.getViewProductClientResponses());
    }

    @Test
    void testFallBackForProduct_ConnectException() {
        Set<String> categories = Set.of("Electronics");

        ApiProductClientResponse response = viewProductServiceImpl.fallBackForProduct(categories, new ConnectException("Connection refused"));

        assertFalse(response.isSuccess());
        assertEquals("productServiceNAN", response.getMessage());
        assertNull(response.getViewProductClientResponses());
    }
}

