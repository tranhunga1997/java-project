package com.taikhoan.mail_verification.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;

import com.taikhoan.common.entites.AccountEntity;
import com.taikhoan.mail_verification.entity.ConfirmTokenEntity;

import lombok.Getter;

@Getter
public class ConfirmTokenEvent extends ApplicationEvent {
	private static final long serialVersionUID = 1L;
	@Autowired
	AccountEntity account;
	@Autowired
	ConfirmTokenEntity cfmToken;
	
	public ConfirmTokenEvent(Object source, AccountEntity account, ConfirmTokenEntity cfmToken) {
		super(source);
		this.account = account;
		this.cfmToken = cfmToken;
	}
	
	

}
