package com.rajat.customer_service.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class ViewProductDetailsClientResponse implements Serializable {
    private String name;
    private String brand;
    private CategoryClientResponse category;
    private String description;
    private Set<ViewProductAttributeClientResponse> attributes;
    private ViewPriceClientResponse price;
    private ViewInventoryClientResponse inventory;
}
