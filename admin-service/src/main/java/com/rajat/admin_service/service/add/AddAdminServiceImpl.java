package com.rajat.admin_service.service.add;

import com.rajat.admin_service.dto.request.add.AddInventoryDto;
import com.rajat.admin_service.dto.request.add.AddPriceDto;
import com.rajat.admin_service.dto.request.add.AddProductDetailsDto;
import com.rajat.admin_service.dto.request.add.AddProductDto;
import com.rajat.admin_service.dto.response.add.AddProductDetailsResponseDto;
import com.rajat.admin_service.model.inventory.request.AddProductInventoryClientRequest;
import com.rajat.admin_service.model.inventory.response.AddProductInventoryClientResponse;
import com.rajat.admin_service.model.price.request.AddProductPriceClientRequest;
import com.rajat.admin_service.model.price.response.AddProductPriceClientResponse;
import com.rajat.admin_service.model.product.request.AddProductClientRequest;
import com.rajat.admin_service.model.product.response.AddProductClientResponse;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import io.micrometer.observation.annotation.Observed;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddAdminServiceImpl implements AddAdminService {

  private final ModelMapper modelMapper;
  private final AddProductService addProductService;
  private final AddPriceService addPriceService;
  private final AddInventoryService addInventoryService;

  @Observed(name = "add.products")
  @Override
  public Set<AddProductDetailsResponseDto> addProductsDetails(
      @NonNull Set<AddProductDetailsDto> productDetails) {
    if (productDetails.isEmpty()) {
      return Set.of(AddProductDetailsResponseDto.builder().status("No products to add.").build());
    }
    Set<AddProductClientRequest> productClientRequests = new HashSet<>();
    Set<AddProductPriceClientRequest> priceClientRequests = new HashSet<>();
    Set<AddProductInventoryClientRequest> inventoryClientRequests = new HashSet<>();

    // Single iteration over product details to collect product, price, and inventory requests
    productDetails.forEach(
        productDetail -> {
          AddProductClientRequest productRequest = mapProduct(productDetail.getProduct());
          productClientRequests.add(productRequest);

          AddProductPriceClientRequest priceRequest =
              mapPrice(productDetail.getPrice(), productRequest.getId());
          priceClientRequests.add(priceRequest);

          AddProductInventoryClientRequest inventoryRequest =
              mapInventory(productDetail.getInventory(), productRequest.getId());
          inventoryClientRequests.add(inventoryRequest);
        });

    // Call the respective services to add products, prices, and inventory
    Set<AddProductClientResponse> productResponses =
        addProductService.addProducts(productClientRequests);
    Set<AddProductPriceClientResponse> priceResponses =
        addPriceService.addProductPrice(priceClientRequests);
    Set<AddProductInventoryClientResponse> inventoryResponses =
        addInventoryService.addProductInventory(inventoryClientRequests);

    return Set.of(
        AddProductDetailsResponseDto.builder().status("Product(s) added successfully.").build());
  }

  /** Maps product DTO to client request and generates a new UUID for each product. */
  AddProductClientRequest mapProduct(AddProductDto product) {
    AddProductClientRequest productClientRequest =
        modelMapper.map(product, AddProductClientRequest.class);
    productClientRequest.setId(UUID.randomUUID()); // Set UUID for the product
    return productClientRequest;
  }

  /** Maps price DTO to client request and sets the product ID. */
  AddProductPriceClientRequest mapPrice(AddPriceDto price, UUID productId) {
    AddProductPriceClientRequest priceClientRequest =
        modelMapper.map(price, AddProductPriceClientRequest.class);
    priceClientRequest.setProductId(productId); // Set productId in price
    return priceClientRequest;
  }

  /** Maps inventory DTO to client request and sets the product ID. */
  AddProductInventoryClientRequest mapInventory(AddInventoryDto inventory, UUID productId) {
    AddProductInventoryClientRequest inventoryClientRequest =
        modelMapper.map(inventory, AddProductInventoryClientRequest.class);
    inventoryClientRequest.setProductId(productId); // Set productId in inventory
    return inventoryClientRequest;
  }
}
