package com.rajat.inventory_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.UUID;

@Data
public class ViewProductInventoryResponse {
    private long inventoryId;
    private UUID productId;
    private Integer total;
    private Integer reserved;

    // The available field is dynamically calculated from total - reserved
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer getAvailable() {
        if (total != null && reserved != null) {
            return total - reserved;
        }
        return null;  // Return null if either is missing, or handle appropriately
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ViewProductInventoryResponse that = (ViewProductInventoryResponse) o;
        return productId.equals(that.productId);
    }

    @Override
    public int hashCode() {
        return productId.hashCode();
    }
}
