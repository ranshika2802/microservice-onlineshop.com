package com.rajat.customer_service.model;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiClientResponse {
    private boolean success;
    private Set<String> message;
    private Set<ViewProductDetailsClientResponse> dataSet;
}
