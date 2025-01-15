package com.ll.coffee.repository;

import com.ll.coffee.order.OrderMenu;
import com.ll.coffee.order.OrderMenuId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author shbaek
 * @since 25. 1. 15
 */
public interface OrderMenuRepository extends JpaRepository<OrderMenu, OrderMenuId> {
}
