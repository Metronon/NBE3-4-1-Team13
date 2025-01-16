package com.ll.coffee.OrderMenu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderMenuWithOrderDto {
    private Long orderId;
    private String email;
    private String address;
    private int postalCode;
    private List<MenuDataDto> menuData;
    private LocalDateTime orderTime;
    private boolean isAfter2pm;
    private int totalPrice;
}
