package com.taikhoan.menu.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taikhoan.common.entites.AccountEntity;
import com.taikhoan.common.entites.NapTheHistoryEntity;
import com.taikhoan.common.repositories.NapTheHistoryRepository;
import com.taikhoan.common.utils.AccountInfoDeltailUtil;
import com.taikhoan.common.utils.CallAPIgachtheTCSR;
import com.taikhoan.interceptor.AccountDetail;
import com.taikhoan.menu.form.EditPasswordLv1Form;
import com.taikhoan.menu.form.EditPasswordLv2Form;
import com.taikhoan.menu.form.NapTheForm;
import com.taikhoan.menu.service.MenuService;
/**
 * trang menu
 * @author Trần Mạnh Hùng
 *
 */
@Controller
@RequestMapping("/menu")
public class MenuController {
	@Autowired
	private HttpSession session;
	@Autowired
	private MenuService menuService;
	@Autowired
	private NapTheHistoryRepository napTheHistoryRepository;
	
	private static final String PATH = "menu";

	Logger LOGGER = LoggerFactory.getLogger(MenuController.class);
	/**
	 * Thông tin tài khoản
	 * @param model
	 * @return
	 */
	@GetMapping(value = {"","/","/thong-tin-tai-khoan"})
	public String accountDetailInfoInitial(Model model) {
		initialDefault(model);
		return PATH + "/thongtintaikhoan";
	}
	/**
	 * đổi mật khẩu cấp 1
	 * @param form
	 * @return
	 */
	@ResponseBody
	@PostMapping("/thong-tin-tai-khoan/doi-mat-khau-cap-1")
	public String passwordLv1EditAction(EditPasswordLv1Form form) {
		// get thông tin từ session
		AccountEntity accountInfo= getAccountInfoSession();
		
		//kiểm tra form
		if(form.getNewPassword().trim().equals("") || form.getNewPassword() == null) {
			return "Hãy nhập mật khẩu mới.";
		}
		if(form.getNewPasswordRepeat().trim().equals("") || form.getNewPasswordRepeat() == null) {
			return "Hãy xác nhận mật khẩu mới.";
		}
		if(form.getNewPassword().length() < 6 || form.getNewPassword().length() > 32) {
			return "Hãy nhập mật khẩu mới từ 6 đến 32 ký tự.";
		}
		
		if(!form.getNewPasswordRepeat().equals(form.getNewPassword())) {
			return "Xác nhận mật khẩu mới không khớp.";
		}
		menuService.editPasswordLv1(accountInfo.getLoginName(), form.getNewPassword());
		return "Đổi mật khẩu thành công";
	}
	/**
	 * đổi mật khẩu cấp 2
	 * @param form
	 * @return
	 */
	@ResponseBody
	@PostMapping("/thong-tin-tai-khoan/doi-mat-khau-cap-2")
	public String passwordLv2Edit(EditPasswordLv2Form form) {
		// get thông tin
		AccountEntity accountInfo= getAccountInfoSession();
		
		// kiểm tra form
		if(form.getCEmail().trim().equals("") || form.getCEmail() == null) {
			return "Hãy nhập Email.";
		}
		if(form.getNewPasswordLv2().trim().equals("") || form.getNewPasswordLv2() == null) {
			return "Hãy nhập mật khẩu cấp 2 mới.";
		}
		if(form.getNewPasswordLv2Repeat().trim().equals("") || form.getNewPasswordLv2Repeat() == null) {
			return "Hãy xác nhận mật khẩu cấp 2 mới.";
		}
		if(!form.getCEmail().equals(accountInfo.getCEmail())) {
			return "Email không đúng. Xin thử lại.";
		}
		if(form.getNewPasswordLv2().length() < 6 || form.getNewPasswordLv2().length() > 32) {
			return "Mật khẩu cấp 2 mới phải từ 6 đến 32 ký tự.";
		}
		if(!form.getNewPasswordLv2Repeat().equals(form.getNewPasswordLv2())) {
			return "Xác nhận mật khẩu cấp 2 mới không khớp.";
		}
		
		menuService.editPasswordLv2(accountInfo.getLoginName(), form.getNewPasswordLv2());
		return "Đổi mật khẩu cấp 2 thành công.";
	}
	
	@GetMapping("/nap-the")
	public String initial(Model model) {
		initialDefault(model);
		List<NapTheHistoryEntity> napTheHistoryEntityList = napTheHistoryRepository.findAll();
		napTheHistoryEntityList.forEach(item -> {
			System.err.println(item.toString());
		});
		model.addAttribute("napTheHistoryList", napTheHistoryEntityList);
		return PATH + "/napthe";
	}
	/**
	 * nhận thông tin nạp thẻ từ form nạp thẻ
	 * @param model
	 * @param form
	 * @return
	 */
	@PostMapping("/nap-the/postCardInfo")
	@ResponseBody
	public ResponseEntity<?> napTheAction(Model model, NapTheForm form) {
		List<String> errList = menuService.checkNullNapTheForm(form);
		NapTheHistoryEntity napTheHistoryEntity = napTheHistoryRepository.findByCardSerialAndCardCode(form.getCoinCardKey(), form.getMathe());
		if(napTheHistoryEntity != null) {
			return ResponseEntity.ok().body("Thẻ đã tồn tại hoặc đang chờ xử lý");
		}
		if(!errList.isEmpty()) {
			return ResponseEntity.ok(errList);
		}
		
		String transId = RandomStringUtils.randomNumeric(8);
		int errorCode = CallAPIgachtheTCSR.napThe(form, transId);
		AccountEntity accountInfo= getAccountInfoSession();
		napTheHistoryEntity = new NapTheHistoryEntity();
		napTheHistoryEntity.setLoginName(accountInfo.getLoginName());
		napTheHistoryEntity.setTransId(Long.valueOf(transId));
		napTheHistoryEntity.setCardAmount(Integer.valueOf(form.getMenhgia()));
		napTheHistoryEntity.setCardType(form.getLoaithe());
		napTheHistoryEntity.setCardCode(form.getMathe());
		napTheHistoryEntity.setCardSerial(form.getCoinCardKey());
		napTheHistoryEntity.setStatusCode(errorCode);
		napTheHistoryEntity.setStatus(errorNotice(errorCode));
		napTheHistoryEntity.setInsertTime(Timestamp.valueOf(LocalDateTime.now()));
		
		if(errorCode == 99 || errorCode == -99) {
			LOGGER.info("tai khoan: " + accountInfo.getLoginName() + " | errorCode: " + errorCode + " | errorMsg: cho xu ly");
			napTheHistoryRepository.save(napTheHistoryEntity);
			return ResponseEntity.ok("Chờ xử lý.");
		}else {
			LOGGER.info("tai khoan: " + accountInfo.getLoginName() + " | Seri: " + form.getCoinCardKey() + " (" + errorNotice(errorCode) + ")");
			return ResponseEntity.ok().body(errorNotice(errorCode));
		}
		
	}
	/**
	 * tạo msg dựa trên errorCode nạp thẻ
	 * @param errorCode
	 * @return
	 */
	String errorNotice(int errorCode) {
		switch (errorCode) {
		case -4:
			return "Mã giao dịch đã tồn tại.";
		case -3:
			return "Sai dữ liệu đầu vào. Hãy báo lại với Admin.";
		case -2:
			return "Nghi vấn dò quét. 30p sau hãy nạp lại.";
		case -1:
			return "Hệ thống đang bận, nạp thẻ lại sau ít phút.";
		case 0:
			return "Lỗi phát sinh. Hãy báo lại với Admin.";
		case 3:
			return "Thẻ lỗi.";
		case 307:
			return "Thẻ đã tồn tại.";
		case 311:
			return "thẻ sai định dạng.";
		default:
			return null;
		}
	}
	
	/**
	 * mặc định modal thông tin các web trong menu
	 * @param model
	 */
	void initialDefault(Model model) {
		// get thông tin từ session
		AccountEntity accountInfo= getAccountInfoSession();
		
		//String passwordLevel2 = AccountInfoDeltailUtil.getSymbolPassword(accountInfo.getPasswordLevel2());
		String passwordLevel2 = AccountInfoDeltailUtil.setSymbolEncrypt(accountInfo.getPasswordLevel2(), 0, 0, '*');
		String EmailEncrypt = AccountInfoDeltailUtil.setSymbolEncrypt(accountInfo.getCEmail(), 1, 1, '*');
		accountInfo.setCEmail(EmailEncrypt);
		accountInfo.setPasswordLevel2(passwordLevel2);
		long sumWithdrawCoin = menuService.sumWithdrawCoin(accountInfo.getLoginName()) / 10000;
		long sumDepositeCoin = menuService.sumDepositeCoin(accountInfo.getLoginName()) / 10000;
		
		model.addAttribute("sumDepositeCoin", sumDepositeCoin);
		model.addAttribute("sumWithdrawCoin", sumWithdrawCoin);
		session.setAttribute("accountInfo", accountInfo);
		session.setAttribute("xu", accountInfo.getXu() / 10000);
	}
	/**
	 * lấy thông tin account từ session
	 * @return
	 */
	AccountEntity getAccountInfoSession() {
		if(session.getAttribute("SPRING_SECURITY_CONTEXT") == null) {
			return null;
		}
		
		SecurityContextImpl context=(SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
		AccountDetail accountDetail=(AccountDetail) context.getAuthentication().getPrincipal();
		AccountEntity accountInfo=accountDetail.getAccountEntity();
		return accountInfo;
	}
}
