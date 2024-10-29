package com.rajat.admin_service.service.update;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.rajat.admin_service.client.PriceClient;
import com.rajat.admin_service.model.price.request.UpdateProductPriceClientRequest;
import com.rajat.admin_service.model.price.response.UpdateProductPriceClientResponse;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UpdatePriceServiceImplTest {

  @Mock private PriceClient priceClient;

  @InjectMocks private UpdatePriceServiceImpl updatePriceService;

  private UpdateProductPriceClientRequest priceRequest;

  @BeforeEach
  void setUp() {
    priceRequest = new UpdateProductPriceClientRequest();
    priceRequest.setCurrency("USD");
    priceRequest.setAmount(99.99f);
    priceRequest.setProductId(UUID.randomUUID());
  }

  @Test
  void testUpdateProductPrice() {
    Set<UpdateProductPriceClientRequest> priceRequests = Set.of(priceRequest);

    UpdateProductPriceClientResponse expectedResponse = new UpdateProductPriceClientResponse();
    expectedResponse.setId(1);
    expectedResponse.setCurrency("USD");
    expectedResponse.setAmount(99.99f);
    expectedResponse.setProductId(priceRequest.getProductId());

    Set<UpdateProductPriceClientResponse> expectedResponses = Set.of(expectedResponse);

    when(priceClient.updateProductPrice(priceRequests)).thenReturn(expectedResponses);

    Set<UpdateProductPriceClientResponse> actualResponses =
        updatePriceService.updateProductPrice(priceRequests);

    verify(priceClient).updateProductPrice(priceRequests);
    assertEquals(expectedResponses, actualResponses);
  }
}
