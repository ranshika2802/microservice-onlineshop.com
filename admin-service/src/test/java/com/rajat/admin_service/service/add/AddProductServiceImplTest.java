package com.rajat.admin_service.service.add;

import com.rajat.admin_service.client.ProductClient;
import com.rajat.admin_service.model.product.request.AddAttributeClientRequest;
import com.rajat.admin_service.model.product.request.AddProductClientRequest;
import com.rajat.admin_service.model.product.response.AddAttributeClientResponse;
import com.rajat.admin_service.model.product.response.AddProductClientResponse;
import com.rajat.admin_service.util.AdminUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddProductServiceImplTest {

    @Mock
    private ProductClient productClient;

    @Mock
    private AdminUtils adminUtils;

    @InjectMocks
    private AddProductServiceImpl addProductService;

    private Set<AddProductClientRequest> requestSet;
    private Set<AddProductClientResponse> responseSet;

    @BeforeEach
    void setUp() {
        // Initialize sample requests with all necessary fields
        AddProductClientRequest request1 = new AddProductClientRequest();
        request1.setId(UUID.randomUUID());
        request1.setName("Product A");
        request1.setBrand("Brand X");
        request1.setCategory("Category 1");
        request1.setDescription("Description for Product A");
        request1.setAttributes(new HashSet<>(Arrays.asList(
                createAddAttributeClientRequest("Color", "Red"),
                createAddAttributeClientRequest("Size", "M")
        )));

        AddProductClientRequest request2 = new AddProductClientRequest();
        request2.setId(UUID.randomUUID());
        request2.setName("Product B");
        request2.setBrand("Brand Y");
        request2.setCategory("Category 2");
        request2.setDescription("Description for Product B");
        request2.setAttributes(new HashSet<>(Collections.singletonList(
                createAddAttributeClientRequest("Material", "Cotton")
        )));

        requestSet = new HashSet<>();
        requestSet.add(request1);
        requestSet.add(request2);

        // Initialize expected responses
        AddProductClientResponse response1 = new AddProductClientResponse();
        response1.setId(request1.getId());
        response1.setName(request1.getName());
        response1.setBrand(request1.getBrand());
        response1.setCategory(request1.getCategory());
        response1.setDescription(request1.getDescription());
        response1.setAttributes(new ArrayList<>(Arrays.asList(
                createAddAttributeClientResponse("Color", "Red"),
                createAddAttributeClientResponse("Size", "M")
        )));

        AddProductClientResponse response2 = new AddProductClientResponse();
        response2.setId(request2.getId());
        response2.setName(request2.getName());
        response2.setBrand(request2.getBrand());
        response2.setCategory(request2.getCategory());
        response2.setDescription(request2.getDescription());
        response2.setAttributes(new ArrayList<>(Collections.singletonList(
                createAddAttributeClientResponse("Material", "Cotton")
        )));

        responseSet = new HashSet<>();
        responseSet.add(response1);
        responseSet.add(response2);
    }

    @Test
    void testAddProducts_Success() {
        // Arrange
        when(productClient.addProducts(requestSet)).thenReturn(responseSet);

        // Act
        Set<AddProductClientResponse> actualResponse = addProductService.addProducts(requestSet);

        // Assert
        assertNotNull(actualResponse, "The response should not be null");
        assertEquals(responseSet.size(), actualResponse.size(), "The size of the response set should match");
        assertTrue(actualResponse.containsAll(responseSet), "The response set should contain all expected responses");

        // Verify that productClient.addProducts was called exactly once with the correct arguments
        verify(productClient, times(1)).addProducts(requestSet);
    }

    @Test
    void testAddProducts_EmptyRequest() {
        // Arrange
        Set<AddProductClientRequest> emptyRequest = new HashSet<>();
        Set<AddProductClientResponse> emptyResponse = new HashSet<>();

        when(productClient.addProducts(emptyRequest)).thenReturn(emptyResponse);

        // Act
        Set<AddProductClientResponse> actualResponse = addProductService.addProducts(emptyRequest);

        // Assert
        assertNotNull(actualResponse, "The response should not be null");
        assertTrue(actualResponse.isEmpty(), "The response set should be empty");

        // Verify that productClient.addProducts was called exactly once with the empty set
        verify(productClient, times(1)).addProducts(emptyRequest);
    }

    @Test
    void testAddProducts_ExceptionHandling() {
        // Arrange
        when(productClient.addProducts(requestSet))
                .thenThrow(new RuntimeException("Product Service Unavailable"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            addProductService.addProducts(requestSet);
        });

        assertEquals("Product Service Unavailable", exception.getMessage(), "Exception message should match");

        // Verify that productClient.addProducts was called exactly once
        verify(productClient, times(1)).addProducts(requestSet);
    }

    @Test
    void testAddProducts_VerifyRequestContent() {
        // Arrange
        when(productClient.addProducts(anySet())).thenReturn(responseSet);

        // Act
        addProductService.addProducts(requestSet);

        // Assert
        ArgumentCaptor<Set<AddProductClientRequest>> requestCaptor =
                ArgumentCaptor.forClass(Set.class);
        verify(productClient).addProducts(requestCaptor.capture());

        Set<AddProductClientRequest> capturedRequests = requestCaptor.getValue();
        assertEquals(requestSet, capturedRequests, "The captured request set should match the input set");
    }

    // Helper methods to create AddAttributeClientRequest and AddAttributeClientResponse instances
    private AddAttributeClientRequest createAddAttributeClientRequest(String name, String value) {
        AddAttributeClientRequest attributeRequest = new AddAttributeClientRequest();
        attributeRequest.setName(name);
        attributeRequest.setValue(value);
        return attributeRequest;
    }

    private AddAttributeClientResponse createAddAttributeClientResponse(String name, String value) {
        AddAttributeClientResponse attributeResponse = new AddAttributeClientResponse();
        attributeResponse.setName(name);
        attributeResponse.setValue(value);
        return attributeResponse;
    }
}
