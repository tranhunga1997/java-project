package com.taikhoan.dangnhap.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.taikhoan.common.entites.AccountEntity;
import com.taikhoan.common.repositories.AccountRepository;
import com.taikhoan.dangnhap.form.ForgetPwdForm;
import com.taikhoan.interceptor.AccountDetail;

@Service
public class LoginService implements UserDetailsService {

	@Autowired
	AccountRepository accountRepository;
	
	@Override
	public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
		AccountEntity accountEntity = accountRepository.findByLoginName(loginName);
		if(accountEntity == null) {
			throw new UsernameNotFoundException("Tài khoản "+ loginName + " không tồn tại.");
		}
			
		return new AccountDetail(accountEntity);
	}
	
	public AccountEntity findByLoginName(String loginName) {
		return accountRepository.findByLoginName(loginName);
	}
	
	public int updatePassword(AccountEntity accountEntity) {
		return accountRepository.updatePassword(accountEntity.getLoginName(), accountEntity.getPasswordHash(), accountEntity.getPasswordHashWeb());
	}
	
	public List<String> checkNullForgetForm(ForgetPwdForm form){
		List<String> nullList = new ArrayList<String>();
		if(form.getLoginName().trim().equals("") || form.getLoginName() == null) {
			nullList.add("Hãy nhập tên tài khoản.");
		}
		if(form.getPasswordLevel2().trim().equals("") || form.getPasswordLevel2() == null) {
			nullList.add("Hãy nhập email.");
		}
		if(form.getPassword().trim().equals("") || form.getPassword() == null) {
			nullList.add("Hãy nhập mật khẩu mới.");
		}
		if(form.getPasswordRepeat().trim().equals("") || form.getPasswordRepeat() == null) {
			nullList.add("Hãy xác nhận mật khẩu mới.");
		}
		return nullList;
	}
}
