package com.rajat.product_service.dto;

import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class UpdateProductResponse {
    private UUID id;
    private String name;
    private String brand;
    private String category;
    private String description;
    private Set<UpdateAttributesResponse> attributesResponses;
}
