package com.rajat.customer_service.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ViewCategoryResponse implements Serializable {
  private static final long serialVersionUID = 1L;

  private Long id;
  private String name;
  private String description;
}
