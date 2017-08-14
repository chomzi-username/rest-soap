package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private static final String USER = "USER";
	private static final String ADMIN = "ADMIN";


	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception{
		httpSecurity.authorizeRequests()
			.antMatchers("/svc/v1/private/accounts/*").hasRole(USER)
			.antMatchers("/svc/v1/private/admin/**").hasRole(ADMIN)
			.and()
			.formLogin();
		httpSecurity.csrf().disable();//new
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.inMemoryAuthentication()
			.withUser("test").password("test").roles(USER)
			.and()
			.withUser("artur").password("artur").roles(USER,ADMIN);
	}

}
