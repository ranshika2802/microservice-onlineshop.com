package com.rajat.admin_service.model.price;

import com.rajat.admin_service.model.price.response.ViewProductPriceClientResponse;
import com.rajat.admin_service.model.product.response.ViewProductClientResponse;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ApiPriceClientResponse {
    private boolean success;
    private String message;
    private Set<ViewProductPriceClientResponse> viewProductPriceClientResponses;
}
