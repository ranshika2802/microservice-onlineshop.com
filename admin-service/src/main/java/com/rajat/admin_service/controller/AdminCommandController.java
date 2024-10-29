package com.rajat.admin_service.controller;

import com.rajat.admin_service.dto.request.add.AddProductDetailsDto;
import com.rajat.admin_service.dto.request.delete.DeleteProductAttributesDto;
import com.rajat.admin_service.dto.request.delete.DeleteProductDetailsDto;
import com.rajat.admin_service.dto.request.update.UpdateProductDetailsDto;
import com.rajat.admin_service.dto.response.add.AddProductDetailsResponseDto;
import com.rajat.admin_service.dto.response.delete.DeleteProductDetailsResponseDto;
import com.rajat.admin_service.dto.response.update.UpdateProductDetailsResponseDto;
import java.util.Set;

import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/admin")
public interface AdminCommandController {
  @PostMapping
  Set<AddProductDetailsResponseDto> addProductsDetails(
      @Valid @RequestBody @NonNull final Set<AddProductDetailsDto> productDetails);

  @PutMapping
  Set<UpdateProductDetailsResponseDto> updateProductsDetails(
      @Valid @RequestBody @NonNull final Set<UpdateProductDetailsDto> updateProductRequests);

  @DeleteMapping("/ids")
  DeleteProductDetailsResponseDto deleteProductsDetails(
      @Valid @RequestBody @NonNull final DeleteProductDetailsDto deleteProductDetailsDto);

  @DeleteMapping("/attributes")
  DeleteProductDetailsResponseDto deleteProductAttributes(
      @Valid @RequestBody @NonNull final DeleteProductAttributesDto deleteProductAttributesDto);
}
