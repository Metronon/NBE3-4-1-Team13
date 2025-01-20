package com.ll.coffee.repository;

import com.ll.coffee.OrderMenu.OrderMenu;
import com.ll.coffee.OrderMenu.OrderMenuId;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 전체 주문조회를 위한 Repository
 * @author seeyeon
 * @since 25. 1. 15
 */
public interface OrderMenuRepository extends JpaRepository<OrderMenu, OrderMenuId> {


    List<OrderMenu> findByOrderId(Long id);
}
