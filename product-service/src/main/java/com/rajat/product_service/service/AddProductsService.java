package com.rajat.product_service.service;

import com.rajat.product_service.dto.AddProductRequest;
import com.rajat.product_service.dto.AddProductResponse;
import com.rajat.product_service.exception.CategoryNotFoundException;
import com.rajat.product_service.model.Category;
import com.rajat.product_service.model.Product;
import com.rajat.product_service.model.ProductAttribute;
import com.rajat.product_service.repository.CategoryRepository;
import com.rajat.product_service.repository.ProductRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddProductsService implements IAddProductsService {
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Set<AddProductResponse> addProducts(@NonNull final Set<AddProductRequest> addProductRequests) {
        List<Product> products = addProductRequests.stream().map(productRequest -> {
            Category category = categoryRepository.findByName(productRequest.getCategory()).orElseThrow(() -> new CategoryNotFoundException(productRequest.getCategory()));
            Product product = modelMapper.map(productRequest, Product.class);
            product.setCategory(category);
            // Set attributes for the product
            Set<ProductAttribute> attributes = productRequest.getAttributes().stream().map(attr -> {
                ProductAttribute productAttribute = new ProductAttribute();
                productAttribute.setName(attr.getName());
                productAttribute.setValue(attr.getValue());
                productAttribute.setProduct(product); // Set the associated product
                return productAttribute;
            }).collect(Collectors.toUnmodifiableSet());

            product.setProductAttributes(attributes); // Set attributes for the product
            return product;
        }).collect(Collectors.toUnmodifiableList());
        List<Product> productsSaved = productRepository.saveAll(products);
        log.info("Products saved with size={}", productsSaved.size());
        return mapSet(Set.copyOf(productsSaved), AddProductResponse.class);
    }

    // Reusable method for mapping set
    private <L, C> Set<C> mapSet(Set<L> sourceList, Class<C> targetClass) {
        return sourceList.parallelStream()  // Use parallelStream if performance is needed
                .map(element -> modelMapper.map(element, targetClass)).collect(Collectors.toUnmodifiableSet());
    }
}
