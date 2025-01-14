package com.ll.coffee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * 나중에 이름 변경
 *
 * @author shjung
 * @since 25. 1. 14.
 */
@Controller
public class AdminController {

    @RequestMapping("/order-menu")
    public String orderMenu() {
        // 나중에 맞는 템플릿 이름으로 변경
        return "home";
    }
}
