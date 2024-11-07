package com.rajat.customer_service.utils;

import com.rajat.customer_service.dto.ApiResponse;
import com.rajat.customer_service.dto.ViewProductDetailsResponse;
import com.rajat.customer_service.model.ApiClientResponse;
import com.rajat.customer_service.model.ViewInventoryClientResponse;
import com.rajat.customer_service.model.ViewPriceClientResponse;
import com.rajat.customer_service.model.ViewProductDetailsClientResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ApiResponseUtilsTest {

    @Mock
    private ResponseMapperUtils responseMapperUtils;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ApiResponseUtils apiResponseUtils;

    private Map<String, Set<ViewProductDetailsResponse>> cacheMap;
    private Map<String, Set<ViewProductDetailsResponse>> dbMap;

    @BeforeEach
    void setUp() {
        cacheMap = new HashMap<>();
        dbMap = new HashMap<>();
    }

    @Test
    void generateApiResponse_withValidData_shouldReturnSuccessResponse() {
        // Mock merged map result
        Map<String, Set<ViewProductDetailsResponse>> mergedMap = new HashMap<>();
        when(responseMapperUtils.mergeProductMaps(cacheMap, dbMap)).thenReturn(mergedMap);

        ApiResponse response = apiResponseUtils.generateApiResponse(cacheMap, dbMap);

        assertTrue(response.isSuccess());
        assertEquals(Set.of("Data fetched successfully"), response.getMessage());
        assertNotNull(response.getViewProductDetailsResponses());
        verify(responseMapperUtils, times(1)).mergeProductMaps(cacheMap, dbMap);
    }

    @Test
    void generateApiResponse_withInvalidDbKeys_shouldReturnErrorResponse() {
        // Add invalid key to dbMap
        dbMap.put("InvalidNANKey", Collections.emptySet());

        ApiResponse response = apiResponseUtils.generateApiResponse(cacheMap, dbMap);

        assertFalse(response.isSuccess());
        assertEquals(Set.of("Invalid category names in response"), response.getMessage());
        assertNull(response.getViewProductDetailsResponses());
    }

    @Test
    void mapResponse_withValidClientResponse_shouldReturnMappedResponse() {
        ApiClientResponse apiClientResponse = new ApiClientResponse();
        ViewProductDetailsResponse viewProductDetailsResponse = new ViewProductDetailsResponse();

        // Set up a mock client response with valid inventory and price
        ViewProductDetailsClientResponse clientResponse = new ViewProductDetailsClientResponse();
        ViewPriceClientResponse price = new ViewPriceClientResponse();
        ViewInventoryClientResponse inventory = new ViewInventoryClientResponse();
        price.setAmount(100);
        inventory.setAvailable(10);
        clientResponse.setPrice(price);
        clientResponse.setInventory(inventory);

        apiClientResponse.setDataSet(Set.of(clientResponse));

        when(modelMapper.map(clientResponse, ViewProductDetailsResponse.class)).thenReturn(viewProductDetailsResponse);

        Map<String, Set<ViewProductDetailsResponse>> result = apiResponseUtils.mapResponse(apiClientResponse);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.containsKey("Unknown"));  // Assuming the category is null in the mock
        assertTrue(result.get("Unknown").contains(viewProductDetailsResponse));
        verify(modelMapper, times(1)).map(clientResponse, ViewProductDetailsResponse.class);
    }

    @Test
    void mapResponse_withInvalidClientResponse_shouldReturnEmptyMap() {
        ApiClientResponse apiClientResponse = new ApiClientResponse();
        ViewProductDetailsClientResponse clientResponse = new ViewProductDetailsClientResponse();
        ViewPriceClientResponse price = new ViewPriceClientResponse();
        ViewInventoryClientResponse inventory = new ViewInventoryClientResponse();

        // Set invalid values for price and inventory
        price.setAmount(0);  // Invalid price amount
        inventory.setAvailable(0);  // Invalid inventory availability
        clientResponse.setPrice(price);
        clientResponse.setInventory(inventory);

        apiClientResponse.setDataSet(Set.of(clientResponse));

        Map<String, Set<ViewProductDetailsResponse>> result = apiResponseUtils.mapResponse(apiClientResponse);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(modelMapper, never()).map(any(), eq(ViewProductDetailsResponse.class));
    }

    @Test
    void containsInvalidKeys_withInvalidKey_shouldReturnTrue() {
        Map<String, Set<ViewProductDetailsResponse>> productMap = new HashMap<>();
        productMap.put("CategoryNAN", Collections.emptySet());

        boolean result = apiResponseUtils.containsInvalidKeys(productMap);

        assertTrue(result);
    }

    @Test
    void containsInvalidKeys_withValidKeys_shouldReturnFalse() {
        Map<String, Set<ViewProductDetailsResponse>> productMap = new HashMap<>();
        productMap.put("CategoryA", Collections.emptySet());
        productMap.put("CategoryB", Collections.emptySet());

        boolean result = apiResponseUtils.containsInvalidKeys(productMap);

        assertFalse(result);
    }
}
