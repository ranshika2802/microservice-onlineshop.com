package com.rajat.customer_service.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private boolean success;
    private Set<String> message;
    private Set<ViewProductDetailsResponse> viewProductDetailsResponses;
}
