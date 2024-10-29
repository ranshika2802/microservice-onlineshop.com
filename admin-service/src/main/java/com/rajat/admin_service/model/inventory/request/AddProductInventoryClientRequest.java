package com.rajat.admin_service.model.inventory.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.UUID;
import lombok.Data;

@Data
public class AddProductInventoryClientRequest {
  private long inventoryId;

  @NotNull(message = "Product ID must not be null")
  private UUID productId;

  @PositiveOrZero(message = "Total inventory must be greater than zero.")
  private Integer total;

  @PositiveOrZero(message = "reserved inventory must be greater than zero.")
  private Integer reserved;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    AddProductInventoryClientRequest that = (AddProductInventoryClientRequest) o;
    return productId.equals(that.productId);
  }

  @Override
  public int hashCode() {
    return productId.hashCode();
  }
}
