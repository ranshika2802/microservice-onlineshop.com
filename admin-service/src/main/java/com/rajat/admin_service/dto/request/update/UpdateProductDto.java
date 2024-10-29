package com.rajat.admin_service.dto.request.update;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class UpdateProductDto {
    @NotNull(message = "Product id can not be null.")
    private UUID productId;
    private String name;
    private String brand;
    private String category;
    private String description;
    private Set<UpdateAttributeDto> attributes;
}
