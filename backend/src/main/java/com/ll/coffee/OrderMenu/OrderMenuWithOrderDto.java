package com.ll.coffee.OrderMenu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 주문자 정보와 주문목록 dto
 *
 * @author shBaek
 * @since 25. 1. 16
 */
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderMenuWithOrderDto {
    private Long orderId;
    private String email;
    private String address;
    private String postalCode;
    private List<MenuDataDto> menuData;
    private LocalDateTime orderTime;
    private boolean isAfter2pm;
    private int totalPrice;
}
