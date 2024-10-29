package com.rajat.admin_service.model.product.response;

import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class AddProductClientResponse {
    private UUID id;
    private String name;
    private String brand;
    private String category;
    private String description;
    private List<AddAttributeClientResponse> attributes;
}
