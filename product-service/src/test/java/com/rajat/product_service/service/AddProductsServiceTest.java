package com.rajat.product_service.service;

import com.rajat.product_service.dto.AddAttributeRequest;
import com.rajat.product_service.dto.AddProductRequest;
import com.rajat.product_service.dto.AddProductResponse;
import com.rajat.product_service.exception.CategoryNotFoundException;
import com.rajat.product_service.model.Category;
import com.rajat.product_service.model.Product;
import com.rajat.product_service.model.ProductAttribute;
import com.rajat.product_service.repository.CategoryRepository;
import com.rajat.product_service.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AddProductsServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AddProductsService addProductsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addProducts_ShouldReturnSavedProductResponses_WhenCategoryExists() {
        // Arrange
        AddProductRequest request = createAddProductRequest("Electronics");
        Category category = createCategory("Electronics");
        Product product = createProduct(category);

        when(categoryRepository.findByName("Electronics")).thenReturn(Optional.of(category));
        when(modelMapper.map(request, Product.class)).thenReturn(product);
        when(productRepository.saveAll(any())).thenReturn(List.of(product));
        when(modelMapper.map(product, AddProductResponse.class)).thenReturn(createAddProductResponse(product));

        // Act
        Set<AddProductResponse> responses = addProductsService.addProducts(Set.of(request));

        // Assert
        assertEquals(1, responses.size());
        AddProductResponse response = responses.iterator().next();
        assertEquals(product.getId(), response.getId());
        assertEquals(product.getName(), response.getName());
        assertEquals(product.getBrand(), response.getBrand());
        verify(productRepository, times(1)).saveAll(any());
    }

    @Test
    void addProducts_ShouldThrowCategoryNotFoundException_WhenCategoryDoesNotExist() {
        // Arrange
        AddProductRequest request = createAddProductRequest("UnknownCategory");
        when(categoryRepository.findByName("UnknownCategory")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CategoryNotFoundException.class, () -> addProductsService.addProducts(Set.of(request)));
        verify(productRepository, never()).saveAll(any());
    }

    private AddProductRequest createAddProductRequest(String categoryName) {
        AddProductRequest request = new AddProductRequest();
        request.setName("Smartphone");
        request.setBrand("BrandX");
        request.setCategory(categoryName);

        AddAttributeRequest attributeRequest = new AddAttributeRequest();
        attributeRequest.setName("Color");
        attributeRequest.setValue("Black");

        request.setAttributes(Set.of(attributeRequest));
        return request;
    }

    private Category createCategory(String name) {
        Category category = new Category();
        category.setId(1L);
        category.setName(name);
        category.setDescription("Category description");
        return category;
    }

    private Product createProduct(Category category) {
        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setName("Smartphone");
        product.setBrand("BrandX");
        product.setCategory(category);

        ProductAttribute attribute = new ProductAttribute();
        attribute.setName("Color");
        attribute.setValue("Black");
        attribute.setProduct(product);

        product.setProductAttributes(Set.of(attribute));
        return product;
    }

    private AddProductResponse createAddProductResponse(Product product) {
        AddProductResponse response = new AddProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setBrand(product.getBrand());
        return response;
    }
}
