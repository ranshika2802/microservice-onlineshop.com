package com.rajat.product_service.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class AddProductResponse {
    private UUID id;
    private String name;
    private String brand;
    private String category;
    private String description;
    private List<AddAttributeResponse> attributes;
}
