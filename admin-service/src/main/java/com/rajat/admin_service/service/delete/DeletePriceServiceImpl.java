package com.rajat.admin_service.service.delete;

import com.rajat.admin_service.client.PriceClient;
import com.rajat.admin_service.dto.request.delete.DeleteProductDetailsDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeletePriceServiceImpl implements DeletePriceService {

    private final PriceClient priceClient;

    @Override
    public void deletePrice(@NonNull DeleteProductDetailsDto productIds) {
        priceClient.deleteProductPrice(productIds.getProductIds());
    }
}
