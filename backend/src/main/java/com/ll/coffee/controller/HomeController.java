package com.ll.coffee.controller;


import com.ll.coffee.OrderMenu.OrderMenuWithOrderDto;
import com.ll.coffee.menu.Menu;
import com.ll.coffee.menu.MenuDto;
import com.ll.coffee.repository.OrderRepository;
import com.ll.coffee.service.MenuService;
import com.ll.coffee.service.OrderMenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

///**
// * @author shjung
// * @since 25. 1. 13.
// */
@RequiredArgsConstructor
@Controller
public class HomeController {
    private final OrderMenuService orderMenuService;
    private final MenuService menuService;
    private final OrderRepository orderRepository;

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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/menu_manage")
    public String menu_list(Model model){
        List<MenuDto> menus = menuService.getAllMenus();
        System.out.println("메뉴 목록: " + menus);
        model.addAttribute("menus",menus);
        return "menu_list";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/menu/add")
    public String add(Model model){
        model.addAttribute("menuDto",new Menu());
        return "menu_modify";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/menu/add")
    public String add(@Valid @ModelAttribute Menu menu, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "menu_modify";
        }
        menuService.addMenu(menu);
        return "redirect:/menu_manage";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/menu/{id}/delete")
    public String delete(@PathVariable Long id){
        menuService.deleteMenuById(id);
        return "redirect:/menu_manage";
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

    @GetMapping("/")
    public String home(Model model){
        return "redirect:/login";
    }

}

