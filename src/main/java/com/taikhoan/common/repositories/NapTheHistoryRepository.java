package com.taikhoan.common.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taikhoan.common.entites.NapTheHistoryEntity;
/**
 * repository của table nap_the_history
 * @author Trần Mạnh Hùng
 *
 */
public interface NapTheHistoryRepository extends JpaRepository<NapTheHistoryEntity, Long> {
	
	NapTheHistoryEntity findByTransId(long transId);
	
	NapTheHistoryEntity findByCardSerialAndCardCode(String cardSerial, String cardCode);
}
