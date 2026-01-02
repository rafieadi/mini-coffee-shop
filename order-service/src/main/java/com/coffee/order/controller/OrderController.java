package com.coffee.order.controller;

import com.coffee.order.dto.OrderRequest;
import com.coffee.order.dto.OrderResponse;
import com.coffee.order.model.Order;
import com.coffee.order.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public OrderResponse create(@RequestBody OrderRequest order) {
        return service.create(order);
    }
}
