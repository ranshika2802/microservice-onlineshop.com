package com.rajat.admin_service.model.inventory.request;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class DeleteProductInventoryRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private Set<UUID> productIds;
}
