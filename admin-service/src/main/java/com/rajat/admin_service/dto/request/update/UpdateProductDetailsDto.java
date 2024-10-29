package com.rajat.admin_service.dto.request.update;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateProductDetailsDto {
    @NotNull(message = "Product id required.")
    private UpdateProductDto product;
    private UpdatePriceDto price;
    private UpdateInventoryDto inventory;
}
