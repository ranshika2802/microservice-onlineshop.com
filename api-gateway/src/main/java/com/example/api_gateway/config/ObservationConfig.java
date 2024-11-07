package com.example.api_gateway.config;

import feign.Capability;
import feign.micrometer.MicrometerCapability;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObservationConfig {
  //  @Bean
  //  public ObservedAspect observedAspect(ObservationRegistry observationRegistry) {
  //    return new ObservedAspect(observationRegistry);
  //  }

  @Bean
  public Capability capability(final MeterRegistry registry) {
    return new MicrometerCapability(registry);
  }
}
