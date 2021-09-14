package com.taikhoan.napthe.controller;


import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.taikhoan.common.entites.Jxsf8PayCoinEntity;
import com.taikhoan.common.entites.NapTheHistoryEntity;
import com.taikhoan.common.repositories.NapTheHistoryRepository;
import com.taikhoan.menu.service.MenuService;
import com.taikhoan.napthe.form.CallbackUrlForm;
/**
 * Xử lý dữ liệu callback và nạp đồng vào tài khoản
 * @author Trần Mạnh Hùng
 *
 */
@RestController
public class NapTheController {
	
	@Autowired
	NapTheHistoryRepository napTheHistoryRepository;
	@Autowired
	MenuService menuService;
	@Autowired
	HttpSession session;
	
	Logger LOGGER = LoggerFactory.getLogger(NapTheController.class);
	
	@GetMapping(value = "/gach-the")
	ResponseEntity<?> getCallback(CallbackUrlForm form){
		NapTheHistoryEntity accountInfo = napTheHistoryRepository.findByTransId(Long.valueOf(form.getTransID()));
		if (accountInfo == null) {
			LOGGER.debug(form.getTransID()+ " : khong ton tai");
			return ResponseEntity.ok().body("TransId này không tồn tại");
		}
		// tránh request nạp lại
		if(accountInfo.getStatusCode() == 1 || accountInfo.getStatusCode() == 2) {
			LOGGER.error("Mã thẻ: " + accountInfo.getCardCode() + " | Seri: " + accountInfo.getCardSerial()
					+ " | Da duoc nap thanh cong.");
			return ResponseEntity.ok().body("Lỗi!!!");
		}
		// update lại thông tin nhận từ callback
		NapTheHistoryEntity napTheHistoryEntity = new NapTheHistoryEntity();
		napTheHistoryEntity.setTransId(Long.valueOf(form.getTransID()));
		napTheHistoryEntity.setLoginName(accountInfo.getLoginName());
		napTheHistoryEntity.setCardCode(accountInfo.getCardCode());
		napTheHistoryEntity.setCardSerial(accountInfo.getCardSerial());
		napTheHistoryEntity.setCardType(accountInfo.getCardType());
		napTheHistoryEntity.setStatusCode(Integer.valueOf(form.getErrorCode()));
		napTheHistoryEntity.setStatus(form.getMsg());
		napTheHistoryEntity.setCardAmount(Integer.valueOf(form.getRamount()));
		napTheHistoryEntity.setInsertTime(accountInfo.getInsertTime());
		napTheHistoryRepository.save(napTheHistoryEntity);
		
		// kiểm tra errorCode và nạp đồng
		Jxsf8PayCoinEntity payCoinEntity = new Jxsf8PayCoinEntity();
		payCoinEntity.setAccount(accountInfo.getLoginName());
		// đúng thông tin thẻ
		if(form.getErrorCode().equals("1")) {
			payCoinEntity.setJbcoin(Integer.valueOf(form.getRamount())*10);
			menuService.savePayCoin(payCoinEntity);
			LOGGER.info("tai khoan: " + payCoinEntity.getAccount() + " da nap " + payCoinEntity.getJbcoin()/10000 + "v dong");
		}else if(form.getErrorCode().equals("2")) {
		// sai mệnh giá thẻ -50%
			payCoinEntity.setJbcoin(Integer.valueOf(form.getRamount())*10/2);
			menuService.savePayCoin(payCoinEntity);
			LOGGER.debug("tai khoan: " + payCoinEntity.getAccount() + " nap sai menh gia |"
					+ " Menh gia nap: "+accountInfo.getCardAmount() + " ! Menh gia thuc: " + form.getRamount());
			LOGGER.info("tai khoan: " + payCoinEntity.getAccount() + " da nap " + payCoinEntity.getJbcoin()/10000 + "v dong");
		}else {
			LOGGER.info("transId: " + form.getTransID() + " | errorCode:" + form.getErrorCode() + " | errorMsg: " + form.getMsg());
		}
		return ResponseEntity.ok().body("Đã nạp: "+ payCoinEntity.getJbcoin() + "đồng.");
	}

}
