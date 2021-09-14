package com.taikhoan.menu.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taikhoan.common.entites.Jxsf8PayCoinEntity;
import com.taikhoan.common.repositories.AccountRepository;
import com.taikhoan.common.repositories.Jxsf8PayCoinRepository;
import com.taikhoan.common.repositories.Jxsf8PayLogRepository;
import com.taikhoan.common.utils.MaHoaPassWord;
import com.taikhoan.menu.form.NapTheForm;

@Service
public class MenuService {
	@Autowired
	AccountRepository accountRepos;
	@Autowired
	Jxsf8PayLogRepository payLogRepos;
	@Autowired
	Jxsf8PayCoinRepository payCoinRepos;
	/**
	 * Lấy tổng đồng đã rút
	 * @param loginName
	 * @return
	 */
	public long sumWithdrawCoin(String loginName) {
		if(payLogRepos.findAllSumAddCoinGroupByAccount(loginName) == null) {
			return 0;
		}else {
		return payLogRepos.findAllSumAddCoinGroupByAccount(loginName);
		}
	}
	/**
	 * Tổng số đồng đã nạp (chưa rút)
	 * @param loginName
	 * @return
	 */
	public long sumDepositeCoin(String loginName) {
		if (payCoinRepos.getCoin(loginName) == null) {
			return 0;
		}else {
			return payCoinRepos.getCoin(loginName);
		}
	}
	
	public Jxsf8PayCoinEntity savePayCoin(Jxsf8PayCoinEntity payCoinEntity) {
		return payCoinRepos.save(payCoinEntity);
	}
	
	public int editPasswordLv1(String loginName, String passwordLv1) {
		return accountRepos.updatePassword(loginName, MaHoaPassWord.cryptWithMD5(passwordLv1), MaHoaPassWord.cryptWithBCrypt(passwordLv1));
	}
	
	public int editPasswordLv2(String loginName, String passwordLv2) {
		return accountRepos.updatePasswordLv2(loginName, passwordLv2);
	}
	
	public List<String> checkNullNapTheForm(NapTheForm form) {
		List<String> list = new ArrayList<String>();
		if(form.getCoinCardKey().trim().equals("") || form.getCoinCardKey() == null) {
			list.add("Hãy nhập mã Seri");
		}
		if(form.getMathe().trim().equals("") || form.getMathe() == null) {
			list.add("Hãy nhập mã thẻ");
		}
		if(form.getLoaithe().trim().equals("") || form.getLoaithe() == null) {
			list.add("Hãy chọn loại thẻ cần nạp");
		}
		if(form.getMenhgia().trim().equals("") || form.getMenhgia() == null) {
			list.add("Hãy chọn mệnh giá thẻ");
		}
		return list;
	}
	
}
