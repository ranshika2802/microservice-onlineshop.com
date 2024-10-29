package com.rajat.customer_service.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ViewInventoryResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private long total;
    private long available;
    private long reserved;
}
