package com.rajat.admin_service.model.product.request;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import lombok.Data;

@Data
public class DeleteProductAttributesRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<UUID, Set<String>> productIdToAttributeNamesMap;
}
