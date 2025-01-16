package com.ll.coffee.service;

import com.ll.coffee.OrderMenu.OrderMenu;
import com.ll.coffee.OrderMenu.OrderMenuDto;
import com.ll.coffee.repository.MenuRepository;
import com.ll.coffee.repository.OrderMenuRepository;
import com.ll.coffee.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shbaek
 * @since 25. 1. 15
 */
@Service
@RequiredArgsConstructor
public class OrderMenuService {
    private final OrderMenuRepository orderMenuRepository;
    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;

    public List<OrderMenuDto> getAllOrders(){

        List<OrderMenu> orderMenus = orderMenuRepository.findAll();

        //주문별로 메뉴 항목을 그룹화
        Map<Integer, List<OrderMenuDto>> orderMenuMap = new HashMap<>();
        int totalPrice = 0;

        for(OrderMenu orderMenu : orderMenus){

            /*Menu menu = menuRepository.findById(orderMenu.getMenuId()).orElseThrow(()-> new RuntimeException("Menu not found"));

            int itemPrice = menu.getPrice() * orderMenu.getCount();
            totalPrice +=itemPrice;

            OrderMenuDto orderMenuDto = new OrderMenuDto(orderMenu.getMenuId(), orderMenu.getCount()   , menu.getName(), itemPrice );*/

        }

        return null;
    }
}
