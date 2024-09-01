package com.microservices.demo.inventory.controller;

import com.microservices.demo.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    boolean isInStock(@RequestParam String skuCode, @RequestParam  Integer quantity) {
        return inventoryService.isInStock(skuCode, quantity);
    }
}
