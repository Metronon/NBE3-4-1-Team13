package com.ll.coffee.controller;

import com.ll.coffee.menu.Menu;
import com.ll.coffee.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    //메뉴 추가
    @PostMapping
    public Menu addMenu(@RequestBody Menu menu) {
        return menuService.addMenu(menu);
    }

    //메뉴 수정
    @PutMapping("/{menu_id}")
    public Menu updateMenu(@PathVariable Long menu_id, @RequestBody Menu menu) {
        return menuService.updateMenu(menu_id, menu);
    }

    //메뉴 삭제
    @DeleteMapping("/{menu_id}")
    public void deleteMenu(@PathVariable Long menu_id) {
        menuService.deleteMenu(menu_id);
    }


    @GetMapping("/{menu_id}")
    public Menu getMenuById(@PathVariable Long menu_id) {
        return menuService.getMenuById(menu_id);
    }

    //메뉴 조회
    @GetMapping
    public List<Menu> getAllMenus() {
        return menuService.getAllMenus();
    }
}