package com.rajat.admin_service.service.delete;

import com.rajat.admin_service.dto.request.delete.DeleteProductAttributesDto;
import com.rajat.admin_service.dto.request.delete.DeleteProductDetailsDto;
import com.rajat.admin_service.dto.response.delete.DeleteProductDetailsResponseDto;
import lombok.NonNull;

public interface DeleteAdminService {
  DeleteProductDetailsResponseDto deleteProductDetails(
      final @NonNull DeleteProductDetailsDto productIds);

  DeleteProductDetailsResponseDto deleteProductAttributes(
      final @NonNull DeleteProductAttributesDto deleteProductAttributesRequest);
}
