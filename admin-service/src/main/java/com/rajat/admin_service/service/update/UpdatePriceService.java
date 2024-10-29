package com.rajat.admin_service.service.update;

import com.rajat.admin_service.model.price.request.UpdateProductPriceClientRequest;
import com.rajat.admin_service.model.price.response.UpdateProductPriceClientResponse;

import java.util.Set;

public interface UpdatePriceService {
  Set<UpdateProductPriceClientResponse> updateProductPrice(
      Set<UpdateProductPriceClientRequest> priceRequests);
}
