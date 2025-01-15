package com.ll.coffee.OrderMenu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class OrderMenuDto {

    private Integer orderId;
    private List<MenuDataDto> menuData;
    private LocalDateTime orderTime;
    private boolean isAfter2pm;
    private int totalPrice;

}
