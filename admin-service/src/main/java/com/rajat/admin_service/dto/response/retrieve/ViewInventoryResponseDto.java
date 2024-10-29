package com.rajat.admin_service.dto.response.retrieve;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ViewInventoryResponseDto {
    private long total;
    private long available;
    private long reserved;
}
