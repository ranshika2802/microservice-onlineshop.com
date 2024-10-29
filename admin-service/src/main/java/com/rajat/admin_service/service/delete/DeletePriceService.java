package com.rajat.admin_service.service.delete;

import com.rajat.admin_service.dto.request.delete.DeleteProductDetailsDto;
import lombok.NonNull;

public interface DeletePriceService {
    void deletePrice(final @NonNull DeleteProductDetailsDto productIds);
}
