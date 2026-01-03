package com.coffee.order.repository;

import com.coffee.order.dto.OrderSummary;
import com.coffee.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(
            value =
                    "SELECT " +
                            "o.id AS id, " +
                            "o.customer_name AS customerName, " +
                            "SUM(oi.quantity) AS totalItems, " +
                            "SUM(oi.price * oi.quantity) AS totalPrice " +
                            "FROM `orders` o " +
                            "JOIN order_items oi ON oi.order_id = o.id " +
                            "WHERE o.id = :orderId " +
                            "GROUP BY o.id, o.customer_name",
            nativeQuery = true
    )
    OrderSummary findOrderSummary(@Param("orderId") Long orderId);
}

