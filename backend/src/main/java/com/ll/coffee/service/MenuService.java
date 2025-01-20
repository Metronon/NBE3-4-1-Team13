package com.ll.coffee.service;


import com.ll.coffee.menu.Menu;
import com.ll.coffee.menu.MenuDto;
import com.ll.coffee.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    // MenuDto 객체를 기반으로 새로운 메뉴를 생성하여 DB에 저장하고
    // 저장된 메뉴를 MenuDto로 변환하여 반환
    public MenuDto addMenu(MenuDto menu) {
        Menu menuEntity = new Menu();
        menuEntity.setName(menu.getName());
        menuEntity.setPrice(menu.getPrice());
        menuEntity.setType(menu.getType());
        Menu savedMenu = menuRepository.save(menuEntity);
        return new MenuDto(savedMenu.getId(),savedMenu.getName(),savedMenu.getType(),savedMenu.getPrice());
    }

    // 주어진 ID에 해당하는 메뉴객체를 주어진 updateMenu를 기반으로 내용을 변경하고
    // 변경된 메뉴객체를 MenuDto로 변환하여 반환
    public MenuDto updateMenu(Long id, MenuDto updateMenu) {
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
        return new MenuDto(savedMenu.getId(),savedMenu.getName(),savedMenu.getType(),savedMenu.getPrice()
        );
    }

    // 주어진 ID에 해당하는 메뉴를 조회하고 MenuDto로 변환하여 반환
    public MenuDto getMenuById(Long id) {
        // 메뉴 객체를 찾아서
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu not found with id " + id));
        // Menu 객체를 MenuDto로 변환하여 반환
        return new MenuDto(menu.getId(), menu.getName(), menu.getType(), menu.getPrice());
    }

    // DB에 저장된 모든 메뉴 엔티티를 조회하고, MenuDto 리스트로 변환하여 반환
    public List<MenuDto> getAllMenus() {
        List<Menu> menus = menuRepository.findAll();
        return menus.stream()
                .map(menu -> new MenuDto(menu.getId(), menu.getName(), menu.getType(), menu.getPrice()
                ))
                .collect(Collectors.toList());
    }
}