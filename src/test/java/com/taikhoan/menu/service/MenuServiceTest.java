package com.taikhoan.menu.service;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.taikhoan.common.entites.Jxsf8PayCoinEntity;
import com.taikhoan.menu.form.NapTheForm;
@SpringBootTest
class MenuServiceTest {
	
	@Autowired
	MenuService menuService;
	@Test
	void testSavePayCoin() {
		/*
		Jxsf8PayCoinEntity payCoinEntity = new Jxsf8PayCoinEntity();
		payCoinEntity.setAccount("sunicob");
		payCoinEntity.setJbcoin(10000);
		payCoinEntity.setCoinCardKey("AAAAAAAAA");
		payCoinEntity.setMathe("BBBBBBBBB");
		payCoinEntity.setLoaithe("viettel");
		payCoinEntity.setMenhgia(10000);
		System.out.println(menuService.savePayCoin(payCoinEntity));
		*/
		
		System.out.println(Timestamp.valueOf(LocalDateTime.now()));
		//fail("Not yet implemented");
	}

}
