package com.ll.coffee.repository;

import com.ll.coffee.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author shbaek
 * @since 25. 1. 15
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findFirstByOrderByIdDesc();
    List<Order> findByEmail(String email);

    Page<Order> findByEmailContaining(String email, Pageable pageable);
}
