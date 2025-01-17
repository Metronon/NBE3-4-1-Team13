package com.ll.coffee.controller;
//
//import com.ll.coffee.OrderMenu.OrderMenuDto;
//import com.ll.coffee.repository.OrderRepository;
//import com.ll.coffee.service.OrderMenuService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//

import com.ll.coffee.OrderMenu.OrderMenuWithOrderDto;
import com.ll.coffee.menu.Menu;
import com.ll.coffee.repository.OrderRepository;
import com.ll.coffee.service.MenuService;
import com.ll.coffee.service.OrderMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

///**
// * @author shjung
// * @since 25. 1. 13.
// */
@RequiredArgsConstructor
@RestController
public class HomeController {
    private final OrderMenuService orderMenuService;
    private final OrderRepository orderRepository;
    private final MenuService menuService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/list")
    public String list(Model model,
                       @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String kw) {
        PageRequest pageable = PageRequest.of(page, 10, Sort.by("createdAt").descending());  // orderTime을 createdAt으로 변경
        Page<OrderMenuWithOrderDto> paging = orderMenuService.getOrderList(pageable, kw);

        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        return "home";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long id) {
        OrderMenuWithOrderDto orderMenuWithOrderDto = orderMenuService.getOrderById(id);
        model.addAttribute("orderMenuWithOrderDto", orderMenuWithOrderDto);
        return "/order_detail";
    }

    //메뉴 수정(관리자)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/menu/{id}/modify")
    public String showModifyPage(@PathVariable Long id, Model model) {

        Menu menu = menuService.getMenuById(id);
        model.addAttribute("menu", menu);
        return "menu_modify";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/menu/{id}/edit")
    public String modify(@PathVariable Long id, @ModelAttribute Menu menu){
        menuService.updateMenu(id,menu);
        return "redirect:/menu/list";
    }


}

