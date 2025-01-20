package com.ll.coffee.controller;

import com.ll.coffee.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * 유저 관련 컨트롤러
 *
 * @author shjung
 * @since 25. 1. 14.
 */
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     *
     * 유저 로그인 화면
     *
     * @return 유저 로그인 화면
     *
     * @author shjung
     * @since 25. 1. 20.
     */
    @RequestMapping("/login")
    public String login() {
        return "login_form";
    }
}
