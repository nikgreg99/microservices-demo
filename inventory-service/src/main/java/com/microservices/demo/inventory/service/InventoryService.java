package com.microservices.demo.inventory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.microservices.demo.inventory.repository.InventoryRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public boolean isInStock(String skuCode,Integer quantity) {
        return inventoryRepository.existsBySkuCodeAndQuantityIsGreaterThanEqual(skuCode,quantity);
    }

}
