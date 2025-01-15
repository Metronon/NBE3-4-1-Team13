package com.ll.coffee.order;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 *
 * 주문 메뉴 복합키
 *
 * @author shbaek
 * @since 25. 1. 15
 */
@Embeddable
@Getter
@Setter
public class OrderMenuId implements Serializable {
    private Integer menuId;
    private Integer orderId;
}
