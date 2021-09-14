package com.taikhoan.dangnhap.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.taikhoan.common.entites.AccountEntity;
import com.taikhoan.common.utils.MaHoaPassWord;
import com.taikhoan.dangnhap.form.ForgetPwdForm;
import com.taikhoan.dangnhap.service.LoginService;

@Controller
@RequestMapping(value = {"","/","/dang-nhap"})
public class LoginController {
	public static final String PATH_LOGIN = "login";
	public static final String PATH_FORGET_PWD = "forget-password";
	
	@Autowired
	HttpSession session;
	@Autowired
	LoginService loginService;
	/**
	 * Khởi tạo trang đăng nhập
	 * @return
	 */
	@GetMapping(value = {"","/"})
	String loginInitial() {
		if(session.getAttribute("SPRING_SECURITY_CONTEXT") != null) {
			return "redirect:/menu";
		}
		return PATH_LOGIN;
	}
	/**
	 * Khởi tạo trang quên mật khẩu 
	 * @return
	 */
	@RequestMapping(value = "/quen-mat-khau", method = {RequestMethod.GET, RequestMethod.POST})
	public String forgetPwdInitial() {
		if(session.getAttribute("SPRING_SECURITY_CONTEXT") != null) {
			return "redirect:/menu";
		}
		return PATH_FORGET_PWD;
	}
	/**
	 * Xử lý form quên mật khẩu
	 * @param model
	 * @param form
	 * @return
	 */
	@PostMapping(value = "/quen-mat-khau", params = "forgetPwd")
	public String forgetPwdProccess(Model model, ForgetPwdForm form) {
		System.out.println("forget password");
		AccountEntity accountInfo = loginService.findByLoginName(form.getLoginName());
		List<String> nullList = loginService.checkNullForgetForm(form);
		if(!nullList.isEmpty()) {
			model.addAttribute("nullList", nullList);
			return PATH_FORGET_PWD;
		}
		if(accountInfo == null) {
			model.addAttribute("errMsg", "Tài khoản không tồn tại.");
			return PATH_FORGET_PWD;
		}
		if(!accountInfo.getPasswordLevel2().equals(form.getPasswordLevel2())) {
			model.addAttribute("errMsg", "Mật khẩu cấp 2 không đúng.");
			return PATH_FORGET_PWD;
		}
		if(form.getPassword().length() < 6 || form.getPassword().length() > 32) {
			model.addAttribute("errMsg", "Hãy nhập mật khẩu từ 6 đến 32 ký tự.");
		}
		if(!form.getPassword().equals(form.getPasswordRepeat())) {
			model.addAttribute("errMsg", "Xác nhận mật khẩu không khớp với mật khẩu.");
			return PATH_FORGET_PWD;
		}
		// tiến hành đổi mật khẩu
		AccountEntity accountEntity = new AccountEntity();
		accountEntity.setLoginName(form.getLoginName());
		accountEntity.setPasswordHash(MaHoaPassWord.cryptWithMD5(form.getPassword()));
		accountEntity.setPasswordHashWeb(MaHoaPassWord.cryptWithBCrypt(form.getPassword()));
		loginService.updatePassword(accountEntity);
		return "redirect:/dang-nhap?status=changePwdSuccess";
	}
	
}
