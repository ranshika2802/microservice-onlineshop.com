package com.rajat.admin_service.dto.response.retrieve;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewInventoryResponseDto {
    private long total;
    private long available;
    private long reserved;
}
