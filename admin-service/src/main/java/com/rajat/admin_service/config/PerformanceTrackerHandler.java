/*
package com.rajat.admin_service.config;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(999)
public class PerformanceTrackerHandler implements ObservationHandler<Observation.Context> {

  private static final Logger log = LoggerFactory.getLogger(PerformanceTrackerHandler.class);

  @Override
  public void onStart(Observation.Context context) {
    log.info("execution started {}", context.getName());
    context.put("time", System.currentTimeMillis());
  }

  @Override
  public void onError(Observation.Context context) {
    log.error("Error occurred {}", context.getError().getMessage());
  }

  @Override
  public void onEvent(Observation.Event event, Observation.Context context) {
    ObservationHandler.super.onEvent(event, context);
  }

  @Override
  public void onScopeOpened(Observation.Context context) {
    ObservationHandler.super.onScopeOpened(context);
  }

  @Override
  public void onScopeClosed(Observation.Context context) {
    ObservationHandler.super.onScopeClosed(context);
  }

  @Override
  public void onScopeReset(Observation.Context context) {
    ObservationHandler.super.onScopeReset(context);
  }

  @Override
  public void onStop(Observation.Context context) {
    log.info(
        "Execution stopped {} duration {}",
        context.getName(),
        System.currentTimeMillis() - context.getOrDefault("time", 0l));
  }

  @Override
  public boolean supportsContext(Observation.Context context) {
    return true;
  }
}
*/
