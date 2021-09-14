package com.taikhoan.dangnhap.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LoginServiceTest {
	@Autowired
	LoginService logService;
	@Test
	void testCheckForgetPwdForm() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate today = LocalDate.now();
		System.out.println(today.format(format));
	}

}
