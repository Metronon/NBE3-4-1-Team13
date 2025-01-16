package com.ll.coffee.controller;

import com.ll.coffee.OrderMenu.OrderMenuDto;
import com.ll.coffee.service.OrderMenuService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * 나중에 이름 변경
 *
 * @author shjung
 * @since 25. 1. 14.
 */

@RestController
@AllArgsConstructor
public class AdminController {

    private final OrderMenuService orderMenuService;

    @GetMapping("/order-menu")
    public ResponseEntity<List<OrderMenuDto>> getAllOrders() {
        List<OrderMenuDto> orders = orderMenuService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

}
