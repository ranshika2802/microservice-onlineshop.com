package com.rajat.admin_service.model.inventory;

import com.rajat.admin_service.model.inventory.response.ViewProductInventoryClientResponse;
import com.rajat.admin_service.model.product.response.ViewProductClientResponse;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ApiInventoryClientResponse {
    private boolean success;
    private String message;
    private Set<ViewProductInventoryClientResponse> viewProductInventoryClientResponses;
}
