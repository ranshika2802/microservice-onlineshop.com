package com.rajat.admin_service.dto.request.delete;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Data
public class DeleteProductAttributesDto {
    @NotNull(message = "Provide attribute details to delete.")
    private Map<UUID, Set<String>> productIdToAttributeNamesMap;
}
