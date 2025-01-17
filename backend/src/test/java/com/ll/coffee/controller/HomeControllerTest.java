package com.ll.coffee.controller;

import com.ll.coffee.menu.Menu;
import com.ll.coffee.repository.MenuRepository;
import com.ll.coffee.service.MenuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@SpringBootTest
public class HomeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuRepository menuRepository;

    private List<Menu> menuList;



    @BeforeEach
    public void setup() {

        Menu menu1 = Menu.builder().id(1L).name("Americano").price(4000).type("Bitter").build();
        Menu menu2 = Menu.builder().id(2L).name("Cappuccino").price(5000).type("Sweet").build();
        Menu menu3 = Menu.builder().id(3L).name("Espresso").price(1500).type("Coffee").build();
        Menu menu4 = Menu.builder().id(4L).name("latte").price(4500).type("Nutty").build();

        menuList = Arrays.asList(menu1, menu2, menu3, menu4);
        menuRepository.saveAll(menuList);

    }

    @Test
    @DisplayName("관리자 - 메뉴 수정 페이지 조회")
    @Transactional
    void t1() throws Exception{

        Menu menu = menuRepository.findAll().get(0);

        given(menuService.getMenuById(1L)).willReturn(menu);

        ResultActions resultActions = mvc.perform(get("/menu/1/modify")).andDo(print());

        resultActions
                .andExpect(handler().handlerType(HomeController.class))
                .andExpect(status().isOk())
                .andExpect(view().name("menu_modify"))
                .andExpect(model().attribute("menu", menu));
    }

//    @Test
//    @DisplayName("관리자 - 메뉴 수정")
//    void t2() throws Exception{
//
//
//
//
//
//    }
}
