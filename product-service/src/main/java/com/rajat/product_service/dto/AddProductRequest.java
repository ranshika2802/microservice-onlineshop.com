package com.rajat.product_service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class AddProductRequest {
    private UUID id;
    @NotBlank(message = "Product name must not be blank")
    private String name;

    @NotBlank(message = "Brand must not be blank")
    private String brand;

    @NotBlank(message = "Category must not be blank")
    private String category;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @NotEmpty(message = "Attributes must not be empty")
    private Set<@Valid AddAttributeRequest> attributes;
}
