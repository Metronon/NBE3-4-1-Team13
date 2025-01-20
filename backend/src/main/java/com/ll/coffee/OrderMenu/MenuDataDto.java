package com.ll.coffee.OrderMenu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 메뉴 정보 DTD
 * @author seeyeon
 * @since 25. 1. 15
 */

@AllArgsConstructor
@Getter
@Builder
public class MenuDataDto {

    private Long menuId;
    private String menuName;
    private int menuPrice;
    private String menuType;
    private int menuCount;
}
