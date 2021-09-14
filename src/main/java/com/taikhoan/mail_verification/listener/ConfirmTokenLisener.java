package com.taikhoan.mail_verification.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.taikhoan.common.entites.AccountEntity;
import com.taikhoan.mail_verification.entity.ConfirmTokenEntity;
import com.taikhoan.mail_verification.event.ConfirmTokenEvent;
import com.taikhoan.mail_verification.service.ConfirmTokenService;
import com.taikhoan.mail_verification.service.MailSenderService;

@Component
public class ConfirmTokenLisener {
	@Autowired
	MailSenderService mailSender;
	@Autowired
	ConfirmTokenService tokenService;
	@EventListener
	@Async
	public void cfmTokenListener(ConfirmTokenEvent event) {
		AccountEntity account = event.getAccount();
		ConfirmTokenEntity token = event.getCfmToken();
		tokenService.save(token);
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(account.getCEmail());
		mailMessage.setFrom("tranhungb1997@gmail.com");
		mailMessage.setSubject("Kích hoạt tài khoản (TEST)");
		mailMessage.setText("Mã kích hoạt: " + token.getConfirmToken()
		+"\n Lưu ý: mã kích hoạt có hiệu lực trong 10 phút");
		mailSender.sendEmail(mailMessage);
		
	}
}
