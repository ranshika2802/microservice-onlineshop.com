package com.rajat.price_service.validation;

import com.rajat.price_service.dto.UpdateProductPriceRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CurrencyOrAmountValidator implements ConstraintValidator<CurrencyOrAmountRequired, UpdateProductPriceRequest> {
    @Override
    public boolean isValid(UpdateProductPriceRequest request, ConstraintValidatorContext context) {
        return request.getAmount() > 0 || request.getCurrency() != null;
    }
}
