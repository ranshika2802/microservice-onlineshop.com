package com.rajat.admin_service.service.view;

import com.rajat.admin_service.model.product.ApiProductClientResponse;
import com.rajat.admin_service.model.product.response.ViewProductClientResponse;
import java.util.Set;
import lombok.NonNull;

public interface ViewProductService {
  ApiProductClientResponse viewProductsByCategory(@NonNull final Set<String> categories);
}
