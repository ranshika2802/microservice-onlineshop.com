package com.rajat.product_service.dto;

import lombok.Data;

import java.util.Objects;

@Data
public class UpdateAttributesRequest {
    private String name;
    private String value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UpdateAttributesRequest that = (UpdateAttributesRequest) o;

        // Only compare the 'name' field for equality
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        // Only use 'name' for generating hashcode
        return name != null ? name.hashCode() : 0;
    }
}
