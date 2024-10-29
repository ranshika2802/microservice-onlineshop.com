package com.rajat.price_service.utils;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PriceUtils {
    // Reusable method for mapping set
    public static  <L, C> Set<C> mapSet(Set<L> sourceList, Class<C> targetClass, ModelMapper modelMapper) {
        return sourceList.stream()  // Use parallelStream if performance is needed
                .map(element -> modelMapper.map(element, targetClass)).collect(Collectors.toUnmodifiableSet());
    }

    // Reusable method for mapping set
    public static  <L, C> List<C> mapList(List<L> sourceList, Class<C> targetClass, ModelMapper modelMapper) {
        return sourceList.stream()
                .map(element -> modelMapper.map(element, targetClass))// Ensure this works with UUID
                .collect(Collectors.toUnmodifiableList());
    }
}
