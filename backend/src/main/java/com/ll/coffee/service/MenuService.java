package com.ll.coffee.service;


import com.ll.coffee.menu.Menu;
import com.ll.coffee.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    public Menu addMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    public Menu updateMenu(Long id, Menu menu) {
        Optional<Menu> existingMenu = menuRepository.findById(id);
        if (existingMenu.isPresent()) {
            Menu updatedMenu = existingMenu.get();
            updatedMenu.setName(menu.getName());
            updatedMenu.setType(menu.getType());
            updatedMenu.setPrice(menu.getPrice());
            return menuRepository.save(updatedMenu);
        } else {
            throw new RuntimeException("Menu not found with id " + id);
        }
    }

    public void deleteMenu(Long id) {
        if (menuRepository.existsById(id)) {
            menuRepository.deleteById(id);
        } else {
            throw new RuntimeException("Menu not found with id " + id);
        }
    }

    public Menu getMenuById(Long id) {
        return menuRepository.findById(id).orElseThrow(() -> new RuntimeException("Menu not found with id " + id));
    }

    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }
}