package com.rajat.inventory_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Data
public class DeleteProductInventoryRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    @NotNull
    private Set<UUID> productIds;
}
