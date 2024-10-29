package com.rajat.inventory_service.dto;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class AddProductInventoryRequest {
    @Hidden
    private long inventoryId;
    @NotNull(message = "Product ID must not be null")
    private UUID productId;
    @PositiveOrZero(message = "Total inventory must be greater than zero.")
    private Integer total;
    @PositiveOrZero(message = "reserved inventory must be greater than zero.")
    private Integer reserved;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AddProductInventoryRequest that = (AddProductInventoryRequest) o;
        return productId.equals(that.productId);
    }

    @Override
    public int hashCode() {
        return productId.hashCode();
    }
}
