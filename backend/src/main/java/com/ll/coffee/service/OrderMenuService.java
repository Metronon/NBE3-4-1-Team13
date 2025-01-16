package com.ll.coffee.service;

import com.ll.coffee.OrderMenu.MenuDataDto;
import com.ll.coffee.OrderMenu.OrderMenu;
import com.ll.coffee.OrderMenu.OrderMenuDto;
import com.ll.coffee.OrderMenu.OrderMenuWithOrderDto;
import com.ll.coffee.menu.Menu;
import com.ll.coffee.order.Order;
import com.ll.coffee.repository.MenuRepository;
import com.ll.coffee.repository.OrderMenuRepository;
import com.ll.coffee.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    //관리자가 전체 주문을 조회하는 경우,
    public List<OrderMenuDto> getAllOrders() {

        //모든 주문 조회하기
        List<Order> orders = orderRepository.findAll();

        //주문 DTO 리스트 생성
        List<OrderMenuDto> orderMenuDtos = new ArrayList<>();

        for(Order order : orders){
            List<MenuDataDto> menuDataList = new ArrayList<>();
            int totalPrice = 0;

            // 현재 주문에 해당하는 메뉴 항목들을 조회
            List<OrderMenu> orderMenus = orderMenuRepository.findByOrderId(order.getId());

            for (OrderMenu orderMenu : orderMenus) {
                // 메뉴 ID로 메뉴 정보 조회
                Optional<Menu> menuOptional = menuRepository.findById(orderMenu.getMenuId());
                if (menuOptional.isPresent()) {
                    Menu menu = menuOptional.get();

                    // 메뉴 데이터 DTO로 변환
                    MenuDataDto menuDataDto = MenuDataDto.builder()
                            .menuId(menu.getId())
                            .menuName(menu.getName())
                            .menuPrice(menu.getPrice())
                            .menuCount(orderMenu.getCount())
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

    /**
     * email로 주문 조회
     * @author shbaek
     * @since 25. 1. 15
     */
    public List<OrderMenuWithOrderDto> getOrdersByEmail(String email) {
        List<Order> orders = orderRepository.findByEmail(email);

        List<OrderMenuWithOrderDto> orderMenuWithOrderDtos = new ArrayList<>();

        for(Order order : orders){
            List<MenuDataDto> menuDataList = new ArrayList<>();
            int totalPrice = 0;

            // 해당 주문의 모든 주문 메뉴 정보 조회
            List<OrderMenu> orderMenus = orderMenuRepository.findByOrderId(order.getId());

            for (OrderMenu orderMenu : orderMenus) {
                // 메뉴 ID로 메뉴 정보 조회
                Optional<Menu> menuOptional = menuRepository.findById(orderMenu.getMenuId());
                if (menuOptional.isPresent()) {
                    Menu menu = menuOptional.get();


                    // 메뉴 데이터 DTO로 변환
                    MenuDataDto menuDataDto = MenuDataDto.builder()
                            .menuId(menu.getId())
                            .menuName(menu.getName())
                            .menuPrice(menu.getPrice())
                            .menuCount(orderMenu.getCount())
                            .build();


                    menuDataList.add(menuDataDto);

                    // 총 가격 계산
                    totalPrice += menu.getPrice() * orderMenu.getCount();
                }
            }

            // 4. 주문 시간과 오후 2시 이후 여부 계산
            LocalDateTime orderTime = order.getCreatedAt();
            boolean isAfter2pm = orderTime.getHour() >= 14;

            // 5. OrderMenuWithOrderDto 생성
            OrderMenuWithOrderDto orderMenuWithOrderDto = OrderMenuWithOrderDto.builder()
                    .orderId(order.getId())
                    .email(order.getEmail())
                    .address(order.getAddress())
                    .postalCode(order.getPostalCode())
                    .menuData(menuDataList)
                    .orderTime(orderTime)
                    .isAfter2pm(isAfter2pm)
                    .totalPrice(totalPrice)
                    .build();

            orderMenuWithOrderDtos.add(orderMenuWithOrderDto);
        }

        return orderMenuWithOrderDtos;
    }


    //전체 주문 조회
    public Page<OrderMenuWithOrderDto> getOrderList(Pageable pageable, String kw){
        Page<Order> orderPage = kw.trim().isEmpty() ?
                orderRepository.findAll(pageable) :
                orderRepository.findByEmailContaining(kw, pageable);

        List<OrderMenuWithOrderDto> orderMenuWithOrderDtos = new ArrayList<>();
        for (Order order : orderPage.getContent()) {
            List<MenuDataDto> menuDataList = new ArrayList<>();
            int totalPrice = 0;

            List<OrderMenu> orderMenus = orderMenuRepository.findByOrderId(order.getId());

            for(OrderMenu orderMenu : orderMenus){
                Optional<Menu> menuOptional = menuRepository.findById(orderMenu.getMenuId());
                if(menuOptional.isPresent()){
                    Menu menu = menuOptional.get();

                    MenuDataDto menuDataDto = MenuDataDto.builder()
                            .menuId(menu.getId())
                            .menuName(menu.getName())
                            .menuPrice(menu.getPrice())
                            .menuCount(orderMenu.getCount())
                            .build();

                    menuDataList.add(menuDataDto);
                    totalPrice += menu.getPrice() * orderMenu.getCount();
                }
            }

            LocalDateTime orderTime = order.getCreatedAt();
            boolean isAfter2pm = orderTime.getHour() >= 14;

            OrderMenuWithOrderDto orderMenuWithOrderDto = OrderMenuWithOrderDto.builder()
                    .orderId(order.getId())
                    .email(order.getEmail())
                    .address(order.getAddress())
                    .postalCode(order.getPostalCode())
                    .menuData(menuDataList)
                    .orderTime(orderTime)
                    .isAfter2pm(isAfter2pm)
                    .totalPrice(totalPrice)
                    .build();

            orderMenuWithOrderDtos.add(orderMenuWithOrderDto);
        }
        return new PageImpl<>(orderMenuWithOrderDtos, pageable, orderPage.getTotalElements());
    }


    //상세 주문 출력
    public OrderMenuWithOrderDto getOrderById(Long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if(!orderOptional.isPresent()){
            throw new EntityNotFoundException("해당 주문을 찾을 수 없습니다.");
        }
        Order order = orderOptional.get();
        List<OrderMenu> orderMenus = orderMenuRepository.findByOrderId(order.getId());
        List<MenuDataDto> menuDataList = new ArrayList<>();
        int totalPrice = 0;
        for (OrderMenu orderMenu : orderMenus) {
            Optional<Menu> menuOptional = menuRepository.findById(orderMenu.getMenuId());

            if (menuOptional.isPresent()) {
                Menu menu = menuOptional.get();

                MenuDataDto menuDataDto = MenuDataDto.builder()
                        .menuId(menu.getId())
                        .menuName(menu.getName())
                        .menuPrice(menu.getPrice())
                        .menuCount(orderMenu.getCount())
                        .build();

                menuDataList.add(menuDataDto);

                totalPrice += menu.getPrice() * orderMenu.getCount();
            }
        }

        LocalDateTime orderTime = order.getCreatedAt();
        boolean isAfter2pm = orderTime.getHour() >= 14;

        return OrderMenuWithOrderDto.builder()
                .orderId(order.getId())
                .email(order.getEmail())
                .address(order.getAddress())
                .postalCode(order.getPostalCode())
                .menuData(menuDataList)
                .orderTime(orderTime)
                .isAfter2pm(isAfter2pm)
                .totalPrice(totalPrice)
                .build();
    }
}
