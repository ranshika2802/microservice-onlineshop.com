package com.rajat.admin_service.dto.request.add;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Objects;

@Data
public class AddAttributeDto {
    @NotBlank(message = "Atrribute name can not be blank.")
    private String name;
    @NotBlank(message = "Attribute value can not be blank.")
    private String value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AddAttributeDto that = (AddAttributeDto) o;

        // Only compare the 'name' field for equality
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        // Only use 'name' for generating hashcode
        return name != null ? name.hashCode() : 0;
    }
}
