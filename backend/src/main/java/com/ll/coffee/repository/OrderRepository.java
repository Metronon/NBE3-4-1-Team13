package com.ll.coffee.repository;

import com.ll.coffee.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author shbaek
 * @since 25. 1. 15
 */
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Optional<Order> findFirstByOrderByIdDesc();
}
