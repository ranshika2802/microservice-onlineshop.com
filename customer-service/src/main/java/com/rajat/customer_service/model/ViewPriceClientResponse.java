package com.rajat.customer_service.model;

import lombok.Data;

@Data
public class ViewPriceClientResponse {
    private String currency;
    private float amount;
}
