package com.rajat.admin_service.service.add;

import com.rajat.admin_service.model.price.request.AddProductPriceClientRequest;
import com.rajat.admin_service.model.price.response.AddProductPriceClientResponse;

import java.util.Set;

public interface AddPriceService {
  Set<AddProductPriceClientResponse> addProductPrice(
      Set<AddProductPriceClientRequest> addProductPriceClientRequests);
}
