package com.taikhoan.mail_verification.entity;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang.RandomStringUtils;

import com.taikhoan.common.entites.AccountEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "confirm_token_tbl")
@Data
@NoArgsConstructor
public class ConfirmTokenEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	@Column(name = "confirm_token", nullable = false)
	private String confirmToken;
	@Column(name = "created_time")
	private Date createdTime;
	@Column(name = "expiry_time")
	private Date expiryTime;
	@OneToOne(targetEntity = AccountEntity.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "uid")
	private AccountEntity account;
	
	public ConfirmTokenEntity(AccountEntity account) {
		Calendar cal = Calendar.getInstance();
		this.account = account;
		createdTime = cal.getTime();
		cal.add(Calendar.MINUTE, 1);
		expiryTime = cal.getTime();
		confirmToken = RandomStringUtils.randomNumeric(6);
	}
}
