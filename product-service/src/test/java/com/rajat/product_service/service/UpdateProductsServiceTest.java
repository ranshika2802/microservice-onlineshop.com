package com.rajat.product_service.service;

import com.rajat.product_service.dto.UpdateAttributesRequest;
import com.rajat.product_service.dto.UpdateAttributesResponse;
import com.rajat.product_service.dto.UpdateProductRequest;
import com.rajat.product_service.dto.UpdateProductResponse;
import com.rajat.product_service.exception.CategoryNotFoundException;
import com.rajat.product_service.exception.ProductNotFoundException;
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

public class UpdateProductsServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private UpdateProductsService updateProductsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateProducts_ShouldUpdateExistingProduct() {
        // Arrange
        UUID productId = UUID.randomUUID();
        UpdateProductRequest request = createUpdateProductRequest(productId, "UpdatedProduct", "UpdatedBrand", "Electronics", "Updated description");

        Product existingProduct = createProduct(productId, "OldProduct", "OldBrand", "Old description", createCategory("Electronics"));
        Category category = createCategory("Electronics");

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(categoryRepository.findByName("Electronics")).thenReturn(Optional.of(category));

        // Simulate the updates on the existing product directly
        existingProduct.setName("UpdatedProduct");
        existingProduct.setBrand("UpdatedBrand");
        existingProduct.setDescription("Updated description");

        when(productRepository.saveAll(any())).thenReturn(List.of(existingProduct));

        // Mock the ModelMapper to return a fully populated UpdateProductResponse
        UpdateProductResponse expectedResponse = new UpdateProductResponse();
        expectedResponse.setId(existingProduct.getId());
        expectedResponse.setName("UpdatedProduct");
        expectedResponse.setBrand("UpdatedBrand");
        expectedResponse.setDescription("Updated description");
        expectedResponse.setCategory("Electronics");

        when(modelMapper.map(existingProduct, UpdateProductResponse.class)).thenReturn(expectedResponse);

        // Act
        Set<UpdateProductResponse> responses = updateProductsService.updateProducts(Set.of(request));

        // Assert
        assertEquals(1, responses.size());
        UpdateProductResponse response = responses.iterator().next();

        // Verify all updated fields
        assertEquals("UpdatedProduct", response.getName());
        assertEquals("UpdatedBrand", response.getBrand());
        assertEquals("Updated description", response.getDescription());
        assertEquals("Electronics", response.getCategory());

        // Ensure saveAll is called with the modified product
        verify(productRepository, times(1)).saveAll(any());
    }


    @Test
    void updateProducts_ShouldThrowProductNotFoundException_WhenProductDoesNotExist() {
        // Arrange
        UUID productId = UUID.randomUUID();
        UpdateProductRequest request = createUpdateProductRequest(productId, "UpdatedProduct", "UpdatedBrand", "Electronics", "Updated description");

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> updateProductsService.updateProducts(Set.of(request)));
        verify(productRepository, never()).saveAll(any());
    }

    @Test
    void updateProducts_ShouldThrowCategoryNotFoundException_WhenCategoryDoesNotExist() {
        // Arrange
        UUID productId = UUID.randomUUID();
        UpdateProductRequest request = createUpdateProductRequest(productId, "UpdatedProduct", "UpdatedBrand", "UnknownCategory", "Updated description");
        Product existingProduct = createProduct(productId, "OldProduct", "OldBrand", "Old description", createCategory("Electronics"));

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(categoryRepository.findByName("UnknownCategory")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CategoryNotFoundException.class, () -> updateProductsService.updateProducts(Set.of(request)));
        verify(productRepository, never()).saveAll(any());
    }

    private UpdateProductRequest createUpdateProductRequest(UUID productId, String name, String brand, String category, String description) {
        UpdateProductRequest request = new UpdateProductRequest();
        request.setId(productId);
        request.setName(name);
        request.setBrand(brand);
        request.setCategory(category);
        request.setDescription(description);

        UpdateAttributesRequest attributeRequest = new UpdateAttributesRequest();
        attributeRequest.setName("Color");
        attributeRequest.setValue("Red");

        request.setAttributes(Set.of(attributeRequest));
        return request;
    }

    private Product createProduct(UUID productId, String name, String brand, String description, Category category) {
        Product product = new Product();
        product.setId(productId);
        product.setName(name);
        product.setBrand(brand);
        product.setDescription(description);
        product.setCategory(category);

        ProductAttribute attribute = new ProductAttribute();
        attribute.setName("Color");
        attribute.setValue("Red");
        attribute.setProduct(product);

        product.setProductAttributes(Set.of(attribute));
        return product;
    }

    private Category createCategory(String name) {
        Category category = new Category();
        category.setId(1L);
        category.setName(name);
        return category;
    }

    private UpdateProductResponse createUpdateProductResponse(Product product) {
        UpdateProductResponse response = new UpdateProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setBrand(product.getBrand());
        response.setCategory(product.getCategory().getName());

        UpdateAttributesResponse attributeResponse = new UpdateAttributesResponse();
        attributeResponse.setName("Color");
        attributeResponse.setValue("Red");

        response.setAttributesResponses(Set.of(attributeResponse));
        return response;
    }
}
