package com.rajat.admin_service.service.update;

import com.rajat.admin_service.model.product.request.UpdateProductClientRequest;
import com.rajat.admin_service.model.product.response.UpdateProductClientResponse;

import java.util.Set;

public interface UpdateProductService {

  Set<UpdateProductClientResponse> updateProducts(Set<UpdateProductClientRequest> productRequests);
}
