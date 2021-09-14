package com.taikhoan.common.entites;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "jxsf8_paylog")
public class Jxsf8PayLogEntity {
	@Id
	private int accid;
	private String account;
	private int addcoin;
//	private String time;
//	
//	@Column(name = "coincardkey")
//	private String coinCardKey;
//	
//	private String mathe;
//	private String loaithe;
//	private int menhgia;
//	private String user;
}
