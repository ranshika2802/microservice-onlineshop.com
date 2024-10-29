package com.rajat.admin_service.model.product.request;

import java.util.Set;
import java.util.UUID;
import lombok.Data;

@Data
public class UpdateProductClientRequest {
    private UUID id;
    private String name;
    private String brand;
    private String category;
    private String description;
    private Set<UpdateAttributeClientRequest> attributes;
}
