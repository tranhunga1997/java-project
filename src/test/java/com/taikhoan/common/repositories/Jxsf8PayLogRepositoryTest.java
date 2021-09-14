package com.taikhoan.common.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.taikhoan.common.entites.Jxsf8PayLogEntity;

@SpringBootTest
class Jxsf8PayLogRepositoryTest {
	@Autowired
	Jxsf8PayLogRepository repos;
	
	@Test
	void testFindAllSumAddCoinGroupByAccount() {
		long sumCoin = repos.findAllSumAddCoinGroupByAccount("sunico");
		System.out.println(sumCoin);
		//fail("Not yet implemented");
	}

}
