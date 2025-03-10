package com.ll.coffee.OrderMenu;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.*;

/**
 * 주문한 메뉴 테이블
 * @author seeyeon
 * @since 25. 1. 15
 */

@Entity
@Getter
@Setter
@IdClass(OrderMenuId.class)
@Table(name="OrderMenu")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderMenu {

    @Id
    private Long menuId; // 외래 키로 참조되는 menu 테이블의 id

    @Id
    private Long orderId; // 외래 키로 참조되는 order 테이블의 id

    private int count; // 주문 수량
}
