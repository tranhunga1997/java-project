package com.taikhoan.dangky.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.taikhoan.common.captcha.recaptcha.MyConstants;
import com.taikhoan.common.captcha.recaptcha.VerifyUtils;
import com.taikhoan.common.entites.AccountEntity;
import com.taikhoan.common.utils.MaHoaPassWord;
import com.taikhoan.dangky.form.RegisterForm;
import com.taikhoan.dangky.service.RegisterService;
import com.taikhoan.mail_verification.entity.ConfirmTokenEntity;
import com.taikhoan.mail_verification.event.ConfirmTokenEvent;
import com.taikhoan.mail_verification.service.ConfirmTokenService;

@Controller
@RequestMapping("/dang-ky")
public class RegisterController {
	
	@Autowired
	RegisterService registerService;
	@Autowired
	ApplicationEventPublisher publisher;
	@Autowired
	ConfirmTokenService tokenService;
	
	public static final String PATH = "register";
	/**
	 * Khởi tạo trang đăng ký
	 * @param session
	 * @return
	 */
	@GetMapping(value= {"","/"})
	String initial(HttpSession session) {
		if(session.getAttribute("SPRING_SECURITY_CONTEXT") != null) {
			return "redirect:/menu";
		}
		return PATH;
	}
	/**
	 * Xử lý form đăng ký
	 * @param request
	 * @param model
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/process", method = {RequestMethod.GET,RequestMethod.POST})
	String registerAction(HttpServletRequest request, Model model,RegisterForm form) {
		/* check Null trong form */
		List<String> errNullList = registerService.checkNullRegForm(form);
		if(!errNullList.isEmpty()) {
			model.addAttribute("errList", errNullList);
			return PATH;
		}
		
		/* check điều kiện trong form */
		List<String> errList = registerService.checkRegForm(form);
		if(!errList.isEmpty()) {
			model.addAttribute("errList", errList);
			return PATH;
		}
		
		/* check RECAPTCHA
		VerifyUtils verifyUtils = new VerifyUtils();
		boolean checkCaptcha = verifyUtils.isCaptchaValid(MyConstants.SECRET_KEY, request.getParameter("g-recaptcha-response"));
		if(!checkCaptcha) {
			model.addAttribute("captchaMsg", "Captcha chưa được xác nhận.");
			return PATH;
		}
		*/
		/* Đăng ký thành công */
		AccountEntity accountEntity = new AccountEntity();
		BeanUtils.copyProperties(form, accountEntity);
		accountEntity.setTime(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		accountEntity.setLoginName(form.getLoginName().trim());
		accountEntity.setPasswordHash(MaHoaPassWord.cryptWithMD5(form.getPasswordHash()));
		accountEntity.setPasswordHashWeb(MaHoaPassWord.cryptWithBCrypt(form.getPasswordHash()));
		registerService.saveAccount(accountEntity);
		/* Khởi tạo token kích hoạt */
		ConfirmTokenEntity cfmToken = new ConfirmTokenEntity(accountEntity);
		publisher.publishEvent(new ConfirmTokenEvent(this, accountEntity, cfmToken));
		
		//model.addAttribute("msgSusscess", "Đăng ký tài khoản thành công, mã kích hoạt đã được gửi vào Email.");
		return "redirect:/dang-ky/confirm";
	}
	
	@GetMapping("/confirm")
	ModelAndView confirmTokenAccount(ModelAndView modelAndView,@RequestAttribute(name = "notice", required = false) boolean notice) {
		modelAndView.setViewName("active-account/confirm-form");
		return modelAndView;
	}
	
	@PostMapping("/confirm")
	String confirmTokenAccountAction(Model model, String confirmToken) {
			Calendar cal = Calendar.getInstance();
			Date now = cal.getTime();
			ConfirmTokenEntity cfmTokenEntity = tokenService.findByToken(confirmToken);
			
			System.out.println(now.compareTo(cfmTokenEntity.getExpiryTime()));
			if(cfmTokenEntity == null || cfmTokenEntity.getAccount().isEnable()) {
				return "redirect:/dang-ky/confirm?active=false";
			}
			if(now.after(cfmTokenEntity.getExpiryTime())) {
				return "redirect:/dang-ky/confirm?active=expiry"; 
			}
			cfmTokenEntity.getAccount().setEnable(true);
			registerService.saveAccount(cfmTokenEntity.getAccount());
		return "redirect:/dang-ky/confirm?active=true";
	}
	
	
}
