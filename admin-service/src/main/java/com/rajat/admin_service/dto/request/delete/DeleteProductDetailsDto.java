package com.rajat.admin_service.dto.request.delete;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class DeleteProductDetailsDto {
    @NotNull(message = "Provide product id to delete.")
    private Set<UUID> productIds;
}
