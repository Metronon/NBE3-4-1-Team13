package com.ll.coffee.OrderMenu;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/**
 * OrderMenu 테이블 복합키 정의
 * @author seeyeon
 * @since 25. 1. 15
 */

@AllArgsConstructor
@NoArgsConstructor
public class OrderMenuId implements Serializable {

    /*복합키를 사용하는 이유
    * 하나의 주문(order_id)내에 동일한 메뉴(menu_id)가 중복으로 들어가는 것을 방지하고
    * 같은 메뉴 추가 시, 기존 count 값을 합산해 업데이트를 진행.
    * */


    private Long menuId;
    private Long orderId;

    // equals() 메서드: 두 복합키 객체가 동일한지 비교
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderMenuId that = (OrderMenuId) o;
        return Objects.equals(menuId, that.menuId) && Objects.equals(orderId, that.orderId);
    }

    // hashCode() 메서드: 복합키 객체의 해시코드를 계산
    @Override
    public int hashCode() {
        return Objects.hash(menuId, orderId);
    }

}
