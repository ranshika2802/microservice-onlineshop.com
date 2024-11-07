package com.rajat.admin_service.dto.response.retrieve;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewPriceResponseDto {
    private String currency;
    private float amount;
}
