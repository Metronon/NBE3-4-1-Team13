package com.ll.coffee;

import com.ll.coffee.member.Member;
import com.ll.coffee.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CoffeeApplicationTests {

	@Autowired
	private UserService userService;

	@Test
	void contextLoads() {

		Member member = userService.save("admin", "admin@admin.com", "admin");
	}

}
