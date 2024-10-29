package com.rajat.price_service.dto;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.Objects;
import java.util.UUID;

@Data
public class AddProductPriceRequest {

    @Hidden
    private long id;

    @NotNull(message = "Currency must not be null")
    @Pattern(regexp = "^[A-Z]{3}$", message = "Currency must be a valid ISO 4217 currency code (3 uppercase letters)")
    private String currency;

    @Digits(integer = Integer.MAX_VALUE, fraction = 2, message = "Amount must be a valid number with up to 2 decimal places.")
    @Positive(message = "Amount must be positive")
    private Float amount;

    @NotNull(message = "Product ID must not be null")
    private UUID productId;

    @Override
    public boolean equals(Object obj) {
        AddProductPriceRequest request = (AddProductPriceRequest) obj;
        return this.productId.equals(request.productId);
        }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
}
