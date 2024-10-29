package com.rajat.customer_service.model;

import lombok.Data;

@Data
public class ViewInventoryClientResponse {
    private long total;
    private long available;
    private long reserved;
}
