package com.rajat.admin_service.service.update;

import com.rajat.admin_service.client.ProductClient;
import com.rajat.admin_service.model.product.request.UpdateProductClientRequest;
import com.rajat.admin_service.model.product.request.UpdateAttributeClientRequest;
import com.rajat.admin_service.model.product.response.UpdateProductClientResponse;
import com.rajat.admin_service.model.product.response.UpdateAttributesResponse;
import com.rajat.admin_service.util.AdminUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UpdateProductServiceImplTest {

  @Mock private ProductClient productClient;

  @Mock private AdminUtils adminUtils;

  @InjectMocks private UpdateProductServiceImpl updateProductService;

  private UpdateProductClientRequest productRequest;
  private UpdateAttributeClientRequest attributeRequest;

  @BeforeEach
  void setUp() {
    attributeRequest = new UpdateAttributeClientRequest();
    attributeRequest.setName("Color");
    attributeRequest.setValue("Red");

    productRequest = new UpdateProductClientRequest();
    productRequest.setId(UUID.randomUUID());
    productRequest.setName("Sample Product");
    productRequest.setBrand("Sample Brand");
    productRequest.setCategory("Sample Category");
    productRequest.setDescription("Sample Description");
    productRequest.setAttributes(Set.of(attributeRequest));
  }

  @Test
  void testUpdateProducts() {
    Set<UpdateProductClientRequest> productRequests = Set.of(productRequest);

    UpdateAttributesResponse attributeResponse = new UpdateAttributesResponse();
    attributeResponse.setName("Color");
    attributeResponse.setValue("Red");

    UpdateProductClientResponse expectedResponse = new UpdateProductClientResponse();
    expectedResponse.setId(productRequest.getId());
    expectedResponse.setName("Sample Product");
    expectedResponse.setBrand("Sample Brand");
    expectedResponse.setCategory("Sample Category");
    expectedResponse.setDescription("Sample Description");
    expectedResponse.setAttributesResponses(Set.of(attributeResponse));

    Set<UpdateProductClientResponse> expectedResponses = Set.of(expectedResponse);

    when(productClient.updateProducts(productRequests)).thenReturn(expectedResponses);

    Set<UpdateProductClientResponse> actualResponses =
        updateProductService.updateProducts(productRequests);

    verify(productClient).updateProducts(productRequests);
    assertEquals(expectedResponses, actualResponses);
  }
}
