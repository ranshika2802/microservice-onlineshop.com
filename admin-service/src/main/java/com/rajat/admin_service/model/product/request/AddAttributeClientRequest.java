package com.rajat.admin_service.model.product.request;

import java.util.Objects;
import lombok.Data;

@Data
public class AddAttributeClientRequest {
    private String name;
    private String value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AddAttributeClientRequest that = (AddAttributeClientRequest) o;

        // Only compare the 'name' field for equality
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        // Only use 'name' for generating hashcode
        return name != null ? name.hashCode() : 0;
    }
}
