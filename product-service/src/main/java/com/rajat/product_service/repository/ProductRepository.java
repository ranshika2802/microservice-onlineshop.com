package com.rajat.product_service.repository;

import com.rajat.product_service.model.Product;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    Set<Product> findAllByCategory_IdIn(@NonNull Set<Long> categoryIds);
}
