package com.rajat.admin_service.model.product;

import com.rajat.admin_service.dto.response.retrieve.ViewProductDetailsResponseDto;
import com.rajat.admin_service.model.product.response.ViewProductClientResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ApiProductClientResponse {
    private boolean success;
    private String message;
    private Set<ViewProductClientResponse> viewProductClientResponses;
}
