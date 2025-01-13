package com.ll.coffee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author shjung
 * @since 25. 1. 13.
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String home(){
        return "home";
    }
}
