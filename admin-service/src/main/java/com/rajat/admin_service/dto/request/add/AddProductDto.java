package com.rajat.admin_service.dto.request.add;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class AddProductDto {
    @NotBlank(message = "Product name can not be blank.")
    private String name;
    @NotBlank(message = "Product brand can not be blank.")
    private String brand;
    @NotBlank(message = "Product category can not be null.")
    private String category;
    private String description;
    @NotNull(message = "Product attribute(s) can not be null.")
    private Set<AddAttributeDto> attributes;
}
