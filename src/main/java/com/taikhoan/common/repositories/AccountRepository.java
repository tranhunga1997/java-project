package com.taikhoan.common.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.taikhoan.common.entites.AccountEntity;
@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, String>{
	@SuppressWarnings("unchecked")
	public AccountEntity save(AccountEntity accountEntity);
	
	@Query("SELECT acc FROM AccountEntity acc WHERE acc.loginName = ?1")
	public AccountEntity findByLoginName(String loginName);
	
	@Query("SELECT acc FROM AccountEntity acc WHERE acc.cEmail =?1")
	public AccountEntity findByCEmail(String cEmail);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("UPDATE AccountEntity acc SET acc.passwordHash =:passwordHash, acc.passwordHashWeb =:passwordHashWeb"
			+ " WHERE acc.loginName =:loginName")
	public int updatePassword(String loginName, String passwordHash, String passwordHashWeb);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("UPDATE AccountEntity acc SET acc.passwordLevel2 =:passwordLv2 WHERE acc.loginName =:loginName")
	public int updatePasswordLv2(String loginName, String passwordLv2);
}
