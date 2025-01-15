package com.ll.coffee.service;

import com.ll.coffee.order.Order;
import com.ll.coffee.order.OrderMenu;
import com.ll.coffee.order.OrderMenuId;
import com.ll.coffee.repository.OrderMenuRepository;
import com.ll.coffee.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

/**
 * @author shbaek
 * @since 25. 1. 15
 */
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMenuRepository orderMenuRepository;

    public Order save(String email, String address, int postalCode, Map<Integer, Integer> orders) {
        Order order = new Order();
        order.setEmail(email);
        order.setAddress(address);
        order.setPostalCode(postalCode);
        order.setCreatedAt(LocalDateTime.now());

        Order saveOrder = orderRepository.save(order);

        // getid 따로

        orders.forEach((menuId, count) -> {
            OrderMenu orderMenu = new OrderMenu();
            OrderMenuId orderMenuId = new OrderMenuId();
            orderMenuId.setOrderId(saveOrder.getId());
            orderMenuId.setMenuId(menuId);

            orderMenu.setId(orderMenuId);
            orderMenu.setCount(count);

            orderMenuRepository.save(orderMenu);
        });

        return saveOrder;
    }

    public Optional<Order> findLatest() {
        return orderRepository.findFirstByOrderByIdDesc();
    }
}
