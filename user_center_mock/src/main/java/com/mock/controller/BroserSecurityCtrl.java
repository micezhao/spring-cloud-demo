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

import com.mock.config.security.handler.VaildataProccessHolder;
import com.mock.properties.SecurityProperties;
import com.mock.response.Rtn;

/**
 * @author zhaochen
 *
 */
@RestController
public class BroserSecurityCtrl {
	
	
	//请求的信息都缓存在HttpSessionRequestCache类中
	private RequestCache cache = new HttpSessionRequestCache();
	
	//跳转策略类
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Autowired
	private SecurityProperties securityProperties ;
	
	@Autowired
	private VaildataProccessHolder vaildataProccessHolder ;
	
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
	
	
	@GetMapping("/auth/vaildataCode")
	public String vaildataCode(HttpServletRequest request,HttpServletResponse response) {
		String type = request.getParameter("type");
		return vaildataProccessHolder.findVaildataHandler(type).creatCode();
	}
	
	@GetMapping("/auht/checkCode")
	public String checkCode(HttpServletRequest request,HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		String code = request.getParameter("code");
		String type = request.getParameter("type");
		boolean flag=vaildataProccessHolder.findVaildataHandler(type).vaildata(code);
		if(flag) {
			return "验证通过";
		}else {
			return "验证失败";	
		}
	}
	
	
	public static void main(String[] args) {
		String  link_code = "1_[2,3,4,6,7]_[8,9,10]";
		String[] arr=link_code.split("_");
		String root = arr[0];
		System.out.println("根机构编码："+root);
		String SuperiorStr = arr[1];
		String[]  supArr = SuperiorStr.substring(1, SuperiorStr.length()-1).split(",");
		StringBuffer supCode = new StringBuffer();
		for (int i = 0; i < supArr.length; i++) {
			if(i==(supArr.length-1)) {
				supCode.append(supArr[i]);
			}else {				
				supCode.append(supArr[i]+",");
			}
		}
		System.out.println("上级机构数量："+supArr.length+" 上级机构的编码："+supCode.toString());
		System.out.println("当前机构的层级："+(supArr.length+1));
		String subordinateStr = arr[2];
		String[] subArr = subordinateStr.substring(1,subordinateStr.length()-1).split(",");
		StringBuffer subCode = new StringBuffer();
		for (int i = 0; i < subArr.length; i++) {
			if(i==(subArr.length-1)) {
				subCode.append(subArr[i]);
			}else {				
				subCode.append(subArr[i]+",");
			}
		}
		System.out.println("下属机构数量："+subArr.length+ " 下属机构的编码："+subCode.toString());
	}
}
