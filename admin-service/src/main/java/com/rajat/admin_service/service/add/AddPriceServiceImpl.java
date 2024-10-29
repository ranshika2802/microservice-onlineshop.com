package com.rajat.admin_service.service.add;

import com.rajat.admin_service.client.PriceClient;
import com.rajat.admin_service.model.price.request.AddProductPriceClientRequest;
import com.rajat.admin_service.model.price.response.AddProductPriceClientResponse;
import com.rajat.admin_service.util.AdminUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AddPriceServiceImpl implements AddPriceService {

    private final PriceClient priceClient;
    private final AdminUtils adminUtils;

    @Override
    public Set<AddProductPriceClientResponse> addProductPrice(Set<AddProductPriceClientRequest> addProductPriceClientRequests) {
        return priceClient.addProductPrice(addProductPriceClientRequests);
    }
}
