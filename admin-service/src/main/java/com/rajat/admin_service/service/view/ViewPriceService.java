package com.rajat.admin_service.service.view;

import com.rajat.admin_service.model.price.ApiPriceClientResponse;
import java.util.Set;
import java.util.UUID;
import lombok.NonNull;

public interface ViewPriceService {
  ApiPriceClientResponse viewPriceByProductId(@NonNull final Set<UUID> productId);
}
