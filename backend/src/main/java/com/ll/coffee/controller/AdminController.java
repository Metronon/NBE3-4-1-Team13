package com.ll.coffee.controller;

import com.ll.coffee.OrderMenu.OrderMenuDto;
import com.ll.coffee.global.RsData;
import com.ll.coffee.service.OrderMenuService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
    public ResponseEntity<RsData<List<OrderMenuDto>>> getAllOrders() {
        List<OrderMenuDto> orders = orderMenuService.getAllOrders();

        if(orders.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new RsData<>(
                            "204-1",
                            "전체 주문 조회 결과가 없습니다.",
                            new ArrayList<>() //빈 리스트 반환
                    ));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new RsData<>(
                        "200-1",
                        "전체 주문 조회 성공",
                        orders
                ));
    }

}
