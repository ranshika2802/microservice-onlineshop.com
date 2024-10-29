package com.rajat.inventory_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "inventory", schema = "inventory_schema")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Long inventoryId;

    @Column(name = "total", nullable = false)
    private Integer total;

    @Column(name = "reserved", nullable = false)
    private Integer reserved;

    @Column(name = "product_id", nullable = false)
    private UUID productId;
}
