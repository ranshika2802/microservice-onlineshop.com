package com.rajat.admin_service.model.inventory.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.UUID;
import lombok.Data;

@Data
public class ViewProductInventoryClientResponse {
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

        ViewProductInventoryClientResponse that = (ViewProductInventoryClientResponse) o;
        return productId.equals(that.productId);
    }

    @Override
    public int hashCode() {
        return productId.hashCode();
    }
}
