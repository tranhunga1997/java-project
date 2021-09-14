package com.taikhoan.dangnhap.form;

import lombok.Data;

@Data
public class ForgetPwdForm {
	private String loginName;
	private String passwordLevel2;
	private String password;
	private String passwordRepeat;
}
