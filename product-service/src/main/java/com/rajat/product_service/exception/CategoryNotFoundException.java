package com.rajat.product_service.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String msg) {
        super("Category not found: " + msg);
    }
}
