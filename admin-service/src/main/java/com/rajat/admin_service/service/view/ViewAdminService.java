package com.rajat.admin_service.service.view;

import com.rajat.admin_service.dto.response.retrieve.ApiResponse;
import lombok.NonNull;

import java.util.Set;

public interface ViewAdminService {
  ApiResponse viewProductsDetailsByCategory(
      @NonNull final Set<String> categories);
}
