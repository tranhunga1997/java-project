package com.taikhoan.dangky.form;

import lombok.Data;

@Data
public class RegisterForm {
	private String loginName;
	private String passwordHash;
	private String passwordHashRepeat;
	private String passwordLevel2;
	private String passwordLevel2Repeat;
	private String cRealName;
	private String cEmail;
}
