package com.rajat.inventory_service.dto;

import java.util.Set;
import lombok.Data;

@Data
public class ViewProductInventoryRequest {
  private Set<String> productIds;
}
