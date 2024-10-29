package com.rajat.admin_service.dto.request.update;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class UpdateInventoryDto {
    @PositiveOrZero(message = "Total inventory must be greater than zero.")
    private Integer total;

    @PositiveOrZero(message = "reserved inventory must be greater than zero.")
    private Integer reserved;
}
