package com.ll.coffee.member;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

/**
 * @author shjung
 * @since 25. 1. 13.
 */
@Getter
@Setter
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

    @Email
    @Column(unique = true)
    private String email;
}
