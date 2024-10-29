package com.rajat.product_service.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ProductResponse {
    private UUID id;
    private String name;
    private String brand;
    private CategoryResponse category;
    private String description;
    private List<AttributeResponse> attributes;
}
