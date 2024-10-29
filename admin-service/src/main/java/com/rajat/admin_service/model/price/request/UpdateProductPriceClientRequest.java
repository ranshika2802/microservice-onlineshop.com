package com.rajat.admin_service.model.price.request;

import java.util.UUID;
import lombok.Data;

@Data
public class UpdateProductPriceClientRequest {
  private long id;
  private String currency;
  private Float amount;
  private UUID productId; // Ensure this is UUID
}
