package com.rajat.admin_service.dto.request.add;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AddPriceDto {
  @NotNull(message = "Currency can not be null")
  @Pattern(
      regexp = "^[A-Z]{3}$",
      message = "Currency must be a valid ISO 4217 currency code (3 uppercase letters)")
  private String currency;

  @Digits(
      integer = Integer.MAX_VALUE,
      fraction = 2,
      message = "Amount must be a valid number with up to 2 decimal places.")
  @PositiveOrZero(message = "Admin Reject ->Amount must not be negative.")
  private Float amount;
}
