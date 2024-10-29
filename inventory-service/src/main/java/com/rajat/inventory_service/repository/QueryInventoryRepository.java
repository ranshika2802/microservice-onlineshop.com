package com.rajat.inventory_service.repository;

import com.rajat.inventory_service.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface QueryInventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findAllByProductIdIn(Set<UUID> productIds);
}
