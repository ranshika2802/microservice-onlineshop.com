package com.rajat.admin_service.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminConfiguration {
  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }
}
