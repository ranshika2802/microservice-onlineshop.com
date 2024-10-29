package com.rajat.inventory_service.repository;

import com.rajat.inventory_service.model.Inventory;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface CommandInventoryRepository extends JpaRepository<Inventory, Long> {

    @Query("SELECT p.productId FROM Inventory p WHERE p.productId IN :productIds")
    List<UUID> findAllProductIdByProductIdIn(Set<UUID> productIds);

    @Modifying
    @Transactional
    @Query("UPDATE Inventory p SET " +
            "p.total = COALESCE(:total, p.total), " +
            "p.reserved = COALESCE(:reserved, p.reserved) " +
            "WHERE p.productId = :productId")
    int updateInventoryBYProductId(@Param("total") Integer total,
                                   @Param("reserved") Integer reserved,
                                   @Param("productId") UUID productId);

    int deleteAllByProductIdIn(@NotNull Set<UUID> productIds);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO inventory_schema.inventory (total, reserved, product_id) " +
            "VALUES (:total, :reserved, :productId) ON CONFLICT (product_id) DO NOTHING", nativeQuery = true)
    int insertInventory(@Param("total") Integer total,
                        @Param("reserved") Integer reserved,
                        @Param("productId") UUID productId);
}
