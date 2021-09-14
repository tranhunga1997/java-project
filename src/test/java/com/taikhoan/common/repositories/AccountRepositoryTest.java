package com.taikhoan.common.repositories;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.taikhoan.common.entites.AccountEntity;

@SpringBootTest
class AccountRepositoryTest {
	@Autowired
	AccountRepository repos;
	@Test
	void testFindByCEmail() {
		int editPassLv2 = repos.updatePasswordLv2("sunicoa", "123");
		
		System.out.println(editPassLv2);
		//fail("Not yet implemented");
	}

}
