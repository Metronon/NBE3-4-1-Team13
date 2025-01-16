package com.ll.coffee.controller;

import com.ll.coffee.OrderMenu.OrderMenuDto;
import com.ll.coffee.repository.OrderRepository;
import com.ll.coffee.service.OrderMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author shjung
 * @since 25. 1. 13.
 */
@RequestMapping("/admin")
@RequiredArgsConstructor
@Controller
public class HomeController {
    private final OrderMenuService orderMenuService;
    private final OrderRepository orderRepository;

    @GetMapping("/")
    public String home(){
        return "login_form";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/list")
    public String list(Model model,
                       @RequestParam(value = "page", defaultValue = "0")int page,
                       @RequestParam(value = "kw",defaultValue = "")String kw){
        PageRequest pageable = PageRequest.of(page,10, Sort.by("orderTime").descending());
        Page<OrderMenuDto> paging = orderMenuService.getOrderList(pageable,kw);

        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        return "home";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/detail/{email}")
    public String detail(Model model,
                      ){

        return "order_detail";
    }
}
