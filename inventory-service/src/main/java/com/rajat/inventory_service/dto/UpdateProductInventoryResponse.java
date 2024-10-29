package com.rajat.inventory_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateProductInventoryResponse {
    private String status;
}
