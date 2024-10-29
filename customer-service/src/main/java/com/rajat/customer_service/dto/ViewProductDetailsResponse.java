package com.rajat.customer_service.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class ViewProductDetailsResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String brand;
    private ViewCategoryResponse category;
    private String description;
    private Set<ViewProductAttributeResponse> attributes;
    private ViewPriceResponse price;
    private ViewInventoryResponse inventory;
}
