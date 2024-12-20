package com.rajat.admin_service.model.price.response;

import java.util.UUID;
import lombok.Data;

@Data
public class ViewProductPriceClientResponse {
    private int id;
    private String currency;
    private float amount;
    private UUID productId;
}
