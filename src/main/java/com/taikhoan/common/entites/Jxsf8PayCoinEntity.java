package com.taikhoan.common.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "jxsf8_paycoin")
public class Jxsf8PayCoinEntity {

	@Id
	private int accid;
	private String account;
	private int jbcoin;
	@Column(name = "coincardkey")
	private String coinCardKey;
	private String mathe;
	private String loaithe;
	private int menhgia;
}
