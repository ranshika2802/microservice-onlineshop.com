package com.rajat.admin_service.model.inventory.request;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.UUID;
import lombok.Data;

@Data
public class UpdateProductInventoryClientRequest {

    @Hidden
    private Long inventoryId;

    @NotNull(message = "Product ID must not be null")
    private UUID productId;

    @PositiveOrZero(message = "Total inventory must be positive or zero.")
    private Integer total;

    @PositiveOrZero(message = "Reserved inventory must be positive or zero.")
    private Integer reserved;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UpdateProductInventoryClientRequest that = (UpdateProductInventoryClientRequest) o;
        return productId.equals(that.productId);
    }

    @Override
    public int hashCode() {
        return productId.hashCode();
    }
}
