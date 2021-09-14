package com.taikhoan.common.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MaHoaPassWord {
	/**
	 * Crypt Md5 Hash
	 * @param pass
	 * @return
	 */
	public static String cryptWithMD5(String pass) {
		return DigestUtils.md5Hex(pass);
	}
	
	/**
	 * Crypt BCrypt Hash
	 * @param pass
	 * @return
	 */
	public static String cryptWithBCrypt(String pass) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(pass);
	}
}
