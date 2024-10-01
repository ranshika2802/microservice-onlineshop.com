package com.rajat.product_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "deleted-products")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DeletedProduct {
   private Product product;
}
