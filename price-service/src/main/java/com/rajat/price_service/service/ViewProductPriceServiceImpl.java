package com.rajat.price_service.service;

import com.rajat.price_service.dto.ViewProductPriceResponse;
import com.rajat.price_service.model.Price;
import com.rajat.price_service.repository.PriceRepository;
import com.rajat.price_service.utils.PriceUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ViewProductPriceServiceImpl implements ViewProductPriceService {

    private final ModelMapper modelMapper;
    private final PriceRepository priceRepository;

    @Override
    @Transactional
    public List<ViewProductPriceResponse> viewPriceByProductId(@NonNull List<String> productIds) {
        List<Price> priceDetails = priceRepository.findAllByProductIdIn(productIds);
        return PriceUtils.mapList(priceDetails, ViewProductPriceResponse.class, modelMapper);
    }
}
