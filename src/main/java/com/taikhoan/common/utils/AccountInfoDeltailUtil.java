package com.taikhoan.common.utils;

import org.apache.commons.lang.StringUtils;

public class AccountInfoDeltailUtil {
	/**
	 * Chuyển password thành dạng ****
	 * @param password
	 * @return
	 */
	public static String getSymbolPassword(String password) {
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<password.length();i++) {
			sb.append("*");
		}
		return sb.toString();
	}
	/**
	 * Mã hóa chuỗi ký tự theo symbol chỉ định
	 * @param sourceStr : chuỗi truyền vào
	 * @param leftNum : số ký tự không mã hóa bên trái
	 * @param rightNum : số ký tự không mã hóa bên phải
	 * @param symbol : ký tự mã hóa 
	 * @return
	 */
	public static String setSymbolEncrypt(String sourceStr, int leftNum, int rightNum, char symbol) {
		String tempStr;
		int len = sourceStr.length();
		tempStr = sourceStr.substring(0, leftNum);
		tempStr = StringUtils.rightPad(tempStr, len, symbol);
		tempStr = tempStr + sourceStr.substring(len - rightNum, len);
		return tempStr;
	}
}
