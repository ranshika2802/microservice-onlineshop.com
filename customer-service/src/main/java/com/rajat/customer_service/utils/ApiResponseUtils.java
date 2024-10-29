package com.rajat.customer_service.utils;

import com.rajat.customer_service.dto.ApiResponse;
import com.rajat.customer_service.dto.ViewProductDetailsResponse;
import com.rajat.customer_service.model.ApiClientResponse;

import java.util.*;
import java.util.stream.Collectors;

import com.rajat.customer_service.model.ViewInventoryClientResponse;
import com.rajat.customer_service.model.ViewPriceClientResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApiResponseUtils {

  private final ResponseMapperUtils responseMapperUtils;
  private final ModelMapper modelMapper;

  /** Generates an ApiResponse by merging cache and database maps, with a check for invalid keys. */
  public ApiResponse generateApiResponse(
      Map<String, Set<ViewProductDetailsResponse>> cacheMap,
      Map<String, Set<ViewProductDetailsResponse>> dbMap) {
    if (Objects.nonNull(dbMap) && containsInvalidKeys(dbMap)) {
      return new ApiResponse(false, Set.of("Invalid category names in response"), null);
    }

    Map<String, Set<ViewProductDetailsResponse>> mergedProducts =
        responseMapperUtils.mergeProductMaps(cacheMap, dbMap);

    Set<ViewProductDetailsResponse> clientResponses =
        mergedProducts.values().stream().flatMap(Set::stream).collect(Collectors.toSet());

    return new ApiResponse(true, Set.of("Data fetched successfully"), clientResponses);
  }

  public Map<String, Set<ViewProductDetailsResponse>> mapResponse(
      ApiClientResponse apiClientResponse) {

    return Optional.ofNullable(apiClientResponse.getDataSet()) // Wrap in Optional
            .orElseGet(Collections::emptySet) // Provide an empty set if dataSet is null
            .stream()
            .filter(clientResponse -> {
              // Perform the validation checks here
              ViewPriceClientResponse price = clientResponse.getPrice();
              ViewInventoryClientResponse inventory = clientResponse.getInventory();

              // Check if price is non-null and amount is non-zero
              boolean isPriceValid = price != null && price.getAmount() > 0;

              // Check if inventory is non-null and available quantity is greater than zero
              boolean isInventoryValid = inventory != null && inventory.getAvailable() > 0;

              // Include the product only if both price and inventory are valid
              return isPriceValid && isInventoryValid;
            })
            .map(clientResponse ->
                    modelMapper.map(
                            clientResponse,
                            ViewProductDetailsResponse.class)) // Mapping to ViewProductDetailsResponse
            .collect(Collectors.groupingBy(
                    viewProductDetailsResponse ->
                            viewProductDetailsResponse.getCategory() != null ? // Check for null category
                                    viewProductDetailsResponse.getCategory().getName() : "Unknown", // Provide default if null
                    Collectors.toSet()));
  }

  /** Checks if any key in the product map contains "NAN". */
  public boolean containsInvalidKeys(Map<String, Set<ViewProductDetailsResponse>> productMap) {
    return productMap.keySet().stream().anyMatch(key -> key.contains("NAN"));
  }
}
