package com.ll.coffee.controller;

import com.ll.coffee.OrderMenu.OrderMenuWithOrderDto;
import com.ll.coffee.global.RsData;
import com.ll.coffee.global.ServiceException;
import com.ll.coffee.menu.MenuDto;
import com.ll.coffee.order.Order;
import com.ll.coffee.order.OrderDto;
import com.ll.coffee.service.MenuService;
import com.ll.coffee.service.OrderMenuService;
import com.ll.coffee.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
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
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final OrderMenuService orderMenuService;
    private final MenuService menuService;

    /**
     * 주문 생성 요청 시 전달 받을 데이터
     */
    record OrderReqBody(
            @Email String email,
            @NotBlank @Length(min = 1) String address,
            @NotBlank @Pattern(regexp = "\\d{5}", message = "우편번호는 5자리 숫자여야 합니다.") String postalCode,
            @NotNull Map<Long, Integer> orders
    ) {
    }

    /**
     * 주문 생성 메서드
     *
     * @param orderReqBody 주문 생성에 필요한 데이터
     * @return 주문 정보를 포함한 ResponseEntity<RsData<OrderDto>>
     * @author shbaek
     */
    @PostMapping
    @Operation(summary = "주문 생성")
    public ResponseEntity<RsData<OrderDto>> create(@RequestBody @Valid OrderReqBody orderReqBody) {
        orderReqBody.orders.forEach((menuId, count) -> {
            MenuDto menu = menuService.getMenuById(menuId);

            if (menuId == null) {
                throw new ServiceException("400-2", "메뉴를 확인해주세요.");
            }
            if (count == null || count <= 0) {
                throw new ServiceException("400-3", "메뉴 수량은 0보다 커야 합니다.");
            }
        });

        Order order = orderService.save(orderReqBody.email, orderReqBody.address, orderReqBody.postalCode, orderReqBody.orders);

        RsData<OrderDto> rsData = new RsData<>(
                "201-1",
                "%d번 주문이 완료되었습니다.".formatted(order.getId()),
                new OrderDto(order, orderReqBody.orders)
        );

        return new ResponseEntity<>(rsData, HttpStatus.CREATED);
    }


    /**
     * email에 해당하는 주문 목록 조회 메서드
     *
     * @param email 조회할 email
     * @return 주문 및 메뉴 정보를 포함한 OrderMenuWithOrderDto
     * @author shbaek
     */
    @GetMapping
    @Operation(summary = "email로 주문 조회")
    public List<OrderMenuWithOrderDto> getOrdersByEmail(@RequestParam String email) {
        return orderMenuService.getOrdersByEmail(email);
    }
}
