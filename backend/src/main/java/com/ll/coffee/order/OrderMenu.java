package com.ll.coffee.order;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * 주문 메뉴 테이블
 *
 * @author shbaek
 * @since 25. 1. 15
 */
@Entity
@Getter
@Setter
public class OrderMenu {
    @EmbeddedId
    private OrderMenuId id;

    private int count;
}
