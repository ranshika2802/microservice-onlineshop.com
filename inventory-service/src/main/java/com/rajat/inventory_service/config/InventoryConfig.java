package com.rajat.inventory_service.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InventoryConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
