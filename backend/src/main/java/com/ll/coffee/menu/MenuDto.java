package com.ll.coffee.menu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class MenuDto {
    private Long id;
    private String name;
    private String type;
    private Integer price;

    public MenuDto() {

    }
}