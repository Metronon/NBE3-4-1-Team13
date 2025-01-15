package com.ll.coffee.controller;

import com.ll.coffee.global.RsData;
import com.ll.coffee.order.Order;
import com.ll.coffee.order.OrderDto;
import com.ll.coffee.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author shbaek
 * @since 25. 1. 15
 */
@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    record OrderReqBody(
       String email,
       String address,
       int postalCode,
       Map<Integer, Integer> orders
    ){}

    @PostMapping("/order")
    public RsData<OrderDto> create(@RequestBody OrderReqBody orderReqBody) {
        Order order = orderService.save(orderReqBody.email, orderReqBody.address, orderReqBody.postalCode, orderReqBody.orders);

        return new RsData<>(
                "201-1",
                "%d번 주문이 완료되었습니다.".formatted(order.getId()),
                new OrderDto(order, orderReqBody.orders)
        );
    }
}
