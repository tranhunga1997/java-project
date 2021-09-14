package com.taikhoan.common.utils;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;

class AccountInfoDeltailUtilTest {

	@Test
	void testSetSymbolEncrypt() {
		Calendar cal = Calendar.getInstance();
		Date date1 = cal.getTime();
		cal.add(Calendar.MINUTE, 1);
		Date date2 = cal.getTime();
		System.out.println(date1.after(date2));
	}

}
