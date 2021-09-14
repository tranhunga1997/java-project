package com.taikhoan.common.repositories;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Jxsf8PayCoinRepositoryTest {

	@Autowired
	private Jxsf8PayCoinRepository repos;
	
	@Test
	void testGetCoin() {
		System.out.println(repos.getCoin("sunicoa") == null);
		//fail("Not yet implemented");
	}

}
