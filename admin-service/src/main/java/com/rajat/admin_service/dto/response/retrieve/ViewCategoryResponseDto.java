package com.rajat.admin_service.dto.response.retrieve;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ViewCategoryResponseDto {
  private Long id;
  private String name;
  private String description;
}
