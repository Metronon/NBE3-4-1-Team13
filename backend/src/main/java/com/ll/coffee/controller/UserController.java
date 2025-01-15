package com.ll.coffee.controller;

import com.ll.coffee.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author shjung
 * @since 25. 1. 14.
 */
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @RequestMapping("/login")
    public String login() {
        return "login_form";
    }
}
