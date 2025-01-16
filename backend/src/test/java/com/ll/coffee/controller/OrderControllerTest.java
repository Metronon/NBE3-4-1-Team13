package com.ll.coffee.controller;

import com.ll.coffee.order.Order;
import com.ll.coffee.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author shbaek
 * @since 25. 1. 15
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OrderControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private OrderService orderService;

    @Test
    @DisplayName("주문 생성")
    void t1() throws Exception {
        ResultActions resultActions = mvc
                .perform(
                        post("/order")
                                .content("""
                                        {
                                            "email": "test@test.com",
                                            "address": "asd",
                                            "postalCode": 12345,
                                            "orders": {
                                                "1": 2
                                            }
                                        }
                                        """)
                                .contentType(
                                        new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)
                                )
                )
                .andDo(print());

        Order order = orderService.findLatest().get();

        resultActions
                .andExpect(handler().handlerType(OrderController.class))
                .andExpect(handler().methodName("create"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.resultCode").value("201-1"))
                .andExpect(jsonPath("$.msg").value("%d번 주문이 완료되었습니다.".formatted(order.getId())))
                .andExpect(jsonPath("$.data.id").value(order.getId()))
                .andExpect(jsonPath("$.data.email").value(order.getEmail()))
                .andExpect(jsonPath("$.data.address").value(order.getAddress()))
                .andExpect(jsonPath("$.data.postalCode").value(order.getPostalCode()))
        ;
    }

    @Test
    @DisplayName("email로 주문 조회")
    void t2() throws Exception {
        String email = "test@test.com";

        Order testOrder = orderService.save(email, "주소", 12345, Map.of(1L, 2, 2L, 1));

        ResultActions resultActions = mvc
                .perform(
                        get("/order")
                                .param("email", email)
                )
                .andDo(print());

        resultActions
                .andExpect(handler().handlerType(OrderController.class))
                .andExpect(handler().methodName("getOrdersByEmail"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[-1].email").value(email))
                .andExpect(jsonPath("$[-1].address").value("주소"))
                .andExpect(jsonPath("$[-1].postalCode").value(12345))
        ;
    }
}
