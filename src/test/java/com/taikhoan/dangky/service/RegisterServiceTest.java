package com.taikhoan.dangky.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.taikhoan.dangky.form.RegisterForm;
@SpringBootTest
class RegisterServiceTest {

	@Autowired
	RegisterService regService;
	@Test
	void test() {
		RegisterForm regForm = new RegisterForm();
		regForm.setLoginName("sunico123");
		regForm.setPasswordHash("123");
		regForm.setPasswordHashRepeat("123");
		regForm.setCRealName("hung");
		regForm.setCEmail("hung@gmail.com");
		List<String> list = regService.checkRegForm(regForm);
		list.forEach(item -> {
			System.out.println(item);
		});
		//fail("Not yet implemented");
	}

}
