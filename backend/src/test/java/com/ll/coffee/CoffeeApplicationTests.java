package com.ll.coffee;

import com.ll.coffee.member.Member;
import com.ll.coffee.menu.Menu;
import com.ll.coffee.menu.MenuDto;
import com.ll.coffee.service.MenuService;
import com.ll.coffee.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class CoffeeApplicationTests {

	@Autowired
	private UserService userService;

	@Test
	void contextLoads() {

		Member member = userService.save("admin", "admin@admin.com", "admin");
	}


	//메뉴 서비스 테스트
	@Autowired
	private MenuService menuService;

	@Test
	@DisplayName("메뉴 생성 서비스 테스트")
	void addMenu() {

			Menu menu = new Menu();
			menu.setName("카푸치노");
			menu.setType("커피");
			menu.setPrice(5300);

			MenuDto savedMenu = menuService.addMenu(menu);
			//menu객체 db 저장 -> 저장된 객체를 dto로 변환해 리턴
			// then
			assertThat(savedMenu.getId()).isNotNull();
			assertThat(savedMenu.getName()).isEqualTo("카푸치노");
			assertThat(savedMenu.getType()).isEqualTo("커피");
			assertThat(savedMenu.getPrice()).isEqualTo(5300);
	}

	@Test
	@DisplayName("전체 메뉴 조회 서비스 테스트")
	void getAllMenus() {
//		// given
//		Menu menu1 = new Menu();
//		menu1.setName("아메리카노");
//		menu1.setType("커피");
//		menu1.setPrice(4500);
//
//		Menu menu2 = new Menu();
//		menu2.setName("라떼");
//		menu2.setType("커피");
//		menu2.setPrice(5000);
//
//		menuService.addMenu(menu1);
//		menuService.addMenu(menu2);

		// when
		List<MenuDto> menus = menuService.getAllMenus();

		// then
		assertThat(menus).hasSize(3);
		assertThat(menus).extracting("name")
				.containsExactlyInAnyOrder("아메리카노", "카페라떼","카푸치노");
	}

	@Test
	@DisplayName("메뉴 수정 서비스 테스트")
	void updateMenu() {
		// given
		Menu menu = new Menu();
		menu.setName("뜨거운 아메리카노");
		menu.setType("커피");
		menu.setPrice(3000);
		MenuDto menuDto=menuService.addMenu(menu);// 저장

		Long id= menuDto.getId();

		// then
		assertThat(menuDto.getName()).isEqualTo("뜨거운 아메리카노");
		assertThat(menuDto.getPrice()).isEqualTo(3000);

		Menu updateMenu = new Menu();
		updateMenu.setName("아이스 아메리카노");
		updateMenu.setType("커피");
		updateMenu.setPrice(3500);

		MenuDto updatedMenu = menuService.updateMenu(id, updateMenu);//업데이트
		// then
		assertThat(updatedMenu.getName()).isEqualTo("아이스 아메리카노");
		assertThat(updatedMenu.getPrice()).isEqualTo(3500);
	}

	@Test
	@DisplayName("메뉴 삭제 서비스 테스트")
	void deleteMenu() {

		// when
		menuService.deleteMenuById(1L);

		// then
		assertThatThrownBy(() -> menuService.getMenuById(1L))
				.isInstanceOf(RuntimeException.class)
				.hasMessageContaining("Menu not found");
	}


}
