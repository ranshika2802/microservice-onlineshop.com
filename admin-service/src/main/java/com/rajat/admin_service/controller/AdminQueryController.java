package com.rajat.admin_service.controller;

import com.rajat.admin_service.dto.response.retrieve.ApiResponse;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/v1/admin")
public interface AdminQueryController {
  @GetMapping("/products")
  ApiResponse viewProductsDetailsByCategory(
      @RequestParam @NonNull @Size(min = 1, message = "At least one category must be specified")
          final Set<String> categories);
}
