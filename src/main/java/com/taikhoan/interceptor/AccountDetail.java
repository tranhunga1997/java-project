package com.taikhoan.interceptor;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.taikhoan.common.entites.AccountEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetail implements UserDetails{
	private static final long serialVersionUID = 1L;
	
	private AccountEntity accountEntity;
	public AccountEntity getAccountEntity() {
		return accountEntity;
	}
	public void setAccountEntity (AccountEntity accountEntity) {
		this.accountEntity=accountEntity;
	}
	@Override
	public String getPassword() {
		return accountEntity.getPasswordHashWeb();
	}

	@Override
	public String getUsername() {
		return accountEntity.getLoginName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return accountEntity.isEnable();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
	}

}
