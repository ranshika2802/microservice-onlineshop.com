package com.rajat.admin_service.dto.response.retrieve;

import lombok.Data;

import java.util.Set;

@Data
public class ViewProductResponseDto {
    private String name;
    private String brand;
    private ViewCategoryResponseDto category;
    private String description;
    private Set<ViewProductAttributeResponseDto> attributes;
}
