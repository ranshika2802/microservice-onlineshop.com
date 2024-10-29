package com.rajat.admin_service.dto.response.retrieve;

import com.rajat.admin_service.model.product.response.ViewProductClientResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private boolean success;
    private Set<String> message;
    private Set<ViewProductDetailsResponseDto> dataSet;
}
