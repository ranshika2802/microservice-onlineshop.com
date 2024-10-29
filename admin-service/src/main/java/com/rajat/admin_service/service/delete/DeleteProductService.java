package com.rajat.admin_service.service.delete;

import com.rajat.admin_service.dto.request.delete.DeleteProductDetailsDto;
import com.rajat.admin_service.model.product.request.DeleteProductAttributesRequest;
import lombok.NonNull;

public interface DeleteProductService {
  void deleteProduct(final @NonNull DeleteProductDetailsDto productIds);

  void deleteProductAttributes(DeleteProductAttributesRequest deleteProductAttributesRequest);
}
