package com.taikhoan.mail_verification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taikhoan.mail_verification.entity.ConfirmTokenEntity;
import com.taikhoan.mail_verification.repositories.ConfirmTokenRepository;

@Service
public class ConfirmTokenService {
	@Autowired
	ConfirmTokenRepository cfmTokenRepository;
	
	public ConfirmTokenEntity save(ConfirmTokenEntity cfmToken) {
		return cfmTokenRepository.save(cfmToken);
	}
	
	public ConfirmTokenEntity findByToken(String token) {
		return cfmTokenRepository.findByConfirmToken(token);
	}
}
