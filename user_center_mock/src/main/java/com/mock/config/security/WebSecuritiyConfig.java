package com.mock.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mock.config.security.handler.UserAuthFailureHandler;
import com.mock.config.security.handler.UserAuthSuccessHandler;
import com.mock.properties.SecurityProperties;

@Configuration
public class WebSecuritiyConfig extends WebSecurityConfigurerAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(WebSecuritiyConfig.class);
	
	@Autowired
	private SecurityProperties securityProperties;
	
	@Autowired
	private UserAuthSuccessHandler userAuthSuccessHandler;
	
	@Autowired
	private UserAuthFailureHandler userAuthFailureHandler;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/**
	 * 自定义表单登录 的配置
	 */
//	@Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//		logger.debug("启用自定义表单登录");
//        http.
//        	authorizeRequests()
//        		.antMatchers("/resources/**", "/login", "/about").permitAll()
//        		.antMatchers("/user/getAppInfo").hasAuthority("ADMIN")
//        		.anyRequest()
//        		.authenticated()
//        	.and()
//        	.formLogin().
//        		loginPage("/login");
//    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		logger.debug("同时处理表单登录和rest请求登录");
		//通过securityProperties.getBrowser().getLoginPage()获取到的登录页面，也是不需要进行认证的
		http.authorizeRequests().antMatchers("/resources/**", "/auth/require", "/about",securityProperties.getBrowser().getLoginPage()).permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin()
				.loginPage("/auth/require")
				.successHandler(userAuthSuccessHandler) //自定义的登录成功的处理器
				.failureHandler(userAuthFailureHandler); //自定义的登录失败的处理器
	}

}
