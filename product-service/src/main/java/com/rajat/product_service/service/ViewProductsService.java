package com.rajat.product_service.service;

import com.rajat.product_service.dto.ProductResponse;
import com.rajat.product_service.exception.CategoryNotFoundException;
import com.rajat.product_service.exception.ProductNotFoundException;
import com.rajat.product_service.model.Product;
import com.rajat.product_service.repository.AttributeRepository;
import com.rajat.product_service.repository.CategoryRepository;
import com.rajat.product_service.repository.ProductRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ViewProductsService implements IViewProductsService {

    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final AttributeRepository attributeRepository;

    @Override
    @Transactional
    public Set<ProductResponse> viewProductsByCategory(@NonNull final Set<String> categories) {
        Set<Long> categoryIds = categoryRepository.findAllByNameIn(categories);
        if (categoryIds.isEmpty()) {
            throw new CategoryNotFoundException(categories.toString());
        }
        Set<Product> products = productRepository.findAllByCategory_IdIn(categoryIds);
        if (products.isEmpty()) {
            throw new ProductNotFoundException();
        }
        products.forEach(product -> product.getProductAttributes().size());
        return mapSet(products, ProductResponse.class);
    }

    // Reusable method for mapping lists
    private <L, C> Set<C> mapSet(Set<L> sourceList, Class<C> targetClass) {
        return sourceList.parallelStream()  // Use parallelStream if performance is needed
                .map(element -> modelMapper.map(element, targetClass)).collect(Collectors.toUnmodifiableSet());
    }
}
