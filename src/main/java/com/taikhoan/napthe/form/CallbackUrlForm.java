package com.taikhoan.napthe.form;

import lombok.Data;

@Data
public class CallbackUrlForm {
	private String errorCode;
	private String data;
	private String transID;
	private String msg;
	private String ramount;
	private String sign;
}
