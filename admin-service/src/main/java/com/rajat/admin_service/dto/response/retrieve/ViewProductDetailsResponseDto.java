package com.rajat.admin_service.dto.response.retrieve;

import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class ViewProductDetailsResponseDto {
    private UUID id;
    private String name;
    private String brand;
    private ViewCategoryResponseDto category;
    private String description;
    private ViewPriceResponseDto price;
    private ViewInventoryResponseDto inventory;
    private Set<ViewProductAttributeResponseDto> attributes;
}
