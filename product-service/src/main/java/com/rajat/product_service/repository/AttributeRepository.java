package com.rajat.product_service.repository;

import com.rajat.product_service.model.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;
import java.util.UUID;

public interface AttributeRepository extends JpaRepository<ProductAttribute, Long> {

    @Modifying
    @Query(value = "DELETE FROM product_schema.product_attributes WHERE product_id = :productId AND attribute_name IN :attributeNames", nativeQuery = true)
    int deleteAllByProductIdAndNameIn(@Param("productId") UUID productId, @Param("attributeNames") Set<String> attributeNames);
}
