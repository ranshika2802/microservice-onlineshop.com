package com.rajat.admin_service.dto.request.add;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddProductDetailsDto {
  @NotNull(message = "Product details can not be null.")
  private AddProductDto product;

  private AddPriceDto price;
  private AddInventoryDto inventory;
}
