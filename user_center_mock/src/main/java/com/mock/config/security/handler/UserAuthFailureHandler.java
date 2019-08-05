package com.mock.config.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mock.config.security.type.LoginTypeEnum;
import com.mock.properties.SecurityProperties;


@Component
//public class UserAuthFailureHandler implements AuthenticationFailureHandler {
/**
 * SimpleUrlAuthenticationFailureHandler类 是基于请求本身的一个登录失败处理类，使用此类认证失败的请求将原路返回，
 * 如果是来自html的请求，将返回页面，如果是json，就提供json响应
 */
public class UserAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler{
	private static final Logger logger=  LoggerFactory.getLogger(AuthenticationFailureHandler.class);
	
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private SecurityProperties securityProperties;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		logger.info("登录失败");
		
		if(securityProperties.getBrowser().getLoginType().equals(LoginTypeEnum.JSON)) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(objectMapper.writeValueAsString(exception));
		}else {
			super.onAuthenticationFailure(request, response, exception);
		}	
	}

}
