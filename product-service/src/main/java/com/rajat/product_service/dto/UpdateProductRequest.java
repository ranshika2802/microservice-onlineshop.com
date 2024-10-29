package com.rajat.product_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class UpdateProductRequest {
    @NotNull(message = "Product ID must not be null")
    private UUID id;
    private String name;
    private String brand;
    private String category;
    private String description;
    private Set<UpdateAttributesRequest> attributes;
}
