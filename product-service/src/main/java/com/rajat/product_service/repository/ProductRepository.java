package com.rajat.product_service.repository;

import com.rajat.product_service.model.Product;
import lombok.NonNull;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findAllByCategoryIn(@NonNull List<String> categories);
}
