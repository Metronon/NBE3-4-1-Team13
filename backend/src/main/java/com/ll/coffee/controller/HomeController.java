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


@RequiredArgsConstructor
@Controller
public class HomeController {
    private final OrderMenuService orderMenuService;
    private final MenuService menuService;

    //주문 목록을 관리자 권한으로 페이징하여서 조회
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/list")
    public String list(Model model,
                       @RequestParam(value = "page", defaultValue = "0") int page, //페이지 번호(defualt: 0)
                       @RequestParam(value = "kw", defaultValue = "") String kw) {  //검색 키워드(default: "")
        //한 페이지당 10개의 항목을 생성일 기준 최신(내림차순)으로 정렬
        PageRequest pageable = PageRequest.of(page, 10, Sort.by("createdAt").descending());
        Page<OrderMenuWithOrderDto> paging = orderMenuService.getOrderList(pageable, kw);
        //페이징된 주문 목록 데이터와 검색 키워드 전달
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        return "home"; //home.html 뷰 반환
    }

    //관리자 권한으로 상세 주문 정보 조회
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long id) {
        //id에 해당하는 주문 정보를 조회
        OrderMenuWithOrderDto orderMenuWithOrderDto = orderMenuService.getOrderById(id);
        //조회된 상세 주문 내역을 뷰에 전달
        model.addAttribute("orderMenuWithOrderDto", orderMenuWithOrderDto);
        return "/order_detail"; //order_detail.html 뷰 반환
    }

    //메뉴 전체 목록 조회
    @GetMapping("/menu/manage")
    public String menu_list(Model model){
        List<MenuDto> menus = menuService.getAllMenus();
        //전체 메뉴 목록 뷰에 전달
        model.addAttribute("menus",menus);
        return "menu_list";//menu_list.html 뷰 반환
    }

    //관리자 권한으로 메뉴 추가 폼 표시
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/menu/add")
    public String add(Model model){
        model.addAttribute("menuDto",new MenuDto());
        return "menu_add";//menu_add 뷰 반환
    }

    //관리자 권한으로 메뉴 추가 처리
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/menu/add")
    public String add(@Valid @ModelAttribute MenuDto menu, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "menu_add";
        }//유효성 검사 실패시 폼 다시 표시
        menuService.addMenu(menu);
        return "redirect:/menu/manage";//메뉴 목록 페이지로 리다이렉트
    }

    //관리자 권환으로 메뉴 삭제
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/menu/{id}/delete")
    public String delete(@PathVariable Long id){
        menuService.deleteMenuById(id);
        return "redirect:/menu/manage";//메뉴 목록 페이지로 리다이렉트
    }

    //메뉴 수정  폼 처리(관리자)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/menu/{id}/edit")
    public String showModifyPage(@PathVariable Long id, Model model, MenuDto menuDto) {

        Menu menu = menuService.getMenuById(id);
        model.addAttribute("menu", menu);
        return "menu_modify";//menu_modify.html 뷰 반환
    }

    //관리자 권한으로 메뉴 수정 처리
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/menu/{id}/edit")
    public String modify(@PathVariable Long id, @ModelAttribute MenuDto menu){
        menuService.updateMenu(id,menu);
        return "redirect:/menu/manage";//메뉴 목록 페이지로 리다이렉트
    }

    //루트 경로로 접근 시 로그인 페이지로 리다이렉트
    @GetMapping("/")
    public String home(Model model){
        return "redirect:/login";
    }

}

