package com.rajat.admin_service.model.inventory.request;

import java.util.UUID;
import lombok.Data;

@Data
public class UpdateProductInventoryClientRequest {

  private Long inventoryId;
  private UUID productId;
  private Integer total;
  private Integer reserved;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    UpdateProductInventoryClientRequest that = (UpdateProductInventoryClientRequest) o;
    return productId.equals(that.productId);
  }

  @Override
  public int hashCode() {
    return productId.hashCode();
  }
}
