package com.ll.coffee.service;

import com.ll.coffee.OrderMenu.MenuDataDto;
import com.ll.coffee.OrderMenu.OrderMenu;
import com.ll.coffee.OrderMenu.OrderMenuDto;
import com.ll.coffee.order.Order;
import com.ll.coffee.repository.MenuRepository;
import com.ll.coffee.repository.OrderMenuRepository;
import com.ll.coffee.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

/**
 * @author shbaek
 * @since 25. 1. 15
 */
@Service
@AllArgsConstructor
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

            Menu menu = menuRepository.findById(orderMenu.getMenuId()).orElseThrow(()-> new RuntimeException("Menu not found"));

            int itemPrice = menu.getPrice() * orderMenu.getCount();
            totalPrice +=itemPrice;

            OrderMenuDto orderMenuDto = new OrderMenuDto(orderMenu.getMenuId(), orderMenu.getCount()   , menu.getName(), itemPrice );

        }

    }

    /**
     * email로 주문 조회
     * @author shbaek
     * @since 25. 1. 15
     */
    public List<OrderMenuDto> findByEmail(String email) {
        List<Order> orders = orderRepository.findByEmail(email);

        List<OrderMenuDto> orderMenuDtos = new ArrayList<>();

        for(Order order : orders){
            List<MenuDataDto> menuDataList = new ArrayList<>();
            int totalPrice = 0;

            // 해당 주문의 모든 주문 메뉴 정보 조회
            List<OrderMenu> orderMenus = orderMenuRepository.findByOrderId(order.getId());

            for (OrderMenu orderMenu : orderMenus) {
                // 메뉴 ID로 메뉴 정보 조회
                Optional<com.ll.coffee.menu.Menu> menuOptional = menuRepository.findById(orderMenu.getMenuId());
                if (menuOptional.isPresent()) {
                    com.ll.coffee.menu.Menu menu = menuOptional.get();


                    // 메뉴 데이터 DTO로 변환
                    MenuDataDto menuDataDto = MenuDataDto.builder()
                            .menuId(menu.getId())
                            .menuName(menu.getName())
                            .menuPrice(menu.getPrice())
                            .count(orderMenu.getCount())
                            .build();


                    menuDataList.add(menuDataDto);

                    // 총 가격 계산
                    totalPrice += menu.getPrice() * orderMenu.getCount();
                }
            }

            // 4. 주문 시간과 오후 2시 이후 여부 계산
            LocalDateTime orderTime = order.getCreatedAt();
            boolean isAfter2pm = orderTime.getHour() >= 14;

            // 5. OrderMenuDto 생성
            OrderMenuDto orderMenuDto = OrderMenuDto.builder()
                    .orderId(order.getId())
                    .menuData(menuDataList)
                    .orderTime(orderTime)
                    .isAfter2pm(isAfter2pm)
                    .totalPrice(totalPrice)
                    .build();

            orderMenuDtos.add(orderMenuDto);
        }

        return orderMenuDtos;
    }
}
