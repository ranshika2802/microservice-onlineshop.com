package com.rajat.customer_service.utils;

import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CustomerUtils {

  /** Filters out cached categories from the input category set. */
  public Set<String> getUncachedCategories(Set<String> categories, Set<String> cachedCategories) {
    return categories.stream()
        .filter(category -> !cachedCategories.contains(category))
        .collect(Collectors.toSet());
  }
}
