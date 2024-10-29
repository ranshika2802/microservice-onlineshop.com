package com.rajat.admin_service.model.product.request;

import java.util.Set;
import java.util.UUID;
import lombok.Data;

@Data
public class AddProductClientRequest {
//    <TO DO> Remove once logic implemented for UUID
//    during Adding Product
    private UUID id;
    private String name;
    private String brand;
    private String category;
    private String description;
    private Set<AddAttributeClientRequest> attributes;
}
