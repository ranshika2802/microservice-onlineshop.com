package com.rajat.product_service.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Data
public class DeleteProductRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<UUID, Set<String>> productIdToAttributeNamesMap;
}
