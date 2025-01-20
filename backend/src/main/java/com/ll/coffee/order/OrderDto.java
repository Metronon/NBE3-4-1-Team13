package com.ll.coffee.order;

import lombok.Getter;

import java.util.Map;

/**
 * @author shbaek
 * @since 25. 1. 15
 */
@Getter
public class OrderDto {
    private final Long id;
    private final String email;
    private final String address;
    private final String postalCode;
    private final Map<Long, Integer> orders;

    public OrderDto(Order order, Map<Long, Integer> orders) {
        this.id = order.getId();
        this.email = order.getEmail();
        this.address = order.getAddress();
        this.postalCode = order.getPostalCode();
        this.orders = orders;
    }
}
