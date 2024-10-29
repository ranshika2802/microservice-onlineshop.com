package com.rajat.price_service.repository;

import com.rajat.price_service.model.Price;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface PriceRepository extends JpaRepository<Price, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Price p SET " +
            "p.currency = COALESCE(:currency, p.currency), " +
            "p.amount = COALESCE(:amount, p.amount) " +
            "WHERE p.productId = :productId")
    void updatePrice(@Param("currency") String currency,
                     @Param("amount") Float amount,
                     @Param("productId") UUID productId);

    int deleteAllByProductIdIn(@NonNull List<String> productIds);

    List<Price> findAllByProductIdIn(@NonNull List<String> productIds);

    boolean existsByProductId(UUID productId);

    @Query("SELECT p.productId FROM Price p WHERE p.productId IN :productIds")
    List<UUID> findAllProductIdsByProductIdIn(Set<UUID> productIds);
}
