package com.rajat.admin_service.service.delete;

import com.rajat.admin_service.client.ProductClient;
import com.rajat.admin_service.dto.request.delete.DeleteProductDetailsDto;
import com.rajat.admin_service.model.product.request.DeleteProductAttributesRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteProductServiceImpl implements DeleteProductService {

  private final ProductClient productClient;

  @Override
  public void deleteProduct(@NonNull DeleteProductDetailsDto deleteProductDetailsDto) {
    productClient.deleteProducts(deleteProductDetailsDto.getProductIds());
  }

  @Override
  public void deleteProductAttributes(
      DeleteProductAttributesRequest deleteProductAttributesRequest) {
    productClient.deleteProductAttributes(deleteProductAttributesRequest);
  }
}
