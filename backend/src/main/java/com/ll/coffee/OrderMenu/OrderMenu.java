package com.ll.coffee.OrderMenu;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@IdClass(OrderMenuId.class)
public class OrderMenu {

    @Id
    private Integer menuId; // 외래 키로 참조되는 menu 테이블의 id

    @Id
    private Integer orderId; // 외래 키로 참조되는 order 테이블의 id

    private Integer count; // 주문 수량
}
