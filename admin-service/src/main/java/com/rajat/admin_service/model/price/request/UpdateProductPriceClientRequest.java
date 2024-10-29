package com.rajat.admin_service.model.price.request;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.UUID;
import lombok.Data;

//@CurrencyOrAmountRequired
@Data
public class UpdateProductPriceClientRequest {
    @Hidden
    private long id;

    @Pattern(regexp = "^[A-Z]{3}$", message = "Currency must be a valid ISO 4217 currency code (3 uppercase letters)")
    private String currency;

    @PositiveOrZero(message = "Amount must be zero or positive.")
    private Float amount;

    @NotNull(message = "Product Id cannot be null.")
    private UUID productId; // Ensure this is UUID
}
