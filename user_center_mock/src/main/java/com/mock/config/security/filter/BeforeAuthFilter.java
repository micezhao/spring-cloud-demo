package com.mock.config.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mock.config.security.exception.BeforeFilterException;
import com.mock.config.security.handler.UserAuthFailureHandler;

@Component
public class BeforeAuthFilter extends OncePerRequestFilter {

	public static final Logger logger = LoggerFactory.getLogger(BeforeAuthFilter.class);
	
	@Autowired
	private UserAuthFailureHandler  failureHandler;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		logger.info("进入了自定义的前置过滤器");
		if(request.getRequestURI().equals("/auth/require")) {
			if(!StringUtils.equals("user", request.getParameter("username")) ) {
				String errorMsg = "非法的用户信息";
				failureHandler.onAuthenticationFailure(request, response,new BeforeFilterException(errorMsg));
				return;
			}
		}
		filterChain.doFilter(request, response);
	}

}
