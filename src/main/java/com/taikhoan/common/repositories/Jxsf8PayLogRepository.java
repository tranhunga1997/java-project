package com.taikhoan.common.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.taikhoan.common.entites.Jxsf8PayLogEntity;

@Repository
public interface Jxsf8PayLogRepository extends JpaRepository<Jxsf8PayLogEntity, Integer> {
	
	@Query("SELECT SUM(paylog.addcoin) FROM Jxsf8PayLogEntity paylog"
			+ " WHERE paylog.account= ?1"
			+ " GROUP BY paylog.account")
	Long findAllSumAddCoinGroupByAccount(String loginName);
}
