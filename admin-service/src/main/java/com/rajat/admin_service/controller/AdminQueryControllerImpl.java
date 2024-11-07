package com.rajat.admin_service.controller;

import com.rajat.admin_service.dto.response.retrieve.ApiResponse;
import com.rajat.admin_service.service.view.ViewAdminService;
import java.util.Set;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminQueryControllerImpl implements AdminQueryController {

  private static final Logger LOGGER = LoggerFactory.getLogger(AdminQueryControllerImpl.class);

  private final ViewAdminService viewAdminService;

  @Override
  public ApiResponse viewProductsDetailsByCategory(@NonNull Set<String> categories) {
    LOGGER.info("AdminQueryControllerImpl-> viewProductsDetailsByCategory -> invocation started.");
    return viewAdminService.viewProductsDetailsByCategory(categories);
  }
}
