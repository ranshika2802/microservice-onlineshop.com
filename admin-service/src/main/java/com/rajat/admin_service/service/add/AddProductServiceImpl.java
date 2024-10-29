package com.rajat.admin_service.service.add;

import com.rajat.admin_service.client.ProductClient;
import com.rajat.admin_service.model.product.request.AddProductClientRequest;
import com.rajat.admin_service.model.product.response.AddProductClientResponse;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddProductServiceImpl implements AddProductService {

  private final ProductClient productClient;

  @Override
  public Set<AddProductClientResponse> addProducts(
      Set<AddProductClientRequest> addProductClientRequests) {
    return productClient.addProducts(addProductClientRequests);
  }
}
