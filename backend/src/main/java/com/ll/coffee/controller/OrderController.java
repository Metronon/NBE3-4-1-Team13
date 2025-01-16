package com.ll.coffee.controller;

import com.ll.coffee.OrderMenu.OrderMenuDto;
import com.ll.coffee.global.RsData;
import com.ll.coffee.order.Order;
import com.ll.coffee.order.OrderDto;
import com.ll.coffee.service.OrderMenuService;
import com.ll.coffee.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author shbaek
 * @since 25. 1. 15
 */
@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderMenuService orderMenuService;

    record OrderReqBody(
            @Email String email,
            @NotNull String address,
            @NotNull int postalCode,
            @NotNull Map<Long, Integer> orders
    ) {
    }

    @PostMapping("/order")
    @Operation(summary = "주문 생성")
    public ResponseEntity<RsData<OrderDto>> create(@RequestBody @Valid OrderReqBody orderReqBody) {
        Order order = orderService.save(orderReqBody.email, orderReqBody.address, orderReqBody.postalCode, orderReqBody.orders);

        RsData<OrderDto> rsData = new RsData<>(
                "201-1",
                "%d번 주문이 완료되었습니다.".formatted(order.getId()),
                new OrderDto(order, orderReqBody.orders)
        );

        return new ResponseEntity<>(rsData,HttpStatus.CREATED);
    }


    @GetMapping("/order")
    @Operation(summary = "email로 주문 조회")
    public List<OrderMenuDto> getOrdersByEmail(@RequestParam String email) {
        return orderMenuService.findByEmail(email);
    }
}
