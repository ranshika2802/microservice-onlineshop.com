package com.rajat.customer_service.utils;

import com.rajat.customer_service.dto.ViewProductDetailsResponse;
import com.rajat.customer_service.model.ApiClientResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResponseMapperUtils {

  private final ModelMapper modelMapper;

  /** Maps the ApiClientResponse to a Map categorized by product categories. */
  public Map<String, Set<ViewProductDetailsResponse>> mapClientResponse(
      ApiClientResponse apiClientResponse) {
    return apiClientResponse.getDataSet().stream()
        .map(
            clientResponse ->
                modelMapper.map(
                    clientResponse,
                    ViewProductDetailsResponse.class)) // Map to ViewProductDetailsResponse
        .collect(
            Collectors.groupingBy(
                viewProductDetailsResponse ->
                    viewProductDetailsResponse.getCategory().getName(), // Group by category name
                Collectors.toSet()));
  }

  /** Merges two product maps by category. */
  public Map<String, Set<ViewProductDetailsResponse>> mergeProductMaps(
      Map<String, Set<ViewProductDetailsResponse>> cacheMap,
      Map<String, Set<ViewProductDetailsResponse>> dbMap) {
    // Create a mutable copy of the cacheMap using HashMap
    Map<String, Set<ViewProductDetailsResponse>> mergedProducts = new HashMap<>(cacheMap);

    if (dbMap != null) {
      dbMap.forEach(
          (category, productDetailsSet) ->
              mergedProducts.merge(
                  category,
                  productDetailsSet,
                  (existing, newProducts) -> {
                    existing.addAll(newProducts); // Merge the new products into the existing set
                    return existing; // Return the updated set
                  }));
    }

    return mergedProducts;
  }
}
