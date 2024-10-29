package com.rajat.admin_service.service.add;

import com.rajat.admin_service.dto.request.add.AddProductDetailsDto;
import com.rajat.admin_service.dto.response.add.AddProductDetailsResponseDto;
import lombok.NonNull;

import java.util.Set;

public interface AddAdminService {

    Set<AddProductDetailsResponseDto> addProductsDetails(@NonNull Set<AddProductDetailsDto> productDetails);
}
