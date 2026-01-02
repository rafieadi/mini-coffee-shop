package com.coffee.order.service;

import com.coffee.order.client.ProductClient;
import com.coffee.order.dto.*;
import com.coffee.order.model.*;
import com.coffee.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final ProductClient productClient;

    public OrderService(OrderRepository repository, ProductClient productClient) {
        this.repository = repository;
        this.productClient = productClient;
    }

    public OrderResponse create(OrderRequest request) {

        Order order = new Order();
        order.setCustomerName(request.getCustomerName());

        List<OrderItem> items = request.getItems().stream().map(req -> {

            ProductResponse product =
                    productClient.getProduct(req.getProductId());

            OrderItem item = new OrderItem();
            item.setProductId(product.getId());
            item.setProductName(product.getName());
            item.setPrice(product.getPrice());
            item.setQuantity(req.getQuantity());
            item.setOrder(order);

            return item;

        }).collect(Collectors.toList());

        double totalPrice = items.stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();

        order.setItems(items);
        order.setTotalPrice(totalPrice);

        Order savedOrder = repository.save(order);

        // === MAPPING KE RESPONSE DTO ===
        List<OrderItemResponse> itemResponses = savedOrder.getItems().stream()
                .map(i -> new OrderItemResponse (
                    i.getProductId(),
                    i.getProductName(),
                    i.getPrice(),
                    i.getQuantity()
                )).collect(Collectors.toList());

        return new OrderResponse(
                savedOrder.getId(),
                savedOrder.getCustomerName(),
                savedOrder.getTotalPrice(),
                itemResponses
        );
    }
}
