package com.rajat.product_service.service;

import com.rajat.product_service.dto.ProductRequest;
import com.rajat.product_service.dto.ProductResponse;
import com.rajat.product_service.dto.UpdateProductRequest;
import com.rajat.product_service.model.Product;
import com.rajat.product_service.repository.ProductRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultProductsService implements ProductsService {

    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;

    @Override
    public List<ProductResponse> addProducts(@NonNull final List<ProductRequest> productRequests) {
        List<Product> products = mapList(productRequests, Product.class);
        List<Product> productsSaved = productRepository.saveAll(products);
        log.info("Products saved with size={}", productsSaved.size());
        return mapList(productsSaved, ProductResponse.class);
    }

    @Override
    public List<ProductResponse> viewProductsByCategory(@NonNull final List<String> categories) {
        List<Product> products = productRepository.findAllByCategoryIn(categories);
        if (products.isEmpty()) {
            log.warn("No products found for categories: {}", categories);
            return List.of();
        }
        return mapList(products, ProductResponse.class);
    }

    @Override
    public List<ProductResponse> updateProducts(@NonNull final List<UpdateProductRequest> updateProductRequests) {
        List<Product> products = mapList(updateProductRequests, Product.class);
        List<Product> productsUpdated = productRepository.saveAll(products);
        log.info("Updated {} product(s)", productsUpdated.size());
        return mapList(productsUpdated, ProductResponse.class);
    }

    @Override
    public ResponseEntity<Void> deleteProduct(@NonNull final List<String> productIds) {
        productRepository.deleteAllById(productIds);
        return ResponseEntity.ok().build();
    }

    // Reusable method for mapping lists
    private <L, C> List<C> mapList(List<L> sourceList, Class<C> targetClass) {
        return sourceList.parallelStream()  // Use parallelStream if performance is needed
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toUnmodifiableList());
    }
}

