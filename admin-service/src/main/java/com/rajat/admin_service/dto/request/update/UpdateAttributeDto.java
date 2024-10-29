package com.rajat.admin_service.dto.request.update;

import com.rajat.admin_service.dto.request.add.AddAttributeDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Objects;

@Data
public class UpdateAttributeDto {
    @NotBlank(message = "Update Attribute name can not be blank.")
    private String name;
    @NotBlank(message = "Update Attribute Value can not be blank.")
    private String value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UpdateAttributeDto that = (UpdateAttributeDto) o;

        // Only compare the 'name' field for equality
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        // Only use 'name' for generating hashcode
        return name != null ? name.hashCode() : 0;
    }
}
