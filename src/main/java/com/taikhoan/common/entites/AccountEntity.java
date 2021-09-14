package com.taikhoan.common.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.Data;

@Entity
@Data
@Table(name= "account")
public class AccountEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long uid;
	@Column(name = "loginname", unique = true)
	private String loginName;
	private String passwordHash;
	@Column(name ="password_level_2")
	private String passwordLevel2;
	private String passwordHashWeb;
	private int xu;
	@Column(name="coin")
	private int coin;
	@Column(name="safecode")
	private int safeCode;
	@Column(name="cemail")
	private String cEmail;
	@Column(name="crealname")
	private String cRealName;
	private String time;
	@Column(name="checkmember")
	private int checkMember;
	@Column(name="lockstate")
	private int lockState;
	@Column(name="lockpassword")
	private int lockPassword;
	@NotNull
	private boolean isEnable;
	
}
