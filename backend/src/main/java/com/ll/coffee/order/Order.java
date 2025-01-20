package com.ll.coffee.order;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 주문 테이블
 *
 * @author shbaek
 * @since 25. 1. 15
 */
@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Table(name="orders")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    private String email;

    private String address;

    private String postalCode;

    @CreatedDate
    private LocalDateTime createdAt;

}
