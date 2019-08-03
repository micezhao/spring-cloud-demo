/**
 * 
 */
package com.mock.config.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mock.config.security.type.LoginTypeEnum;
import com.mock.properties.SecurityProperties;

/**
 * @author zhaochen
 * 
 */

@Component
//SavedRequestAwareAuthenticationSuccessHandler 类 是基于请求本身的一个登录成功处理类，使用此类认证成功的请求原路返回，如果是来自html的请求，将返回页面，如果是json，就提供json响应
//public class UserAuthSuccessHandler implements AuthenticationSuccessHandler
public class UserAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{
	
	private static final Logger logger=  LoggerFactory.getLogger(UserAuthSuccessHandler.class);
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private SecurityProperties securityProperties;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		logger.info("登录成功");
		
		if(securityProperties.getBrowser().getLoginType().equals(LoginTypeEnum.JSON)) {
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(objectMapper.writeValueAsString(authentication));
		}else {
			super.onAuthenticationSuccess(request, response, authentication);
		}
		
		
	}

}
