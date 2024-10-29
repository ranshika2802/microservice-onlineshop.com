package com.rajat.customer_service.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ViewProductAttributeResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String value;
}
