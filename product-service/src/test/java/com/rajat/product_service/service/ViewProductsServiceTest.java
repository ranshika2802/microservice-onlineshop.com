package com.rajat.product_service.service;

import com.rajat.product_service.dto.AttributeResponse;
import com.rajat.product_service.dto.CategoryResponse;
import com.rajat.product_service.dto.ProductResponse;
import com.rajat.product_service.exception.CategoryNotFoundException;
import com.rajat.product_service.exception.ProductNotFoundException;
import com.rajat.product_service.model.Product;
import com.rajat.product_service.model.ProductAttribute;
import com.rajat.product_service.repository.AttributeRepository;
import com.rajat.product_service.repository.CategoryRepository;
import com.rajat.product_service.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ViewProductsServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private AttributeRepository attributeRepository;

    @InjectMocks
    private ViewProductsService viewProductsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void viewProductsByCategory_ShouldReturnProductResponses_WhenCategoriesExist() {
        // Arrange
        Set<String> categories = Set.of("Electronics");
        Set<Long> categoryIds = Set.of(1L);
        Product product = createProduct();

        when(categoryRepository.findAllByNameIn(categories)).thenReturn(categoryIds);
        when(productRepository.findAllByCategory_IdIn(categoryIds)).thenReturn(Set.of(product));
        when(modelMapper.map(product, ProductResponse.class)).thenReturn(createProductResponse(product));

        // Act
        Set<ProductResponse> responses = viewProductsService.viewProductsByCategory(categories);

        // Assert
        assertEquals(1, responses.size());
        ProductResponse response = responses.iterator().next();
        assertEquals(product.getId(), response.getId());
        assertEquals(product.getName(), response.getName());
        assertEquals(product.getBrand(), response.getBrand());
        verify(productRepository, times(1)).findAllByCategory_IdIn(categoryIds);
    }

    @Test
    void viewProductsByCategory_ShouldThrowCategoryNotFoundException_WhenCategoryDoesNotExist() {
        // Arrange
        Set<String> categories = Set.of("UnknownCategory");
        when(categoryRepository.findAllByNameIn(categories)).thenReturn(Collections.emptySet());

        // Act & Assert
        assertThrows(CategoryNotFoundException.class, () -> viewProductsService.viewProductsByCategory(categories));
        verify(productRepository, never()).findAllByCategory_IdIn(any());
    }

    @Test
    void viewProductsByCategory_ShouldThrowProductNotFoundException_WhenNoProductsFound() {
        // Arrange
        Set<String> categories = Set.of("Electronics");
        Set<Long> categoryIds = Set.of(1L);

        when(categoryRepository.findAllByNameIn(categories)).thenReturn(categoryIds);
        when(productRepository.findAllByCategory_IdIn(categoryIds)).thenReturn(Collections.emptySet());

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> viewProductsService.viewProductsByCategory(categories));
        verify(productRepository, times(1)).findAllByCategory_IdIn(categoryIds);
    }

    private Product createProduct() {
        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setName("Smartphone");
        product.setBrand("BrandX");

        ProductAttribute attribute = new ProductAttribute();
        attribute.setName("Color");
        attribute.setValue("Black");
        attribute.setProduct(product);

        product.setProductAttributes(Set.of(attribute));
        return product;
    }

    private ProductResponse createProductResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setBrand(product.getBrand());

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(1L);
        categoryResponse.setName("Electronics");
        response.setCategory(categoryResponse);

        AttributeResponse attributeResponse = new AttributeResponse();
        attributeResponse.setName("Color");
        attributeResponse.setValue("Black");
        response.setAttributes(List.of(attributeResponse));

        return response;
    }
}
