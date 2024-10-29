package com.rajat.admin_service.service.update;

import com.rajat.admin_service.client.PriceClient;
import com.rajat.admin_service.model.price.request.UpdateProductPriceClientRequest;
import com.rajat.admin_service.model.price.response.UpdateProductPriceClientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UpdatePriceServiceImpl implements UpdatePriceService {

    private final PriceClient priceClient;
    @Override
    public Set<UpdateProductPriceClientResponse> updateProductPrice(Set<UpdateProductPriceClientRequest> priceRequests) {
        return priceClient.updateProductPrice(priceRequests);
    }
}
