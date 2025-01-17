package com.ll.coffee.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import com.ll.coffee.menu.MenuDto;
import com.ll.coffee.menu.Menu;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsInAnyOrder;

@SpringBootTest
@AutoConfigureMockMvc
class MenuControllerTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser
    @DisplayName("메뉴 생성 테스트")
    void addMenu_test() throws Exception {
        // given
        Menu menu = new Menu();
        menu.setName("아메리카노1");
        menu.setType("커피");
        menu.setPrice(5500);

        String body = mapper.writeValueAsString(menu);

        // when & then
        mvc.perform(post("/menu")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())  // 테스트 결과를 자세히 출력
                .andExpect(status().isCreated())  // HTTP 상태코드 201 검증
                .andExpect(jsonPath("$.resultCode").value("201-1"))  // RsData의 resultCode 검증
                // .andExpect(jsonPath("$.msg").value("아메리카노 메뉴가 성공적으로 추가되었습니다."))  // RsData의 msg 검증
                .andExpect(jsonPath("$.data.name").value("아메리카노1"))  // 저장된 메뉴 이름 검증
                .andExpect(jsonPath("$.data.type").value("커피"))  // 저장된 메뉴 타입 검증
                .andExpect(jsonPath("$.data.price").value(5500));  // 저장된 메뉴 가격 검증
    }

    @Test
    @WithMockUser
    @DisplayName("전체 메뉴 조회 테스트")
    void getAllMenus_test() throws Exception {
        // given
        // 1. 메뉴 두 개 생성
        Menu menu1 = new Menu();
        menu1.setName("아메리카노");
        menu1.setType("커피");
        menu1.setPrice(4500);

        Menu menu2 = new Menu();
        menu2.setName("카페라떼");
        menu2.setType("커피");
        menu2.setPrice(5000);

        mvc.perform(post("/menu")
                .content(mapper.writeValueAsString(menu1))
                .contentType(MediaType.APPLICATION_JSON));

        mvc.perform(post("/menu")
                .content(mapper.writeValueAsString(menu2))
                .contentType(MediaType.APPLICATION_JSON));

        // when & then
        mvc.perform(get("/menu"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value("200-1"))
                .andExpect(jsonPath("$.data[*].name")
                        .value(containsInAnyOrder("아메리카노", "카페라떼")));
    }

    @Test
    @WithMockUser
    @DisplayName("메뉴 수정 테스트")
    void updateMenu_test() throws Exception {
        // given
        // 1. 먼저 메뉴 생성
        Menu menu = new Menu();
        menu.setName("아메리카노3");
        menu.setType("커피");
        menu.setPrice(4500);

        String body = mapper.writeValueAsString(menu);

        MvcResult createResult = mvc.perform(post("/menu")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn();

        // 생성된 메뉴의 ID 추출
        String createResponse = createResult.getResponse().getContentAsString();
        //System.out.println(createResponse);
        Long menuId = JsonPath.parse(createResponse)
                .read("$.data.id", Long.class);
        //System.out.println(menuId);

        // 2. 수정할 메뉴 데이터 준비
        Menu updateMenu = new Menu();
        updateMenu.setName("아이스 아메리카노");
        updateMenu.setType("커피");
        updateMenu.setPrice(5000);

        String updateBody = mapper.writeValueAsString(updateMenu);

        // when & then
        mvc.perform(put("/menu/{menu_id}", menuId)
                        .content(updateBody)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value("200-1"))
                .andExpect(jsonPath("$.data.name").value("아이스 아메리카노"))
                .andExpect(jsonPath("$.data.price").value(5000));
    }

    @Test
    @WithMockUser
    @DisplayName("메뉴 삭제 테스트")
    void deleteMenu_test() throws Exception {
        // given
        // 1. 먼저 메뉴 생성
        Menu menu = new Menu();
        menu.setName("아메리카노");
        menu.setType("커피");
        menu.setPrice(4500);

        MvcResult createResult = mvc.perform(post("/menu")
                        .content(mapper.writeValueAsString(menu))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn();

        String createResponse = createResult.getResponse().getContentAsString();
        Long menuId = JsonPath.parse(createResponse)
                .read("$.data.id", Long.class);

        // when & then
        mvc.perform(delete("/menu/{menu_id}", menuId))  // URL 템플릿 사용
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value("200-1"));
    }


}