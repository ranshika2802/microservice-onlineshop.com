package com.rajat.product_service.repository;

import com.rajat.product_service.model.Category;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(@NonNull String name);

    @Query("SELECT c.id FROM Category c WHERE c.name IN :names")
    Set<Long> findAllByNameIn(@Param("names") Set<String> names);
}
