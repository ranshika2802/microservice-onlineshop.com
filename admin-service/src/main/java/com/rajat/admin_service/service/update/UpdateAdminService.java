package com.rajat.admin_service.service.update;

import com.rajat.admin_service.dto.request.update.UpdateProductDetailsDto;
import com.rajat.admin_service.dto.response.update.UpdateProductDetailsResponseDto;
import lombok.NonNull;

import java.util.Set;

public interface UpdateAdminService {
    Set<UpdateProductDetailsResponseDto> updateProductsDetails(@NonNull final Set<UpdateProductDetailsDto> updateProductRequests);
}
