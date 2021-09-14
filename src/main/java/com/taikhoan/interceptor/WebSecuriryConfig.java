package com.taikhoan.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.taikhoan.common.entites.AccountEntity;
import com.taikhoan.dangnhap.service.LoginService;

@EnableWebSecurity
@Configuration
public class WebSecuriryConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	LoginService loginService;
	@Autowired BCryptPasswordEncoder pwdEncoder;
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(loginService).passwordEncoder(pwdEncoder);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.debug(false).ignoring().antMatchers("/css/**", "/image/**", "/js/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// bỏ security trang đăng ký
		http.authorizeRequests().antMatchers("/dang-ky/**").permitAll();
		// Cấu hình login form
		http.authorizeRequests().antMatchers("/menu/**").authenticated()
				.and().csrf().disable()
				.formLogin()
				.loginProcessingUrl("/j_spring_security_check")
				.loginPage("/dang-nhap")
				.defaultSuccessUrl("/menu")
				.usernameParameter("loginName")
				.passwordParameter("passwordHashWeb")
				
				.failureHandler((req, res, exp) -> {
					AccountEntity user = loginService.findByLoginName(req.getParameter("loginName"));
					if (user == null) {
						res.sendRedirect("/dang-nhap?status=errorLogin&detail=notExist");
					}
					else {
						if(!pwdEncoder.matches(req.getParameter("passwordHashWeb"), user.getPasswordHashWeb())) {
							res.sendRedirect("/dang-nhap?status=errorLogin&detail=pwdWrong");
						}else if(!user.isEnable()) {
							res.sendRedirect("/dang-nhap?status=errorLogin&detail=notActive");	
						}else {
							exp.printStackTrace();
						}
					}
				 })
				
				// Cấu hình log out
				.and()
				.logout()
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.logoutSuccessUrl("/dang-nhap");

		// Cấu hình Remember Me.
		http.authorizeRequests().and().rememberMe().tokenRepository(this.persistentTokenRepository())
				.tokenValiditySeconds(1 * 24 * 60 * 60); // 24h
	}

	// Token stored in Memory (Of Web Server).
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		InMemoryTokenRepositoryImpl memory = new InMemoryTokenRepositoryImpl();
		return memory;
	}
}
