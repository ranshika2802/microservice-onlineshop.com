package com.rajat.product_service.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "categories", schema = "product_schema")
@Data
public class Category {
    @Id
    @Column(name = "category_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;
}
