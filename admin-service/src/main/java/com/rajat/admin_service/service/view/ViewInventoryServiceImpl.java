package com.rajat.admin_service.service.view;

import com.rajat.admin_service.client.InventoryClient;
import com.rajat.admin_service.model.inventory.ApiInventoryClientResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ViewInventoryServiceImpl implements ViewInventoryService {

  private static final String INVENTORY_SERVICE = "inventoryService";
  private static final String FALLBACK_METHOD = "fallBackForInventory";
  private final InventoryClient inventoryClient;
  private int counter = 1;

  @Override
  @Retry(name = INVENTORY_SERVICE, fallbackMethod = FALLBACK_METHOD)
  @CircuitBreaker(name = INVENTORY_SERVICE)
  public ApiInventoryClientResponse viewInventoryByProductId(Set<UUID> productIds) {
    log.info("{} inside viewInventoryByProductId method count = {}", new Date(), counter++);
    return new ApiInventoryClientResponse(
        true, null, inventoryClient.viewInventoryByProductId(productIds));
  }

  public ApiInventoryClientResponse fallBackForInventory(
      Set<UUID> productIds, Throwable throwable) {
    counter = 0;
    log.info("inside fallback method.");
    return new ApiInventoryClientResponse(false, INVENTORY_SERVICE + " NAN", null);
  }
}
