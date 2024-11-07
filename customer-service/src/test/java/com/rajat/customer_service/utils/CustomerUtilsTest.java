package com.rajat.customer_service.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerUtilsTest {

    private CustomerUtils customerUtils;

    @BeforeEach
    void setUp() {
        customerUtils = new CustomerUtils();
    }

    @Test
    void getUncachedCategories_withAllUncachedCategories_shouldReturnAllCategories() {
        Set<String> categories = Set.of("Electronics", "Books", "Fashion");
        Set<String> cachedCategories = Set.of();

        Set<String> result = customerUtils.getUncachedCategories(categories, cachedCategories);

        assertEquals(categories, result);
    }

    @Test
    void getUncachedCategories_withAllCachedCategories_shouldReturnEmptySet() {
        Set<String> categories = Set.of("Electronics", "Books", "Fashion");
        Set<String> cachedCategories = Set.of("Electronics", "Books", "Fashion");

        Set<String> result = customerUtils.getUncachedCategories(categories, cachedCategories);

        assertTrue(result.isEmpty());
    }

    @Test
    void getUncachedCategories_withPartialCachedCategories_shouldReturnUncachedCategories() {
        Set<String> categories = Set.of("Electronics", "Books", "Fashion");
        Set<String> cachedCategories = Set.of("Electronics");

        Set<String> expectedUncached = Set.of("Books", "Fashion");

        Set<String> result = customerUtils.getUncachedCategories(categories, cachedCategories);

        assertEquals(expectedUncached, result);
    }

    @Test
    void getUncachedCategories_withNoCategories_shouldReturnEmptySet() {
        Set<String> categories = new HashSet<>();
        Set<String> cachedCategories = Set.of("Electronics");

        Set<String> result = customerUtils.getUncachedCategories(categories, cachedCategories);

        assertTrue(result.isEmpty());
    }

    @Test
    void getUncachedCategories_withEmptyCachedCategories_shouldReturnAllCategories() {
        Set<String> categories = Set.of("Electronics", "Books");
        Set<String> cachedCategories = new HashSet<>();

        Set<String> result = customerUtils.getUncachedCategories(categories, cachedCategories);

        assertEquals(categories, result);
    }
}
