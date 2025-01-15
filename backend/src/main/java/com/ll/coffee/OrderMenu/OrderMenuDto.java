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
public class OrderMenuDto {

    private Long orderId;
    private List<MenuDataDto> menuData;
    private LocalDateTime orderTime;
    private boolean isAfter2pm;
    private int totalPrice;

}
