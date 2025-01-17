package com.ll.coffee.controller;

import com.ll.coffee.global.RsData;
import com.ll.coffee.menu.Menu;
import com.ll.coffee.menu.MenuDto;
import com.ll.coffee.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

        // 메뉴 추가
        @PostMapping
        public ResponseEntity<RsData<MenuDto>> addMenu(@RequestBody MenuDto menu) {
            // 메뉴 추가 작업
            MenuDto menuDto = menuService.addMenu(menu); // MenuDto 리턴

            // RsData로 감싸서 응답 생성
            RsData<MenuDto> rsData = new RsData<>(
                    "201-1", // 201 코드로 메뉴가 생성되었음을 나타냄
                    "%s 메뉴가 성공적으로 추가되었습니다.".formatted(menuDto.getName()),
                    menuDto
            );

            // ResponseEntity로 감싸서 상태 코드와 함께 응답 반환
            return new ResponseEntity<>(rsData, HttpStatus.CREATED); // 상태 코드 201(CREATED)
        }

        //메뉴 조회
    @GetMapping
    public ResponseEntity<RsData<List<MenuDto>>> getAllMenus() {
        List<MenuDto> menuDtos = menuService.getAllMenus();

        // RsData로 감싸서 응답 생성
        RsData<List<MenuDto>> rsData = new RsData<>(
                "200-1", // 조회 성공
                "메뉴가 성공적으로 조회되었습니다.",
                menuDtos
        );

        return new ResponseEntity<>(rsData, HttpStatus.OK);
    }

    @PutMapping("/{menu_id}")
    public ResponseEntity<RsData<MenuDto>> updateMenu(@PathVariable Long menu_id, @RequestBody MenuDto updateMenu) {
        MenuDto updatedMenuDto = menuService.updateMenu(menu_id, updateMenu);

        RsData<MenuDto> rsData = new RsData<>(
                "200-1",
                "%s 메뉴가 성공적으로 수정되었습니다.".formatted(updatedMenuDto.getName()),
                updatedMenuDto
        );

        return new ResponseEntity<>(rsData, HttpStatus.OK);
    }

    //메뉴 삭제
        @DeleteMapping("/{menu_id}")
         public ResponseEntity<RsData<Void>> deleteMenu(@PathVariable Long menu_id) {
        // 메뉴 삭제 작업
            menuService.deleteMenuById(menu_id);

        // RsData로 감싸서 응답 생성
             RsData<Void> rsData = new RsData<>(
                    "200-1", // 삭제 성공
                    "메뉴가 성공적으로 삭제되었습니다."
              );

        // ResponseEntity로 감싸서 상태 코드와 함께 응답 반환
              return new ResponseEntity<>(rsData, HttpStatus.OK); // 상태 코드 200(OK)
          }

          @GetMapping("/{menu_id}")
          public Menu getMenuById(@PathVariable Long menu_id) {
            return menuService.getMenuById(menu_id);
         }


}