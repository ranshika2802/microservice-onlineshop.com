package com.rajat.admin_service.service.add;

import com.rajat.admin_service.model.product.request.AddProductClientRequest;
import com.rajat.admin_service.model.product.response.AddProductClientResponse;

import java.util.Set;

public interface AddProductService {
    Set<AddProductClientResponse> addProducts(Set<AddProductClientRequest> addProductDtos);
}
