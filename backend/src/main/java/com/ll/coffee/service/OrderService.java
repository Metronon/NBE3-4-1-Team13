package com.ll.coffee.service;

import com.ll.coffee.OrderMenu.OrderMenu;
import com.ll.coffee.order.Order;
import com.ll.coffee.repository.OrderMenuRepository;
import com.ll.coffee.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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

    /**
     * Order, OrderMenu 테이블 데이터 저장
     *
     * @author shbaek
     * @since 25. 1. 15
     */
    public Order save(String email, String address, String postalCode, Map<Long, Integer> orders) {
        Order order = new Order();
        order.setEmail(email);
        order.setAddress(address);
        order.setPostalCode(postalCode);
        order.setCreatedAt(LocalDateTime.now());

        Order saveOrder = orderRepository.save(order);

        OrderMenu orderMenu = new OrderMenu();
        orderMenu.setOrderId(saveOrder.getId());

        orders.forEach((menuId, count) -> {
            orderMenu.setMenuId(menuId);
            orderMenu.setCount(count);

            orderMenuRepository.save(orderMenu);
        });

        return saveOrder;
    }

    /**
     * 가장 최근에 생성된 주문 조회
     *
     * @author shbaek
     * @since 25. 1. 15
     */
    public Optional<Order> findLatest() {
        return orderRepository.findFirstByOrderByIdDesc();
    }

    public List<Order> findByEmail(String email) {
        return orderRepository.findByEmail(email);
    }
}
