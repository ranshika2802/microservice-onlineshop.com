package com.rajat.price_service.service;

import com.rajat.price_service.dto.UpdateProductPriceRequest;
import com.rajat.price_service.dto.UpdateProductPriceResponse;
import com.rajat.price_service.repository.PriceRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateProductPriceServiceImpl implements UpdateProductPriceService {
    
    private final PriceRepository priceRepository;
    private final ModelMapper modelMapper;
    
    @Override
    @Transactional
    public List<UpdateProductPriceResponse> updateProductPrice(@NonNull List<UpdateProductPriceRequest> updateProductPriceRequests) {
        for (UpdateProductPriceRequest request : updateProductPriceRequests) {
            priceRepository.updatePrice(request.getCurrency(),
                    request.getAmount(),
                    request.getProductId());
        }
        return List.of();
    }
}
