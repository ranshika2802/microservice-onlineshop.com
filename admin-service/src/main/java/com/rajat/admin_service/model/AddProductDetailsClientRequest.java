package com.rajat.admin_service.model;

import com.rajat.admin_service.model.inventory.request.AddProductInventoryClientRequest;
import com.rajat.admin_service.model.price.request.AddProductPriceClientRequest;
import com.rajat.admin_service.model.product.request.AddProductClientRequest;
import lombok.Data;

@Data
public class AddProductDetailsClientRequest {
   private AddProductClientRequest addProductClientRequest;
   private AddProductPriceClientRequest addProductPriceClientRequest;
   private AddProductInventoryClientRequest addProductInventoryClientRequest;
}
