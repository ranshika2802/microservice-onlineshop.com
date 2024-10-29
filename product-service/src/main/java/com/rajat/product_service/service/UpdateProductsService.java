package com.rajat.product_service.service;

import com.rajat.product_service.dto.*;
import com.rajat.product_service.exception.CategoryNotFoundException;
import com.rajat.product_service.exception.ProductNotFoundException;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateProductsService implements IUpdateProductsService {

    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Set<UpdateProductResponse> updateProducts(@NonNull Set<UpdateProductRequest> updateProductRequests) {
        List<Product> products = updateProductRequests.parallelStream().map(updateProductRequest -> {
            // Fetch existing product from the database
            Product existingProduct = productRepository.findById(updateProductRequest.getId()).orElseThrow(() -> new ProductNotFoundException());

            // Update fields from request
            if (updateProductRequest.getName() != null) existingProduct.setName(updateProductRequest.getName());

            if (updateProductRequest.getBrand() != null) existingProduct.setBrand(updateProductRequest.getBrand());

            if (updateProductRequest.getDescription() != null)
                existingProduct.setDescription(updateProductRequest.getDescription());

            // Check if category needs to be updated
            if (updateProductRequest.getCategory() != null) {
                Category category = categoryRepository.findByName(updateProductRequest.getCategory()).orElseThrow(() -> new CategoryNotFoundException(updateProductRequest.getCategory()));
                existingProduct.setCategory(category);
            }

            // Set attributes for the product
            if (updateProductRequest.getAttributes() != null) {
                existingProduct.setProductAttributes(mergeProductAttributes(existingProduct.getProductAttributes(), updateProductRequest.getAttributes(), existingProduct));
            }

            return existingProduct;
        }).collect(Collectors.toUnmodifiableList());
        List<Product> productsSaved = productRepository.saveAll(products);
        log.info("Products updated with size={}", productsSaved.size());
        return mapSet(Set.copyOf(productsSaved));
    }

    // Refactor the logic into a utility method to handle attribute updates
    private Set<ProductAttribute> mergeProductAttributes(Set<ProductAttribute> existingAttributes, Set<UpdateAttributesRequest> requestedAttributes, Product product) {
        // Create a map for faster lookup of existing attributes by name
        Map<String, ProductAttribute> existingAttrMap = new HashMap<>();
        for (ProductAttribute attr : existingAttributes) {
            existingAttrMap.put(attr.getName(), attr);
        }
        // Update existing attributes or add new ones
        for (UpdateAttributesRequest updateAttributesRequest : requestedAttributes) {
            String attributeName = updateAttributesRequest.getName();
            ProductAttribute existingAttribute = existingAttrMap.get(attributeName);

            if (existingAttribute != null) {
                // Update the value of existing attribute
                existingAttribute.setValue(updateAttributesRequest.getValue());
            } else {
                // Add new attribute if not present
                ProductAttribute newAttribute = new ProductAttribute();
                newAttribute.setName(updateAttributesRequest.getName());
                newAttribute.setValue(updateAttributesRequest.getValue());
                newAttribute.setProduct(product);
//                newAttribute.setProductId(product.getId());
                existingAttributes.add(newAttribute);
            }
        }

        return existingAttributes; // Return the updated attributes
    }

    // Reusable method for mapping lists
    private Set<UpdateProductResponse> mapSet(Set<Product> savedProducts) {
        return savedProducts.stream()
                .map(element -> mapProductToResponse(element)).collect(Collectors.toUnmodifiableSet());
    }

    private UpdateProductResponse mapProductToResponse(Product product) {
        UpdateProductResponse response = modelMapper.map(product, UpdateProductResponse.class);

        // Manually map the attributes
        Set<UpdateAttributesResponse> attributeResponses = product.getProductAttributes().stream()
                .map(attr -> modelMapper.map(attr, UpdateAttributesResponse.class))
                .collect(Collectors.toSet());

        response.setAttributesResponses(attributeResponses);
        return response;
    }
}
