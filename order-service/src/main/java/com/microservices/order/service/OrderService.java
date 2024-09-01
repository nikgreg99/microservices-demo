package com.microservices.order.service;

import com.microservices.order.client.InventoryClient;
import com.microservices.order.model.Order;
import com.microservices.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.microservices.order.dto.OrderRequest;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public void placeOrder(OrderRequest orderRequest) {

        var isProductIsInStock = inventoryClient.isInStock(orderRequest.skuCode(),orderRequest.quantity());

        if(isProductIsInStock) {
            var order = mapToOrder(orderRequest);
            orderRepository.save(order);
        }
        else{
            throw new RuntimeException("Profuct with SkuCode " + orderRequest.skuCode() + " is not in stock");
        }
    }

    private static Order mapToOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setPrice(orderRequest.price());
        order.setSkuCode(orderRequest.skuCode());
        order.setQuantity(orderRequest.quantity());
        return order;
    }
}
