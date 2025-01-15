package com.ll.coffee.order;

import lombok.Getter;

import java.util.Map;

/**
 * @author shbaek
 * @since 25. 1. 15
 */
@Getter
public class OrderDto {
    private Long id;
    private String email;
    private String address;
    private int postalCode;
    private Map<Integer, Integer> orders;

    public OrderDto(Order order, Map<Integer, Integer> orders) {
        this.id = order.getId();
        this.email = order.getEmail();
        this.address = order.getAddress();
        this.postalCode = order.getPostalCode();
        this.orders = orders;
    }
}
