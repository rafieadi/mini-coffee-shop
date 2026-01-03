package com.coffee.order.dto;

public interface OrderSummary {

    Long getId();
    String getCustomerName();
    Integer getTotalItems();
    Double getTotalPrice();
}
