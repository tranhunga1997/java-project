package com.taikhoan.common.utils;

import org.apache.commons.lang.RandomStringUtils;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.taikhoan.common.entites.NapTheHistoryEntity;
import com.taikhoan.menu.form.NapTheForm;

public class CallAPIgachtheTCSR {

	public static int napThe(NapTheForm form, String transId) {
		try {				
			//test gọi api nạp thẻ
			//String APIKEY = "XOXHZYCVAHVMGJNRDNYSMCMJVHFTFGIV";
			//String taikhoan_tcsr = "nineball";
			
			String APIKEY = "GROFJTETMJNTKRTOAQORQPATPHAPWYXO";   //truy cập https://thecaosieure.com/profile để lấy
			String mathe = form.getMathe();
			String serial_the = form.getCoinCardKey();
			String menh_gia = form.getMenhgia();
			String cardType= form.getLoaithe();

			String taikhoan_tcsr = "hoangxuan";			
			//String transIdUrl= RandomStringUtils.randomNumeric(8);
			String zAPIURL = "https://thecaosieure.com/gachthe?account="+taikhoan_tcsr +"&cardType=" + cardType + "&cardCode=" + mathe
						+ "&APIKey=" + APIKEY + "&transId="+transId + "&cardSerial=" + serial_the+ "&cardAmount=" + menh_gia;		
			java.net.URL obj = new java.net.URL(zAPIURL);
			JSONTokener tokener = new JSONTokener(obj.openStream());
			JSONObject root = new JSONObject(tokener);
			return root.getInt("errorCode");
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
