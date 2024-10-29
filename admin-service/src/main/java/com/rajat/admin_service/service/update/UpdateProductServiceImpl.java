package com.rajat.admin_service.service.update;

import com.rajat.admin_service.client.ProductClient;
import com.rajat.admin_service.model.product.request.UpdateProductClientRequest;
import com.rajat.admin_service.model.product.response.UpdateProductClientResponse;
import com.rajat.admin_service.util.AdminUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UpdateProductServiceImpl implements UpdateProductService {

    private final ProductClient productClient;
    private final AdminUtils adminUtils;

    @Override
    public Set<UpdateProductClientResponse> updateProducts(Set<UpdateProductClientRequest> productRequests) {
    return productClient.updateProducts(productRequests);
    }
}
