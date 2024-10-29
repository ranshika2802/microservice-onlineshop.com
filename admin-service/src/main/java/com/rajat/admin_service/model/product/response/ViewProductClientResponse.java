package com.rajat.admin_service.model.product.response;

import java.util.Set;
import java.util.UUID;
import lombok.Data;

@Data
public class ViewProductClientResponse {
    private UUID id;
    private String name;
    private String brand;
    private ViewCategoryClientResponse category;
    private String description;
    private Set<ViewAttributeClientResponse> attributes;
}
