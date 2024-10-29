package com.rajat.price_service.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CurrencyOrAmountValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrencyOrAmountRequired {
    String message() default "Either currency or amount must be provided.";
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default {};
}
