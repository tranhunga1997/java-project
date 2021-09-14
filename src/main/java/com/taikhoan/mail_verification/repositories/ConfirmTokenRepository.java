package com.taikhoan.mail_verification.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taikhoan.mail_verification.entity.ConfirmTokenEntity;

public interface ConfirmTokenRepository extends JpaRepository<ConfirmTokenEntity, Long> {
	ConfirmTokenEntity findByConfirmToken(String token);
}
