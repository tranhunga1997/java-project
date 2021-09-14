package com.taikhoan.dangky.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taikhoan.common.entites.AccountEntity;
import com.taikhoan.common.repositories.AccountRepository;
import com.taikhoan.dangky.form.RegisterForm;

@Service
public class RegisterService {
	@Autowired
	AccountRepository accountRepository;
	
	public List<AccountEntity> findAll(){
		return accountRepository.findAll();
	}
	
	public AccountEntity saveAccount(AccountEntity accountEntity) {
		System.out.println(accountEntity.toString());
		return accountRepository.save(accountEntity);
	}

	public AccountEntity findByLoginName(String loginName) {
		return accountRepository.findByLoginName(loginName);
	}
	
	public AccountEntity findByCEmail(String cEmail) {
		return accountRepository.findByCEmail(cEmail);
	}
	/**
	 * kiểm tra giá trị null và trống form đăng ký
	 * @param registerForm
	 * @return
	 */
	public List<String> checkNullRegForm(RegisterForm registerForm){
		List<String> errNullList = new ArrayList<String>();
		if(registerForm.getLoginName() == null || registerForm.getLoginName().trim() == "") {
			errNullList.add("Hãy nhập tên tài khoản.");
		}
		if(registerForm.getPasswordHash() == null || registerForm.getPasswordHash().trim().equals("")) {
			errNullList.add("Hãy nhập mật khẩu.");
		}
		if(registerForm.getPasswordHashRepeat() == null || registerForm.getPasswordHashRepeat().trim().equals("")) {
			errNullList.add("Hãy xác nhận mật khẩu.");
		}
		if(registerForm.getPasswordLevel2() == null || registerForm.getPasswordLevel2().trim().equals("")) {
			errNullList.add("Hãy nhập mật khẩu cấp 2");
		}
		if(registerForm.getPasswordLevel2Repeat() == null || registerForm.getPasswordLevel2Repeat().trim().equals("")) {
			errNullList.add("Hãy xác nhận mật khẩu cấp 2");
		}
		if(registerForm.getCEmail() == null|| registerForm.getCEmail().trim().equals("")) {
			errNullList.add("Hãy nhập Email.");
		}
		return errNullList;
	}
	/**
	 * kiểm tra ký tự trong form đăng ký
	 * @param registerForm
	 * @return
	 */
	public List<String> checkRegForm(RegisterForm registerForm){
		List<String> errList = new ArrayList<String>();
		// kiểm tra tên tài khoản
		if(this.findByLoginName(registerForm.getLoginName()) != null) {
			errList.add("Tài khoản "+ registerForm.getLoginName() + " đã tồn tại, vui lòng nhập tài khoản khác.");
		}
		if(!registerForm.getLoginName().matches("^[a-zA-Z]*$")) {
			errList.add("Tài khoản không được có số.");
		}
		if(registerForm.getLoginName().length() < 6 || registerForm.getLoginName().length() > 32) {
			errList.add("Nhập tài khoản từ 6 đến 32 ký tự.");
		}
		// kiểm tra mật khẩu
		if(registerForm.getPasswordHash().length() < 6 || registerForm.getPasswordHash().length() > 32) {
			errList.add("Hãy nhập mật khẩu từ 6 đến 32 ký tự.");
		}
		// kiểm tra nhập lại mật khẩu
		if(!registerForm.getPasswordHash().equals(registerForm.getPasswordHashRepeat())){
			errList.add("Xác nhận mật khẩu không khớp.");
		}
		// kiểm tra mật khẩu cấp 2
		if(registerForm.getPasswordLevel2().length() < 6 || registerForm.getPasswordLevel2().length() >32) {
			errList.add("Hãy nhập mật khẩu cấp 2 từ 6 đến 32 ký tự.");
		}
		// kiểm tra xác nhận mật khẩu cấp 2
		if(!registerForm.getPasswordLevel2Repeat().equals(registerForm.getPasswordLevel2())){
			errList.add("Xác nhận mật khẩu cấp 2 không khớp.");
		}
		// kiểm tra mật khẩu và mật khẩu cấp 2 có giống nhau
		if(registerForm.getPasswordHash().equals(registerForm.getPasswordLevel2())) {
			errList.add("mật khẩu và mật khẩu cấp 2 không được giống nhau.");
		}
		// kiểm tra tên
		if(registerForm.getCRealName().length() > 32) {
			errList.add("Họ và tên không quá 32 ký tự.");
		}
		// kiểm tra Email
		if(!registerForm.getCEmail().matches("^[a-zA-Z0-9_.+-]+@([a-zA-Z0-9][a-zA-Z0-9-]*[a-zA-Z0-9]*\\.)+[a-zA-Z]{2,}$")) {
			errList.add("Email sai định dạng.");
		}
		if(this.findByCEmail(registerForm.getCEmail()) != null) {
			errList.add("Email đã tồn tại, vui lòng nhập Email khác.");
		}
		return errList;
	}
}
