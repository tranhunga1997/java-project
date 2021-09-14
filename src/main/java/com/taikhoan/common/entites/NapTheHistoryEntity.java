package com.taikhoan.common.entites;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "nap_the_history")
@Data
public class NapTheHistoryEntity {
	
	private String loginName;
	@Id
	private long transId;
	private String cardType;
	private int cardAmount;
	private String cardCode;
	private String cardSerial;
	private int statusCode;
	private String status;
	private Timestamp insertTime;
}
