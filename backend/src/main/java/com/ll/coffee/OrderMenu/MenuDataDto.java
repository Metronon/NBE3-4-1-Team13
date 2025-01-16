package com.ll.coffee.OrderMenu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class MenuDataDto {

    private Long menuId;
    private String menuName;
    private int menuPrice;
    private int count;
}
