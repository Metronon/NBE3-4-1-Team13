package com.ll.coffee.controller;

import com.ll.coffee.OrderMenu.MenuDataDto;
import com.ll.coffee.OrderMenu.OrderMenu;
import com.ll.coffee.OrderMenu.OrderMenuDto;
import com.ll.coffee.menu.Menu;
import com.ll.coffee.order.Order;
import com.ll.coffee.repository.MenuRepository;
import com.ll.coffee.repository.OrderMenuRepository;
import com.ll.coffee.repository.OrderRepository;
import com.ll.coffee.service.OrderMenuService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AdminControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private OrderMenuService orderMenuService;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMenuRepository orderMenuRepository;

    @Test
    @DisplayName("관리자 - 전체 주문 조회 : 주문1개 메뉴1개")
    void t1() throws Exception{

        //임시 데이터 생성
        Menu menu = Menu.builder()
                .name("Americano")
                .type("sweet")
                .price(4000)
                .build();

        menuRepository.save(menu);

        Order order = Order.builder()
                .email("test@example.com")
                .address("TestAddress")
                .postalCode(12345)
                .createdAt(LocalDateTime.now())
                .build();

        orderRepository.save(order);

        OrderMenu orderMenu = OrderMenu.builder()
                .orderId(order.getId())
                .menuId(menu.getId())
                .count(2)
                .build();

        orderMenuRepository.save(orderMenu);

        ResultActions resultActions = mvc
                .perform(
                        get("/order-menu")
                ).andDo(print());

        //서비스 로직 test
        List<OrderMenuDto> orderMenuDtos = orderMenuService.getAllOrders();

        resultActions
                .andExpect(handler().handlerType(AdminController.class))
                .andExpect(handler().methodName("getAllOrders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value("200-1"))
                .andExpect(jsonPath("$.msg").value("전체 주문 조회 성공"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(orderMenuDtos.size()));

        resultActions
                .andExpect(jsonPath("$.data[0].orderId").value(order.getId()));

        //MenuData 검증
        List<MenuDataDto> menuDataDtoList = orderMenuDtos.get(0).getMenuData();

        resultActions
                .andExpect(jsonPath("$.data[0].menuData.length()").value(menuDataDtoList.size()))
                .andExpect(jsonPath("$.data[0].menuData[0].menuId").value(menuDataDtoList.get(0).getMenuId()))
                .andExpect(jsonPath("$.data[0].menuData[0].menuName").value(menuDataDtoList.get(0).getMenuName()))
                .andExpect(jsonPath("$.data[0].menuData[0].menuPrice").value(menuDataDtoList.get(0).getMenuPrice()))


                .andExpect(jsonPath("$.data[0].orderTime").value(Matchers.startsWith(orderMenuDtos.get(0).getOrderTime().toString().substring(0,25))))
                .andExpect(jsonPath("$.data[0].after2pm").value(orderMenuDtos.get(0).isAfter2pm()))
                .andExpect(jsonPath("$.data[0].totalPrice").value(orderMenuDtos.get(0).getTotalPrice()));

    }

    @Test
    @DisplayName("관리자 - 전체 주문 조회 : 주문이 없는 경우")
    void t2() throws Exception{
        ResultActions resultactions =mvc.perform(get("/order-menu")).andDo(print());

        resultactions
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.resultCode").value("204-1"))
                .andExpect(jsonPath("$.msg").value("전체 주문 조회 결과가 없습니다."));

    }

    @Test
    @DisplayName("관리자 - 전체 주문 조회 : 특정 주문 시간이 오후 2시 이후 인지 확인")
    void t3() throws Exception{

        Order order = orderRepository.save(Order.builder()
                   .createdAt(LocalDateTime.of(2025,1,16,15,0)).build());


        Menu menu = Menu.builder()
                .name("Americano")
                .type("sweet")
                .price(4000)
                .build();

        menuRepository.save(menu);

        OrderMenu orderMenu = OrderMenu.builder()
                .orderId(order.getId())
                .menuId(menu.getId())
                .count(5)
                .build();

        ResultActions resultActions = mvc.perform(get("/order-menu")).andDo(print());

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].after2pm").value(true));
    }

    @Test
    @DisplayName("관리자 - 전체 주문 조회 : 대량 데이터")
    void t4() throws Exception {

        Menu menu = Menu.builder()
                .name("Americano")
                .type("sweet")
                .price(4000)
                .build();

        menuRepository.save(menu);

        for(int i=1; i<=100; i++){
            Order order = Order.builder()
                    .email("test@example.com")
                    .address("TestAddress")
                    .postalCode(12345)
                    .createdAt(LocalDateTime.now())
                    .build();

            orderRepository.save(order);

            OrderMenu orderMenu = OrderMenu.builder()
                    .orderId(order.getId())
                    .menuId(menu.getId())
                    .count(i)
                    .build();

            orderMenuRepository.save(orderMenu);
        }

        ResultActions resultActions= mvc.perform(get("/order-menu")).andDo(print());

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(100));

    }

    @Test
    @DisplayName("관리자 - 전체 주문 조회 : 주문1 메뉴 여러개")
    void t5() throws Exception {

        Order order = Order.builder()
                .email("test@example.com")
                .address("TestAddress")
                .postalCode(12345)
                .createdAt(LocalDateTime.now())
                .build();

        orderRepository.save(order);

        //메뉴 목록
        List<Menu> menulist = Arrays.asList(
                Menu.builder().name("Americano").price(4000).type("sweet").build(),
                Menu.builder().name("Espresso").price(1500).type("bitter").build(),
                Menu.builder().name("Cappuccino").price(5000).type("sweet").build(),
                Menu.builder().name("latte").price(4500).type("nutty").build()
        );

        menuRepository.saveAll(menulist);

        List<OrderMenu> orderMenuList = Arrays.asList(
                OrderMenu.builder().orderId(order.getId()).menuId(menulist.get(0).getId()).count(1).build(),
                OrderMenu.builder().orderId(order.getId()).menuId(menulist.get(1).getId()).count(2).build(),
                OrderMenu.builder().orderId(order.getId()).menuId(menulist.get(2).getId()).count(1).build(),
                OrderMenu.builder().orderId(order.getId()).menuId(menulist.get(3).getId()).count(3).build()
        );

        orderMenuRepository.saveAll(orderMenuList);

        ResultActions resultActions = mvc.perform(get("/order-menu")).andDo(print());

        int expectedTotalPrice = (4000*1) + (1500*2) + (5000*1) + (4500*3);


        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(1))
                .andExpect(jsonPath("$.data[0].orderId").value(order.getId()))
                .andExpect(jsonPath("$.data[0].menuData.length()").value(4))
                .andExpect(jsonPath("$.data[0].menuData[0].menuName").value("Americano"))
                .andExpect(jsonPath("$.data[0].menuData[0].menuPrice").value(4000))
                .andExpect(jsonPath("$.data[0].menuData[0].menuCount").value(1))
                .andExpect(jsonPath("$.data[0].menuData[1].menuName").value("Espresso"))
                .andExpect(jsonPath("$.data[0].menuData[1].menuPrice").value(1500))
                .andExpect(jsonPath("$.data[0].menuData[1].menuCount").value(2))
                .andExpect(jsonPath("$.data[0].menuData[2].menuName").value("Cappuccino"))
                .andExpect(jsonPath("$.data[0].menuData[2].menuPrice").value(5000))
                .andExpect(jsonPath("$.data[0].menuData[2].menuCount").value(1))
                .andExpect(jsonPath("$.data[0].menuData[3].menuName").value("latte"))
                .andExpect(jsonPath("$.data[0].menuData[3].menuPrice").value(4500))
                .andExpect(jsonPath("$.data[0].menuData[3].menuCount").value(3))
                .andExpect(jsonPath("$.data[0].totalPrice").value(expectedTotalPrice));

    }

}
