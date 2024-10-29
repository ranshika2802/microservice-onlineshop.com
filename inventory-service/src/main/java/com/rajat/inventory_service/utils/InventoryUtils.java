package com.rajat.inventory_service.utils;

import com.rajat.inventory_service.dto.UpdateProductInventoryRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InventoryUtils {

    private final ModelMapper modelMapper;

    public static void validateInventoryRequest(@NonNull final UpdateProductInventoryRequest updateProductInventoryRequest) {
        //First check if total =
    }

    // Reusable method for mapping set
    public <L, C> Set<C> mapSet(Set<L> sourceList, Class<C> targetClass) {
        return sourceList.stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toUnmodifiableSet());
    }

    // Reusable method for mapping list
    public <L, C> List<C> mapList(List<L> sourceList, Class<C> targetClass) {
        return sourceList.stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toUnmodifiableList());
    }
}
