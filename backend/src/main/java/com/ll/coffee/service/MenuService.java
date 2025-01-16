package com.ll.coffee.service;


import com.ll.coffee.menu.Menu;
import com.ll.coffee.menu.MenuDto;
import com.ll.coffee.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    //메뉴생성
    public MenuDto addMenu(Menu menu) {
        Menu savedMenu = menuRepository.save(menu);
        return new MenuDto(savedMenu.getId(), savedMenu.getName(), savedMenu.getType(), savedMenu.getPrice());
    }

    public MenuDto updateMenu(Long id, Menu updateMenu) {
        //1.메뉴객체를 찾아서
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 메뉴가 존재하지 않습니다. id=" + id));
        //2.객체에 새로운 이름,타입,가격을 저장 후
        menu.setName(updateMenu.getName());
        menu.setType(updateMenu.getType());
        menu.setPrice(updateMenu.getPrice());
        //3.db에 새로운 메뉴객체를 저장
        Menu savedMenu = menuRepository.save(menu);
        //Dto로 변환하여 리턴
        return new MenuDto(
                savedMenu.getId(),
                savedMenu.getName(),
                savedMenu.getType(),
                savedMenu.getPrice()
        );
    }

    public void deleteMenuById(Long id) {
        if (menuRepository.existsById(id)) {
            menuRepository.deleteById(id);
        } else {
            throw new RuntimeException("Menu not found with id " + id);
        }
    }

    public Menu getMenuById(Long id) {
        return menuRepository.findById(id).orElseThrow(() -> new RuntimeException("Menu not found with id " + id));
    }

    public List<MenuDto> getAllMenus() {
        List<Menu> menus = menuRepository.findAll();
        return menus.stream()
                .map(menu -> new MenuDto(
                        menu.getId(),
                        menu.getName(),
                        menu.getType(),
                        menu.getPrice()))
                .collect(Collectors.toList());
    }
}