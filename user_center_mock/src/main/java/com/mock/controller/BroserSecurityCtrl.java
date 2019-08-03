/**
 * 
 */
package com.mock.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mock.properties.SecurityProperties;
import com.mock.response.Rtn;
import com.netflix.discovery.converters.Auto;

/**
 * @author zhaochen
 *
 */
@RestController
public class BroserSecurityCtrl {
	
	//请求的信息都缓存在HttpSessionRequestCache类中
	private RequestCache cache = new HttpSessionRequestCache();
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Autowired
	private SecurityProperties securityProperties ;
	
	/**
	 * 认证请求处理
	 * @return
	 * @throws IOException 
	 */
	@GetMapping("/auth/require")
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)	//如果认证失败，就返回401
	public Rtn requireAuth(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		//从请求的缓存中获取，引发跳转的请求url
		SavedRequest saveRequest = cache.getRequest(request, response);
		if(saveRequest != null) {
			//获取触发请求的跳转地址，并判断该请求的来源 如果是页面登录，就通过sendRedirect方法跳转到登录页面，反之则通过httpStatusCode 返回状态码
			String redirectUrl = saveRequest.getRedirectUrl();
			if(StringUtils.endsWithIgnoreCase(redirectUrl, ".html")) {
				redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
			}
		}

		return new Rtn("用户请求未授权，请引到至登录页面");
	}

}
