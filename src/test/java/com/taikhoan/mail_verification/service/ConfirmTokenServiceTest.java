package com.taikhoan.mail_verification.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.taikhoan.mail_verification.entity.ConfirmTokenEntity;
@SpringBootTest
class ConfirmTokenServiceTest {
	@Autowired
	ConfirmTokenService tokenService;
	@Test
	void testFindByToken() {
		ConfirmTokenEntity token = tokenService.findByToken("998633");
		System.out.println(token.toString());
		//fail("Not yet implemented");
	}

}
