package com.ll.coffee.OrderMenu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 주문 최종 결과 DTD
 * @author seeyeon
 * @since 25. 1. 15
 */

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
