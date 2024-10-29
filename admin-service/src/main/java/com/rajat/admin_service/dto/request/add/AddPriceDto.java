package com.rajat.admin_service.dto.request.add;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
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
  @Positive(message = "Amount must be positive")
  private Float amount;
}
