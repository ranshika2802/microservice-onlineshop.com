package com.rajat.admin_service.model.inventory.request;

import java.util.Set;
import lombok.Data;

@Data
public class ViewProductInventoryRequest {
  private Set<String> productIds;
}
