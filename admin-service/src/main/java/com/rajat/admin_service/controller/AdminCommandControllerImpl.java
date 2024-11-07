package com.rajat.admin_service.controller;

import com.rajat.admin_service.dto.request.add.AddProductDetailsDto;
import com.rajat.admin_service.dto.request.delete.DeleteProductAttributesDto;
import com.rajat.admin_service.dto.request.delete.DeleteProductDetailsDto;
import com.rajat.admin_service.dto.request.update.UpdateProductDetailsDto;
import com.rajat.admin_service.dto.response.add.AddProductDetailsResponseDto;
import com.rajat.admin_service.dto.response.delete.DeleteProductDetailsResponseDto;
import com.rajat.admin_service.dto.response.update.UpdateProductDetailsResponseDto;
import com.rajat.admin_service.service.add.AddAdminService;
import com.rajat.admin_service.service.delete.DeleteAdminService;
import com.rajat.admin_service.service.update.UpdateAdminService;

import java.util.Set;

import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class AdminCommandControllerImpl implements AdminCommandController {

  private final AddAdminService addAdminService;
  private final UpdateAdminService updateAdminService;
  private final DeleteAdminService deleteAdminService;

  @Override
  public Set<AddProductDetailsResponseDto> addProductsDetails(
      @Valid @NonNull Set<AddProductDetailsDto> productDetails) {
    return addAdminService.addProductsDetails(productDetails);
  }

  @Override
  public Set<UpdateProductDetailsResponseDto> updateProductsDetails(
      @Valid @NonNull Set<UpdateProductDetailsDto> updateProductRequests) {
    return updateAdminService.updateProductsDetails(updateProductRequests);
  }

  @Override
  public DeleteProductDetailsResponseDto deleteProductsDetails(
      @NonNull final DeleteProductDetailsDto deleteProductDetailsDto) {
    return deleteAdminService.deleteProductDetails(deleteProductDetailsDto);
  }

  @Override
  public DeleteProductDetailsResponseDto deleteProductAttributes(
      @NonNull final DeleteProductAttributesDto deleteProductAttributesDto) {
    return deleteAdminService.deleteProductAttributes(deleteProductAttributesDto);
  }
}
