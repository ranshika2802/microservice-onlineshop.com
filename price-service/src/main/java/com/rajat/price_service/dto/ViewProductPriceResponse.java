package com.rajat.price_service.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ViewProductPriceResponse {
    private int id;
    private String currency;
    private float amount;
    private UUID productId;
}
