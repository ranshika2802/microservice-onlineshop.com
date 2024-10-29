package com.rajat.product_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Objects;

@Data
public class AddAttributeRequest {

    @NotBlank(message = "attribute name must not be blank")
    private String name;

    @NotBlank(message = "attribute name must not be blank")
    private String value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AddAttributeRequest that = (AddAttributeRequest) o;

        // Only compare the 'name' field for equality
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        // Only use 'name' for generating hashcode
        return name != null ? name.hashCode() : 0;
    }
}
