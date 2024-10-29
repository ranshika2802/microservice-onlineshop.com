package com.rajat.admin_service.controller;

import com.rajat.admin_service.dto.response.retrieve.ApiResponse;
import com.rajat.admin_service.dto.response.retrieve.ViewProductDetailsResponseDto;
import com.rajat.admin_service.service.view.ViewAdminService;
import java.util.Set;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminQueryControllerImpl implements AdminQueryController {

  private final ViewAdminService viewAdminService;

  @Override
  public ApiResponse viewProductsDetailsByCategory(
      @NonNull Set<String> categories) {
    return viewAdminService.viewProductsDetailsByCategory(categories);
  }
}
