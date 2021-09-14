package com.taikhoan.common.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.taikhoan.common.entites.Jxsf8PayCoinEntity;

@Repository
public interface Jxsf8PayCoinRepository extends JpaRepository<Jxsf8PayCoinEntity, Integer> {
	@Query("SELECT SUM(paycoin.jbcoin) FROM Jxsf8PayCoinEntity paycoin"
			+ " WHERE paycoin.account = ?1"
			+ " GROUP BY paycoin.account")
	Long getCoin(String loginName);
	

}
